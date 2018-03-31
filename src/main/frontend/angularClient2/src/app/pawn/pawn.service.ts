import {Injectable} from '@angular/core';
import {StompConfig, StompService} from "@stomp/ng2-stompjs";
import {Observable} from "rxjs/Observable";
import {Message} from '@stomp/stompjs';
import {Subject} from "rxjs/Subject";
import {DimensionModel} from "../model/dimension.model";
import {PawnModel} from "./model/pawn.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {PawnMoveActionModel} from "./model/pawn-move-action.model";
import {PointModel} from "../model/point.model";


@Injectable()
export class PawnService {
  private ip: string = "localhost";
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

  private locationUrl: string = `http://${this.ip}:8080/action`;
  private pawnList: Map<number, PawnModel>;
  private selectedPawnList: Map<number, PawnModel>;
  private stompService: StompService;
  private subject: Subject<Message>;

  constructor(private httpClient: HttpClient) {
    this.stompService = new StompService(this.stompConfig);
    this.pawnList = new Map();
    this.selectedPawnList = new Map();
    this.subject = new Subject();

    this.stompService.subscribe("/app/world")
      .subscribe(message => {
        const pawnList: Array<PawnModel> = JSON.parse(message.body);
        pawnList.forEach(element => {
          this.pawnList.set(element.id, element);
          if (this.selectedPawnList.has(element.id)) {
            this.selectedPawnList.set(element.id, element);
          }
        });
        this.subject.next();
      });
  }

  getPawnStompSubscription(): Observable<Message> {
    return this.subject.asObservable();
  }

  getListById(): Map<number, PawnModel> {
    return this.pawnList;
  }

  sendHi() {
    this.stompService.publish("/app/pawn", "hi");
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
    })
  }

  getSelectedList(): Map<number, PawnModel> {
    return this.selectedPawnList
  }

  moveRequest(pawnList: Map<number, PawnModel>, point: PointModel) {
    this.selectedPawnList.forEach(value => {
      let action = new PawnMoveActionModel(value.id, point.x, point.y);
      this.httpClient.post(this.locationUrl,
        action,
        this.httpOptions).subscribe(() => {
      })
    })
  }
}
