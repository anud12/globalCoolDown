import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from "@stomp/ng2-stompjs";
import {myRxStompConfig} from "./config/myRxStomp.config";
import {GameObjectComponent} from './game-object/game-object.component';
import {FormsModule} from "@angular/forms";
import {ActionComponent} from "./game-components/action/action.component";
import {SecurityModule} from "./security/security.module";

@NgModule({
  declarations: [
      AppComponent,
      GameObjectComponent,
      ActionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    SecurityModule
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
