import {Component, ElementRef, ViewChild} from '@angular/core';
import {GlService} from "./opengl/gl.service";

interface Point {
  x: number,
  y: number
}

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

  pointList: Array<Point> = [];
  gridPoints: Array<Point> = [];
  gridSize = 50;

  constructor() {

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
    })
    this.glcanvas.nativeElement.addEventListener("contextmenu", (event: MouseEvent) => {
      event.preventDefault();
      const point = {
        x: this.grid((event.offsetX / this.camera.scale) - this.camera.x),
        y: this.grid((event.offsetY / this.camera.scale) - this.camera.y)
      }
      this.pointList = this.pointList.filter(value => {
        return !(
          (value.x === point.x)
          && (value.y === point.y)
        )
      })
      console.log(this.pointList);
    })
    this.glcanvas.nativeElement.addEventListener("click", (event: MouseEvent) => {
      event.preventDefault();
      const point = {
        x: this.grid((event.offsetX / this.camera.scale) - this.camera.x),
        y: this.grid((event.offsetY / this.camera.scale) - this.camera.y)
      }
      console.log(point);
      this.pointList.push(point)
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
    this.glService.clear();
    this.glService.drawPoints(this.gridPoints, [0.2, 0.2, 0.2, 1]);
    if (this.pointList.length > 0) {
      this.glService.drawPointList(this.pointList, [0, 0.5, 0.51, 1]);
    }
    if (this.pointList.length > 1) {
      this.glService.drawPointList([this.pointList[this.pointList.length - 1]], [1, 1, 0, 1]);
    }
    this.glService.drawPointList([ this.mousePointerGrid], [0, 1, 0, 1])
  }
}
