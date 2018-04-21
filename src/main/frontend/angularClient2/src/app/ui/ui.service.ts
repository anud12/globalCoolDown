import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {PawnService} from '../pawn/pawn.service';
import {DimensionModel} from '../model/dimension.model';

@Injectable()
export class UiService {


    private observer: Subject<null>;
    public coordinateScale: number;
    public fontSize: number;

    constructor(private pawnService: PawnService) {
        this.coordinateScale = 1 / 100;
        this.fontSize = 18;
        this.observer = new Subject();
        this.pawnService.getPawnStompSubscription().subscribe(() => {
            this.draw();
        });
    }


    draw() {
        this.observer.next();
    }

    getDrawObserver(): Observable<null> {
        return this.observer.asObservable();
    }

    select(dimensionModel: DimensionModel) {

        dimensionModel.start.x -= this.fontSize / 2;
        dimensionModel.start.y += this.fontSize / 2;
        dimensionModel.end.x -= this.fontSize / 2;
        dimensionModel.end.y += this.fontSize / 2;

        dimensionModel.start.x = dimensionModel.start.x / this.coordinateScale;
        dimensionModel.start.y = dimensionModel.start.y / this.coordinateScale;
        dimensionModel.end.x = dimensionModel.end.x / this.coordinateScale;
        dimensionModel.end.y = dimensionModel.end.y / this.coordinateScale;

        let sortedDimension = new DimensionModel();
        if (dimensionModel.start.x < dimensionModel.end.x) {
            sortedDimension.start.x = dimensionModel.start.x;
            sortedDimension.end.x = dimensionModel.end.x;
        } else {
            sortedDimension.start.x = dimensionModel.end.x;
            sortedDimension.end.x = dimensionModel.start.x;
        }

        if (dimensionModel.start.y < dimensionModel.end.y) {
            sortedDimension.start.y = dimensionModel.start.y;
            sortedDimension.end.y = dimensionModel.end.y;
        } else {
            sortedDimension.start.y = dimensionModel.end.y;
            sortedDimension.end.y = dimensionModel.start.y;
        }
        this.pawnService.selectAllByDimension(sortedDimension);
    }
}
