import {Component, Input, OnInit} from '@angular/core';
import {Point} from "../../java.models";
import {Subject} from "rxjs";
import {ActionsEndpoints} from "../../endpoints/action.endpoints";

export type ActionComponentEvent = {
    endpoint: string,
    body: any
}

@Component({
    selector: 'app-action',
    templateUrl: './action.component.html',
    styleUrls: ['./action.component.scss']
})
export class ActionComponent implements OnInit {

    @Input() subject: Subject<ActionComponentEvent>;
    teleport: Point = {
        x: 0,
        y: 0
    };
    move: Point = {
        x: 0,
        y: 0
    };


    ngOnInit() {
    }

    sendTeleport() {
        console.log("Publish", this.teleport)
        this.subject.next({
            endpoint: ActionsEndpoints.teleport,
            body: JSON.stringify(this.teleport)
        })
    }


    sendMove() {
        console.log("Publish", this.move);
        this.subject.next({
            endpoint: ActionsEndpoints.move,
            body: JSON.stringify(this.move)
        })
    }

    sendCreate() {
        console.log("Publish");
        this.subject.next({
            endpoint: ActionsEndpoints.create,
            body: ""
        })
    }

}
