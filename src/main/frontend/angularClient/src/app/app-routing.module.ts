import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {StandardLayoutComponent} from "./standard-layout/standard-layout.component";

const routes:Routes = [
  {path: '', component: StandardLayoutComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
