import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {WorldRenderComponent} from './world-render/world-render.component';
import {PawnModule} from '../pawn/pawn.module';
import {UiComponent} from './ui.component';
import {InteractiveRenderComponent} from './interactive-render/interactive-render.component';
import {UiService} from './ui.service';
import {SelectListComponent} from './select-list/select-list.component';
import {UserInfoComponent} from './user-info/user-info.component';
import {MapModule} from '../map/map.module';

@NgModule({
    imports: [
        CommonModule,
        PawnModule,
        MapModule
    ],
    providers: [UiService],
    declarations: [WorldRenderComponent, UiComponent, InteractiveRenderComponent, SelectListComponent, UserInfoComponent]
})
export class UiModule {
}
