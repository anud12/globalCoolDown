import {Injectable} from '@angular/core';
import {StompConfig, StompService} from '@stomp/ng2-stompjs';
import {Subscription} from 'rxjs/Subscription';
import {Subject} from 'rxjs/Subject';
import {Message} from '@stomp/stompjs';
import {AreaModel} from '../model/area.model';

@Injectable()
export class MapService {
    areaSubject: Subject<Message>;
    subscription: Subscription;
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
    private stompService: StompService;
    private area: AreaModel;

    constructor() {
        this.area = new AreaModel([]);
        this.areaSubject = new Subject();
        this.stompService = new StompService(this.stompConfig);
        this.subscribe();
    }

    subscribe() {
        this.subscription = this.stompService.subscribe('/app/map')
            .subscribe(message => {
                const messageObject = JSON.parse(message.body);
                console.log(messageObject);
                this.area = messageObject;
                this.areaSubject.next();
            });
    }

    getAreaStompSubscription() {
        return this.areaSubject;
    }

    getArea(): AreaModel {
        return this.area;
    }

}
