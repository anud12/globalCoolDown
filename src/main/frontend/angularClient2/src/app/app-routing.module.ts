import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {UiComponent} from "./ui/ui.component";
import {LoginComponent} from "./authentication/login/login.component";
import {AuthenticationGuard} from "./authentication/authentication.guard";


const routes: Routes = [
  {path: '', component: UiComponent, canActivate: [AuthenticationGuard]},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: 'login'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
