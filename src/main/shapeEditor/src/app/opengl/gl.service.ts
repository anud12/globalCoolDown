import {createProgramWithShadersAnd} from "./util/createProgramWithShadersAnd.function";
import {compileShader} from "./util/compileShader.function";
import {convertCoordinatesToGL} from "../../../../angular/src/app/opengl/util/convertCoordinatesToGL.function";

export class GlService {
  private gl: WebGLRenderingContext;
  private clientRect: ClientRect;
  private program: WebGLProgram;

  constructor(canvas: HTMLCanvasElement) {

    this.clientRect = canvas.getBoundingClientRect();
    let VERTEX_SHADER_SOURCE = `
            attribute vec4 a_Position;
            void main() {
                gl_Position = a_Position;
                gl_PointSize = 2.0;
            }
            `;
    let FRAGMENT_SHADER_SOURCE = `
            precision mediump float;
            uniform vec4 u_FragColor;
            void main() {
                gl_FragColor = u_FragColor;
            }
    `;
    this.gl = canvas.getContext("webgl");
    this.program = createProgramWithShadersAnd(this.gl,
      compileShader(this.gl,
        this.gl.VERTEX_SHADER,
        VERTEX_SHADER_SOURCE),
      compileShader(this.gl,
        this.gl.FRAGMENT_SHADER,
        FRAGMENT_SHADER_SOURCE)
    )
      .linkAndUseProgram();
    this.gl.enable(this.gl.BLEND);
    this.gl.blendFunc(this.gl.ONE, this.gl.ONE_MINUS_SRC_ALPHA);
    this.gl.clearColor(0, 0, 0, 1.0);
    this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.gl.createBuffer());
  }


  drawPointList(pointList: Array<{ x: number, y: number }>, color: any) {
    const position = [];
    pointList
      .map(value => convertCoordinatesToGL(value, this.clientRect))
      .map(value => {
        position.push(value.x);
        position.push(value.y);
      });
    const n = position.length / 2;

    this.gl.bufferData(this.gl.ARRAY_BUFFER,
      new Float32Array(position),
      this.gl.STATIC_DRAW);

    const a_Position = this.gl.getAttribLocation(this.program, 'a_Position');
    this.gl.vertexAttribPointer(a_Position, 2, this.gl.FLOAT, false, 0, 0);
    this.gl.enableVertexAttribArray(a_Position);

    let u_FragColor = this.gl.getUniformLocation(this.program, "u_FragColor");
    this.gl.uniform4fv(u_FragColor, new Float32Array(color));
    this.gl.drawArrays(this.gl.TRIANGLE_FAN, 0, n);
    this.gl.drawArrays(this.gl.LINE_LOOP, 0, n);
    this.gl.drawArrays(this.gl.POINTS, 0, n);
  }


  clear(): void {
    this.gl.clear(this.gl.COLOR_BUFFER_BIT);
  }

}
