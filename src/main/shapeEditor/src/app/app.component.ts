import {Component, ElementRef, ViewChild} from '@angular/core';
import {GlService} from "./opengl/gl.service";
import {ModelService, Point} from "./model.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'shapeEditor';
  @ViewChild('glcanvas') glcanvas: ElementRef;

  private glService;

  mousePointer: Point = {x: 0, y: 0};
  mousePointerGrid: Point = {x: 0, y: 0};

  private camera = {
    x: 0,
    y: 0,
    scale: 1
  }

  gridPoints: Array<Point> = [];
  gridSize = 50;

  constructor(private modelService: ModelService) {

  }

  generateGrid() {
    const dotNumber = 100;
    for (let x = -dotNumber; x < dotNumber; x++) {
      for (let y = -dotNumber; y < dotNumber; y++) {
        this.gridPoints.push({x: x * this.gridSize, y: y * this.gridSize})
      }
    }
  }

  grid(number: number) {
    return Math.round(number / this.gridSize) * this.gridSize;
  }

  ngAfterViewInit(): void {
    this.glService = new GlService(this.glcanvas.nativeElement, this.camera);
    const drawCallback = () => {
      this.draw()
      requestAnimationFrame(drawCallback)
    };
    requestAnimationFrame(drawCallback);
    this.generateGrid();
    this.glcanvas.nativeElement.addEventListener("mousemove", (event: MouseEvent) => {
      this.mousePointerGrid = {
        x: this.grid((event.offsetX / this.camera.scale) - this.camera.x),
        y: this.grid((event.offsetY / this.camera.scale) - this.camera.y)
      }
      this.mousePointer = {
        x: (event.offsetX / this.camera.scale) - this.camera.x,
        y: (event.offsetY / this.camera.scale) - this.camera.y
      }
    });
    this.glcanvas.nativeElement.addEventListener("wheel", (event: WheelEvent) => {
      this.camera.scale += event.deltaY / 100;
      if (this.camera.scale < 0.10) {
        this.camera.scale = 0.10;
      }
    })
    this.glcanvas.nativeElement.addEventListener("contextmenu", (event: MouseEvent) => {
      event.preventDefault();
      this.modelService.removePoint({
        x: this.grid((event.offsetX / this.camera.scale) - this.camera.x),
        y: this.grid((event.offsetY / this.camera.scale) - this.camera.y)
      })
    })
    this.glcanvas.nativeElement.addEventListener("click", (event: MouseEvent) => {
      event.preventDefault();
      this.modelService.addPoint({
        x: this.grid((event.offsetX / this.camera.scale) - this.camera.x),
        y: this.grid((event.offsetY / this.camera.scale) - this.camera.y)
      })
    })
    this.glcanvas.nativeElement.addEventListener("mousemove", (event: MouseEvent) => {
      if (event.buttons === 1) {
        // this.onLeftClickDrag(event)
      }
      if (event.buttons === 4) {
        this.onRightClickDrag(event)
      }
    })
  }

  onRightClickDrag(event: MouseEvent) {
    this.camera.x += event.movementX / this.camera.scale;
    this.camera.y += event.movementY / this.camera.scale;
  }

  draw() {
    const pointList = this.modelService.getAllPoints();
    this.glService.clear();
    this.glService.drawPoints(this.gridPoints, [0.2, 0.2, 0.2, 1]);
    if (pointList.length > 0) {
      this.glService.drawPointList(pointList, [0, 0.5, 0.51, 1]);
    }
    if (pointList.length > 1) {
      this.glService.drawPointList([pointList[pointList.length - 1]], [1, 1, 0, 1]);
    }
    this.glService.drawPointList([this.mousePointerGrid], [0, 1, 0, 1])
    this.glService.drawPointList([{x: 0, y: 0}], [1, 0, 1, 1])
  }
}
