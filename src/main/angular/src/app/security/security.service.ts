import {Injectable} from '@angular/core';
import {UserModel} from "../java.models";
import {UserWsEndpoints} from "../endpoints/user.ws.endpoint";
import {StompService} from "../stomp.service";
import {ReplaySubject, Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class SecurityService {

    private tokenSubject: Subject<string> = new ReplaySubject<string>();

    constructor(private stompService: StompService) {
        this.stompService.subscribeGlobal(UserWsEndpoints.token, response => {
            console.log("Updated token :" + response);
            this.tokenSubject.next(response as string)
        })
    }

    onTokenChange() {
        return this.tokenSubject;
    }

    registerUser(user: UserModel) {
        this.stompService.publish(UserWsEndpoints.register, JSON.stringify(user))
    }

    loginUser(user: UserModel) {
        this.stompService.publish(UserWsEndpoints.login, JSON.stringify(user))
    }
}
