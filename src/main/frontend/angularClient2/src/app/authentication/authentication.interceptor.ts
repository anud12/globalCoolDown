import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest,
    HttpResponse
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {forwardRef, Inject, Injectable} from '@angular/core';
import {Router} from "@angular/router";
import 'rxjs/add/operator/do';
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

    constructor(private router: Router,
                @Inject(forwardRef(() => AuthenticationService)) private authenticationService: AuthenticationService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // const copiedReq = req.clone({headers: req.headers.set('', '')});
        let token = this.authenticationService.getToken();
        const copiedReq = req.clone({headers: req.headers.set('X-Auth-Token', token ? token : '')});
        return next.handle(copiedReq).do((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
            }
        }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
                if (err.status === 401) {
                    // redirect to the login route
                    this.router.navigate(['/login']);
                }
            }
        });
    }
}
