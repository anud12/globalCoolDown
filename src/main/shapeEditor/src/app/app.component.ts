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
  pointList: Array<Point> = [];

  constructor() {

  }

  grid(number: number) {
    return Math.round(number / 100) * 100;
  }

  ngAfterViewInit(): void {
    this.glService = new GlService(this.glcanvas.nativeElement);
    const drawCallback = () => {
      this.draw()
      requestAnimationFrame(drawCallback)
    };
    requestAnimationFrame(drawCallback);
    this.glcanvas.nativeElement.addEventListener("mousemove", (event: MouseEvent) => {
      this.mousePointer = {
        x: this.grid(event.offsetX),
        y: this.grid(event.offsetY)
      }
    })
    this.glcanvas.nativeElement.addEventListener("contextmenu", (event: MouseEvent) => {
      event.preventDefault();
      const point = {
        x: this.grid(event.offsetX),
        y: this.grid(event.offsetY)
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
        x: this.grid(event.offsetX),
        y: this.grid(event.offsetY)
      }
      console.log(point);
      this.pointList.push(point)
    })
  }

  draw() {
    this.glService.clear();
    if (this.pointList.length > 0) {
      this.glService.drawPointList(this.pointList, [0.5, 0.5, 0.5, 1]);
    }
    if (this.pointList.length > 1) {
      this.glService.drawPointList([this.pointList[this.pointList.length - 1]], [0, 1, 1, 1]);
    }
    this.glService.drawPointList([this.mousePointer], [0, 1, 0, 1])
  }
}
