import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RxStompService} from "@stomp/ng2-stompjs";
import {GlService} from "./opengl/gl.service";
import {GameObjectModel, LocationTrait, Point} from "./java.models";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  message: string = "message";
  @ViewChild('glcanvas') glcanvas: ElementRef;

  gameObjectList: Array<GameObjectModel> = [];
  teleport: Point;
  move: Point;

  constructor(private rxStompService: RxStompService) {
    this.teleport = {
      x: 0,
      y: 0
    };
      this.move= {
          x: 0,
          y: 0
      }
  }

  ngOnInit() {

  }

  sendTeleport() {
    console.log("Publish", this.teleport)
    this.rxStompService.stompClient.publish({
      destination: "/ws/gameObject/1/queue/teleport",
      body: JSON.stringify(this.teleport)
    })
  }


    sendMove() {
        console.log("Publish", this.move)
        this.rxStompService.stompClient.publish({
                                                    destination: "/ws/gameObject/1/queue/move",
                                                    body: JSON.stringify(this.move)
                                                })
    }

  ngAfterViewInit(): void {
    const canvas = this.glcanvas.nativeElement;
    console.log(canvas)
    const glService = new GlService(canvas);
    this.rxStompService.connected$.subscribe(value => {
      this.rxStompService.stompClient.subscribe('/ws/hello', (message: any) => {
        this.message = message.body;
      });

      this.rxStompService.stompClient.subscribe("/ws/world", (message: any) => {
        this.gameObjectList = JSON.parse(message.body);
        glService.clear();
        glService.draw(this.gameObjectList.map(value1 => {
          const trait = value1.aspects.LocationTrait as LocationTrait;
          return trait.point2D
        }));
      })
    })
  }

}

