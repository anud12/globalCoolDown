import {Component, OnInit} from '@angular/core';
import {StompService} from "@stomp/ng2-stompjs";
import {Pawn} from "../models/pawn.model";

@Component({
  selector: 'app-standard-layout',
  templateUrl: './standard-layout.component.html',
  styleUrls: ['./standard-layout.component.css']
})
export class StandardLayoutComponent implements OnInit {

  constructor(private stompService: StompService) {
  }

  ngOnInit() {


  }

}
