import {Injectable} from '@angular/core';
import {LoginModel} from "./login/login.model";
import {HttpClient} from "@angular/common/http";
import 'rxjs/add/operator/map'
import {tokenNotExpired, JwtHelper} from "angular2-jwt";

import {Router} from "@angular/router";
import {AuthenticationModel} from "./authentication.model";

@Injectable()
export class AuthenticationService {

    private jwtHelper: JwtHelper;
    token: string;
    private authenticationModel: AuthenticationModel;
    private url: string = "localhost";
    private authenticationEndpoint = `http://${this.url}:8080/auth`;

    constructor(private router: Router,
                private http: HttpClient) {
        this.jwtHelper = new JwtHelper();
        this.token = localStorage.getItem('token');
    }

    signinUser(loginModel: LoginModel) {
        return this.http.post(this.authenticationEndpoint, loginModel)
            .map(response => {
                let token = response && response['token'];
                if (token) {
                    this.token = token;
                    this.authenticationModel = this.createModel(token);
                    localStorage.setItem('token', token);
                    return true;
                }
                return false;
            });
    }

    logout() {
        this.token = null;
        this.authenticationModel = null;
        localStorage.removeItem('token');
        localStorage.removeItem('currentUser');
        this.router.navigate(['/login']);
    }

    getModel():AuthenticationModel {
        return this.authenticationModel;
    }

    getToken() {
        return this.token
    }

    isAuthenticated() {
        if (localStorage.getItem('token') != null && tokenNotExpired()) {
            this.token = localStorage.getItem('token');
            this.authenticationModel = this.createModel(this.token);
            return true;
        }
        this.router.navigate(['/login']);
        return false;
    }

    createModel(token: string): AuthenticationModel {
        const object: any = this.jwtHelper.decodeToken(token);
        return new AuthenticationModel(
            object.id,
            object.sub,
            object.role
        )
    }
}
