import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SecurityEndpoints} from "../endpoints/security.http.endpoint";
import {UserModel} from "../java.models";

@Injectable({
    providedIn: 'root'
})
export class SecurityService {

    constructor(private httpClient: HttpClient) {
    }

    registerUser(user: UserModel) {
        return this.httpClient.post(SecurityEndpoints.register, user)
    }

    loginUser(user: UserModel) {
        return this.httpClient.post(SecurityEndpoints.login, user)
    }

    getMe() {
        return this.httpClient.get(SecurityEndpoints.getMe);
    }
}
