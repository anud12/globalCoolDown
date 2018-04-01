import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {WorldRenderComponent} from './world-render/world-render.component';
import {PawnModule} from "../pawn/pawn.module";
import {UiComponent} from './ui.component';
import {InteractiveRenderComponent} from './interactive-render/interactive-render.component';
import {UiService} from "./ui.service";
import { SelectListComponent } from './select-list/select-list.component';

@NgModule({
  imports: [
    CommonModule,
    PawnModule
  ],
  providers: [UiService],
  declarations: [WorldRenderComponent, UiComponent, InteractiveRenderComponent, SelectListComponent]
})
export class UiModule {
}
