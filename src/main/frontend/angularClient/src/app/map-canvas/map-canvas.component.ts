import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-map-canvas',
  templateUrl: './map-canvas.component.html',
  styleUrls: ['./map-canvas.component.css']
})
export class MapCanvasComponent implements OnInit {

  constructor() {
  }

  @ViewChild('canvas') canvasRef: ElementRef;
  private canvas : HTMLCanvasElement;
  private width: number;
  private height: number;
  public title = "Hello";

  ngOnInit() {
  }

  ngAfterViewInit() {
    console.log("He");
    this.canvas = this.canvasRef.nativeElement;
    this.height = 450;
    this.width = 300;
    this.canvas.width = this.height;
    this.canvas.height = this.width;
    this.draw();
  }

  draw() {
    console.log(this.canvas);
    if (this.canvas.getContext) {
      const context = this.canvas.getContext('2d');
      //fill in the background
      context.fillStyle = "black";
      context.fillRect(0, 0, this.width, this.height);
      console.log(context);


    }
  }
}
