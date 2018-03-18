import {Component, ElementRef, Inject, ViewChild} from '@angular/core';
import {PawnService} from "../../pawn/pawn.service";
import {UiService} from "../ui.service";

@Component({
  selector: 'app-world-render',
  templateUrl: './world-render.component.html',
  styleUrls: ['./world-render.component.css']
})
export class WorldRenderComponent {


  constructor(private pawnService: PawnService,
              private uiService: UiService,
              @Inject('Window') private window: Window) {
  }

  @ViewChild('canvas') canvasRef: ElementRef;
  private canvas: HTMLCanvasElement;
  public width: number = 0;
  public height: number = 0;

  ngAfterViewInit() {
    this.uiService.getDrawObserver().subscribe(() => {
      this.draw();
    });
    this.canvas = this.canvasRef.nativeElement;
    this.onResize();
  }

  draw() {
    if (this.canvas.getContext) {
      const context = this.canvas.getContext('2d');
      //fill in the background
      context.fillStyle = "#eeeeee";
      context.fillRect(0, 0, this.width, this.height);

      this.pawnService.getListById().forEach(value => {
        context.font = "20px monospace";
        context.fillStyle = `black`;
        context.fillText(
          String.fromCharCode(value.characterCode) as any,
          value.point.x * this.uiService.coordinateScale,
          value.point.y * this.uiService.coordinateScale
        );
      });

    }
  }

  onResize() {
    this.height = this.window.innerHeight;
    this.width = this.window.innerWidth;
    this.canvas.height = this.height;
    this.canvas.width = this.width;
    this.draw();
  }
}
