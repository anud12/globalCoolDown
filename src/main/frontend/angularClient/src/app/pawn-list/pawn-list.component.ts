import {Component, OnInit} from '@angular/core';
import {StompService} from "@stomp/ng2-stompjs";
import {Pawn} from "../models/pawn.model";

@Component({
  selector: 'app-pawn-list',
  templateUrl: './pawn-list.component.html',
  styleUrls: ['./pawn-list.component.css']
})
export class PawnListComponent implements OnInit {

  public pawnList: Pawn[];
  public pawnMap: Map<number, Pawn>;

  constructor(private stompService: StompService) {
    this.pawnMap = new Map();
  }

  ngOnInit() {
    let stompSubscription = this.stompService.subscribe("/app/world");
    stompSubscription.subscribe((message) => {
      const tempList: Pawn[] = JSON.parse(message.body);
      tempList.forEach(value => {
        if(this.pawnMap.has(value.id)){
          if(this.pawnMap.get(value.id).version >= value.version) {
            return;
          }
        }
        this.pawnMap.set(value.id, value);
      });
      this.pawnList = [];
      this.pawnMap.forEach(value => {
        this.pawnList.push(value);
      })
      console.log(this.pawnList);
    });

    this.stompService.publish("/app/pawn", "hi",)
  }

  getFromCharacterCode(characterCode: number) {
    return String.fromCharCode(characterCode)
  }
}
