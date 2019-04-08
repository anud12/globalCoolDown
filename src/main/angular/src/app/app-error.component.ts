import {Component, OnInit} from '@angular/core';
import {StompService} from "./stomp.service";
import {SecurityService} from "./security/security.service";

@Component({
    selector: 'app-error',
    templateUrl: './app-error.component.html',
    styleUrls: ['./app-error.component.scss']
})
export class AppErrorComponent implements OnInit {

    constructor(private stompService: StompService,
                private securityService: SecurityService) {
    }

    errors: Array<string> = [];

    ngOnInit() {
        this.stompService.subscribeGlobal<string>("/ws/error", response => {
            this.errors.push(response)
        })
        this.securityService.onTokenChange().subscribe(value => {
            this.stompService.subscribePersonal<string>("/ws/error", value, response => {
                this.errors.push(response)
            })
        })
    }

    clear(error: string) {
        this.errors = this.errors.filter(value => value != error)
    }

}
