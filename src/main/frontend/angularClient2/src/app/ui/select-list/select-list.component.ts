import {Component, OnInit} from '@angular/core';
import {PawnModel} from "../../pawn/model/pawn.model";
import {PawnService} from "../../pawn/pawn.service";

@Component({
    selector: 'app-select-list',
    templateUrl: './select-list.component.html',
    styleUrls: ['./select-list.component.css']
})
export class SelectListComponent implements OnInit {

    public selectedPawnList: Array<PawnModel>;

    constructor(private pawnService: PawnService) {

    }

    ngOnInit() {
        this.pawnService.getPawnSelectSubscription().subscribe(() => {
            this.selectedPawnList = [];
            this.pawnService.getSelectedList().forEach((value: any, key) => {
                const code = value.characterCode;
                value.character = String.fromCharCode(code);
                this.selectedPawnList.push(value);
            })
        })
    }

    deselect(pawn: PawnModel) {
        this.pawnService.deselect(pawn)
    }

}
