import {Injectable} from '@angular/core';
import {StompConfig, StompService} from '@stomp/ng2-stompjs';
import {Observable} from 'rxjs/Observable';
import {Message} from '@stomp/stompjs';
import {Subject} from 'rxjs/Subject';
import {DimensionModel} from '../model/dimension.model';
import {PawnModel} from './model/pawn.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {PawnMoveActionModel} from './model/pawn-move-action.model';
import {PointModel} from '../model/point.model';
import {AuthenticationService} from '../authentication/authentication.service';
import {Subscription} from 'rxjs/Subscription';


@Injectable()
export class PawnService {
    // private ip: string = "192.168.81.102";
    private ip: string = '192.168.0.143';
    private stompConfig: StompConfig = {
        url: `ws://${this.ip}:8080/socket`,
        headers: {
            login: 'guest',
            passcode: 'guest'
        },
        heartbeat_in: 0,
        heartbeat_out: 2000,
        reconnect_delay: 2000,
        debug: false
    };

    private httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
    };

    private queueActionUrl: string = `http://${this.ip}:8080/action/queue`;
    private overrideActionUrl: string = `http://${this.ip}:8080/action/override`;
    private pawnList: Map<number, PawnModel>;
    private selectedPawnList: Map<number, PawnModel>;
    private stompService: StompService;
    private pawnListSubject: Subject<Map<number, PawnModel>>;
    private selectedPawnListSubject: Subject<Message>;
    private subscription: Subscription;

    constructor(private httpClient: HttpClient,
                private authenticationService: AuthenticationService) {
        this.stompService = new StompService(this.stompConfig);
        this.pawnList = new Map();
        this.selectedPawnList = new Map();
        this.pawnListSubject = new Subject();
        this.selectedPawnListSubject = new Subject();
        this.subscribe();
        this.authenticationService.onLogin().subscribe((authenticationModel) => {
            if (this.subscription !== undefined) {
                this.unsubscribe();
            }
            this.subscribe();
        });
        this.authenticationService.onLogout().subscribe(() => {
            this.unsubscribe();
        });
    }

    subscribe() {
        this.subscription = this.stompService.subscribe('/app/pawn')
            .subscribe(message => {
                this.pawnList = new Map();
                const pawnList: Array<PawnModel> = JSON.parse(message.body);
                pawnList.forEach(element => {
                    this.pawnList.set(element.id, element);
                    if (this.selectedPawnList.has(element.id)) {
                        this.selectedPawnList.set(element.id, element);
                    }
                });
                this.pawnListSubject.next(this.pawnList);
            });
    }

    unsubscribe() {
        this.subscription.unsubscribe();
        this.subscription = undefined;
    }

    getPawnStompSubscription(): Observable<Map<number, PawnModel>> {
        return this.pawnListSubject.asObservable();
    }

    getPawnSelectSubscription(): Observable<Message> {
        return this.selectedPawnListSubject.asObservable();
    }

    getListById(): Map<number, PawnModel> {
        return this.pawnList;
    }

    sendHi() {
        this.stompService.publish('/app/pawn', 'hi');
    }

    selectAllByDimension(dimension: DimensionModel) {
        this.selectedPawnList.clear();
        this.pawnList.forEach(value => {
            if (value.point.x > dimension.start.x &&
                value.point.x < dimension.end.x &&
                value.point.y > dimension.start.y &&
                value.point.y < dimension.end.y) {
                this.selectedPawnList.set(value.id, value);
            }
        });
        this.selectedPawnListSubject.next();
    }

    getSelectedList(): Map<number, PawnModel> {
        return this.selectedPawnList;
    }

    queueMove(point: PointModel) {
        this.selectedPawnList.forEach(value => {
            let action = new PawnMoveActionModel(value.id, point.x, point.y);
            this.httpClient.post(this.queueActionUrl,
                action,
                this.httpOptions).subscribe(() => {
            });
        });
    }

    setMove(point: PointModel) {
        this.selectedPawnList.forEach(value => {
            let action = new PawnMoveActionModel(value.id, point.x, point.y);
            this.httpClient.post(this.overrideActionUrl,
                action,
                this.httpOptions).subscribe(() => {
            });
        });
    }

    deselect(pawn: PawnModel) {
        this.selectedPawnList.delete(pawn.id);
        this.selectedPawnListSubject.next();
    }
}
