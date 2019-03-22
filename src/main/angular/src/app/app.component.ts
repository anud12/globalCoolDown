import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RxStompService} from "@stomp/ng2-stompjs";
import {GlService} from "./opengl/gl.service";
import {GameObjectModel, LocationTrait} from "./java.models";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  message: string = "message";
  @ViewChild('glcanvas') glcanvas: ElementRef;

  gameObjectList: Array<GameObjectModel> = [];
  action: LocationTrait;

  constructor(private rxStompService: RxStompService) {
    this.action = {
      x: 0,
      y: 0
    }
  }

  ngOnInit() {

  }

  sendAction() {
    console.log("Publish", this.action)
    this.rxStompService.stompClient.publish({
      destination: "/ws/gameObject/queue/Teleport",
      body: JSON.stringify(this.action)
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
        glService.draw(this.gameObjectList.map(value1 => value1.aspects.LocationTrait as LocationTrait));
      })
    })
  }

}

