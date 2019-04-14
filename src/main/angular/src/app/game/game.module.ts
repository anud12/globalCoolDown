import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GameObjectListComponent} from './game-object-list/game-object-list.component';
import {GameObjectActionComponent} from './game-object-action/game-object-action.component';
import {GameObjectViewComponent} from './game-object-view/game-object-view.component';
import {FormsModule} from "@angular/forms";

@NgModule({
    declarations: [GameObjectListComponent, GameObjectActionComponent, GameObjectViewComponent],
    exports: [
        GameObjectListComponent,
        GameObjectActionComponent
    ],
    imports: [
        CommonModule,
        FormsModule
    ]
})
export class GameModule {
}
