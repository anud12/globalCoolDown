import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from "@stomp/ng2-stompjs";
import {myRxStompConfig} from "./config/myRxStomp.config";
import {FormsModule} from "@angular/forms";
import {SecurityModule} from "./security/security.module";
import {AppErrorComponent} from './app-error.component';
import {GameModule} from "./game/game.module";
import { ResultComponent } from './result/result.component';

@NgModule({
    declarations: [
        AppComponent,
        AppErrorComponent,
        ResultComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        SecurityModule,
        GameModule
    ],
    providers: [{
        provide: InjectableRxStompConfig,
        useValue: myRxStompConfig
    }, {
        provide: RxStompService,
        useFactory: rxStompServiceFactory,
        deps: [InjectableRxStompConfig]
    }],
    bootstrap: [AppComponent]
})
export class AppModule {
}
