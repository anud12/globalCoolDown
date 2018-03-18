import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {PawnService} from "../../pawn/pawn.service";
import {UiService} from "../ui.service";
import {SelectBoxModel} from "../model/select-box.model";
import {DimensionModel} from "../../model/dimension.model";
import {PointModel} from "../../model/point.model";

@Component({
  selector: 'app-interactive-render',
  templateUrl: './interactive-render.component.html',
  styleUrls: ['./interactive-render.component.css']
})
export class InteractiveRenderComponent implements OnInit {

  @ViewChild('canvas') canvasRef: ElementRef;
  private canvas: HTMLCanvasElement;
  private strokeColor: string;
  public width: number = 0;
  public height: number = 0;
  public pawnDecoratorSize = 14;
  public pawnDecoratorPadding = 2;
  private selectBox: SelectBoxModel = new SelectBoxModel(false, new PointModel(0, 0), new PointModel(0, 0));

  constructor(private pawnService: PawnService,
              private uiService: UiService,
              @Inject('Window') private window: Window) {
    this.strokeColor = "gray";
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.uiService.getDrawObserver().subscribe(() => {
      this.draw();
    });
    this.canvas = this.canvasRef.nativeElement;
    this.onResize();
    this.canvas.addEventListener('contextmenu', function(event){
      event.preventDefault();
    }, false);
  }

  draw() {
    if (this.canvas.getContext) {
      const context = this.canvas.getContext('2d');
      //fill in the background
      context.clearRect(0, 0, this.width, this.height);

      context.strokeStyle = this.strokeColor;
      context.lineWidth = 2;

      this.drawSelectBox(context);
      this.drawSelectDecorators(context)
    }
  }

  drawSelectBox(context: CanvasRenderingContext2D) {
    if (this.selectBox.isSelected) {
      context.beginPath();
      context.rect(this.selectBox.start.x,
        this.selectBox.start.y,
        this.selectBox.end.x - this.selectBox.start.x,
        this.selectBox.end.y - this.selectBox.start.y);
      context.stroke();
    }
  }

  drawSelectDecorators(context: CanvasRenderingContext2D) {
    this.pawnService.getSelectedList().forEach(value => {
      context.beginPath();
      context.rect(
        (value.point.x ) * this.uiService.coordinateScale - this.pawnDecoratorPadding,
        (value.point.y ) * this.uiService.coordinateScale - this.pawnDecoratorSize - this.pawnDecoratorPadding,
        this.pawnDecoratorSize + this.pawnDecoratorPadding * 2,
        this.pawnDecoratorSize + this.pawnDecoratorPadding * 2);
      context.stroke();
    })
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

  onRightClick($event: MouseEvent) {
    $event.preventDefault();
    $event.stopImmediatePropagation();
    this.pawnService.moveRequest(this.pawnService.getSelectedList(),
      new PointModel($event.clientX / this.uiService.coordinateScale, $event.clientY / this.uiService.coordinateScale));
  }
}
