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
        return this.httpClient.post(SecurityEndpoints.register, user, {withCredentials: true})
    }

    loginUser(user: UserModel) {
        let body = `username=${user.username}`;
        return this.httpClient.post(SecurityEndpoints.login,
            body,
            {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                withCredentials: true,
            }
        )
    }

    getMe() {
        return this.httpClient.get(SecurityEndpoints.getMe, {
            withCredentials: true,
            responseType: 'text' as 'json'
        });
    }
}
