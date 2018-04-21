import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {PawnService} from '../../pawn/pawn.service';
import {UiService} from '../ui.service';
import {SelectBoxModel} from '../model/select-box.model';
import {DimensionModel} from '../../model/dimension.model';
import {PointModel} from '../../model/point.model';

@Component({
    selector: 'app-interactive-render',
    templateUrl: './interactive-render.component.html',
    styleUrls: ['./interactive-render.component.css']
})
export class InteractiveRenderComponent implements OnInit {

    @ViewChild('canvas') canvasRef: ElementRef;
    public width: number = 0;
    public height: number = 0;
    public fontSizeDifference = 6;
    public pawnDecoratorXOffset = 3;
    public pawnDecoratorYOffset = 1;
    public pawnDecoratorRadius = 5;

    public cursor = '';

    private canvas: HTMLCanvasElement;
    private strokeColor: string;
    private keyboardEvent: KeyboardEvent;
    private selectBox: SelectBoxModel = new SelectBoxModel(false, new PointModel(0, 0), new PointModel(0, 0));

    constructor(private pawnService: PawnService,
                private uiService: UiService,
                @Inject('Window') private window: Window) {
        this.strokeColor = 'green';
    }

    ngOnInit() {
        this.uiService.getDrawObserver().subscribe(() => {
            this.draw();
        });
        this.canvas = this.canvasRef.nativeElement;
        this.onResize();
        this.canvas.addEventListener('contextmenu',
            function (event) {
                event.preventDefault();
            },
            false);
    }

    draw() {
        if (this.canvas.getContext) {
            const context = this.canvas.getContext('2d');
            //fill in the background
            context.clearRect(0, 0, this.width, this.height);


            context.lineWidth = 2;

            this.drawSelectBox(context);
            this.drawSelectDecorators(context);
            this.drawInformation(context);
        }
    }

    private drawSelectBox(context: CanvasRenderingContext2D) {
        if (this.selectBox.isSelected) {
            context.strokeStyle = this.strokeColor;
            context.beginPath();
            context.rect(this.selectBox.start.x,
                this.selectBox.start.y,
                this.selectBox.end.x - this.selectBox.start.x,
                this.selectBox.end.y - this.selectBox.start.y);
            context.stroke();
        }
    }

    private drawSelectDecorators(context: CanvasRenderingContext2D) {
        this.pawnService.getSelectedList().forEach(value => {
            const pawnDecoratorSize = this.uiService.fontSize - this.fontSizeDifference;
            context.strokeStyle = this.strokeColor;
            context.beginPath();
            context.rect(
                (value.point.x ) * this.uiService.coordinateScale - this.pawnDecoratorRadius + this.pawnDecoratorXOffset,
                (value.point.y ) * this.uiService.coordinateScale - this.pawnDecoratorRadius - this.pawnDecoratorYOffset - pawnDecoratorSize,
                pawnDecoratorSize + this.pawnDecoratorRadius * 2,
                pawnDecoratorSize + this.pawnDecoratorRadius * 2 - this.pawnDecoratorYOffset);
            context.stroke();
        });
    }

    private drawInformation(context: CanvasRenderingContext2D) {
        this.pawnService.getSelectedList().forEach(value => {
            context.font = `${this.uiService.fontSize }px gnu-unifont`;
            context.fillStyle = this.strokeColor;
            context.fillText(
                'V:' + value.value as any,
                value.point.x * this.uiService.coordinateScale,
                value.point.y * this.uiService.coordinateScale + this.uiService.fontSize
            );
        });
    }

    onSelect() {
        let dimensionModel = new DimensionModel();
        dimensionModel.start = this.selectBox.start;
        dimensionModel.end = this.selectBox.end;
        this.uiService.select(dimensionModel);
        this.draw();
    }

    onMouseDown($event: MouseEvent) {
        if ($event.button === 0) {
            this.selectBox.isSelected = true;
            this.selectBox.start = {
                x: $event.clientX,
                y: $event.clientY
            };
            this.selectBox.end = {
                x: $event.clientX,
                y: $event.clientY
            };
        }

    }

    onMouseUp($event: MouseEvent) {
        if ($event.button === 0) {
            this.selectBox.isSelected = false;
            this.draw();

            this.onSelect();
        }
    }

    onMouseMove($event: MouseEvent) {
        if (this.selectBox.isSelected) {
            this.selectBox.end = {
                x: $event.clientX,
                y: $event.clientY
            };
            this.draw();
        }
    }

    onResize() {
        this.height = this.window.innerHeight;
        this.width = this.window.innerWidth;
        this.canvas.height = this.height;
        this.canvas.width = this.width;
        this.draw();
    }

    onMouseLeave($event: Event) {
        this.selectBox.isSelected = false;
        this.draw();
    }

    onKeyPress($event: KeyboardEvent) {
        if ($event.shiftKey) {
            this.cursor = 'copy';
        } else {
            this.cursor = '';
        }
        this.keyboardEvent = $event;
    }

    onRightClick($event: MouseEvent) {
        $event.preventDefault();
        $event.stopPropagation();
        $event.stopImmediatePropagation();
        if (this.keyboardEvent != undefined && this.keyboardEvent.shiftKey) {
            this.pawnService.queueMove(new PointModel($event.clientX / this.uiService.coordinateScale,
                $event.clientY / this.uiService.coordinateScale));
        } else {
            this.pawnService.setMove(new PointModel($event.clientX / this.uiService.coordinateScale,
                $event.clientY / this.uiService.coordinateScale));
        }
        return false;
    }


}
