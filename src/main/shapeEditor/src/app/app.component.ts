import {Component, ElementRef, ViewChild} from '@angular/core';
import {GlService} from "./opengl/gl.service";
import {applyCameraOffset} from "./opengl/util/applyCameraOffset";

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

  constructor() {

  }

  grid(number: number) {
    return Math.round(number / 100) * 100;
  }

  ngAfterViewInit(): void {
    this.glService = new GlService(this.glcanvas.nativeElement, this.camera);
    const drawCallback = () => {
      this.draw()
      requestAnimationFrame(drawCallback)
    };
    requestAnimationFrame(drawCallback);
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
      const point =  {
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
    if (this.pointList.length > 0) {
      this.glService.drawPointList(this.pointList, [0.5, 0.5, 0.5, 1]);
    }
    if (this.pointList.length > 1) {
      this.glService.drawPointList([this.pointList[this.pointList.length - 1]], [0, 1, 1, 1]);
    }
    this.glService.drawPointList([this.mousePointer, this.mousePointerGrid], [0, 1, 0, 1])
  }
}
