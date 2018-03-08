import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StandardLayoutComponent} from "./standard-layout.component";
import {PawnListComponent} from "../pawn-list/pawn-list.component";
import {CanvasComponent} from "../canvas/canvas.component";

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    StandardLayoutComponent,
    PawnListComponent,
    CanvasComponent]
})
export class StandardLayoutModule {

}
