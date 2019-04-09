import {createProgramWithShadersAnd} from "./util/createProgramWithShadersAnd.function";
import {compileShader} from "./util/compileShader.function";
import {convertCoordinatesToGL} from "./util/convertCoordinatesToGL.function";

export class GlService {
  private gl: WebGLRenderingContext;
  private clientRect: ClientRect
  private program: WebGLProgram;

  constructor(canvas: HTMLCanvasElement) {
    this.clientRect = canvas.getBoundingClientRect();
    let VERTEX_SHADER_SOURCE = `
    attribute vec4 a_Position;
    void main() {
        gl_Position = a_Position;
        gl_PointSize = 3.0;
    }
    `;
    let FRAGMENT_SHADER_SOURCE = `
    void main() {
        gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
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
    this.gl.clearColor(0.0, 0.0, 0.0, 1.0);
    this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.gl.createBuffer());
  }

  draw(array: Array<{ x: number, y: number }>): void {
    var floatArray = array
      .map(value => convertCoordinatesToGL(value, this.clientRect))
      .map(value => [value.x, value.y])
      .reduce((previousValue, currentValue) => previousValue.concat(currentValue));

    var n = floatArray.length / 2;

    const a_Position = this.gl.getAttribLocation(this.program, 'a_Position');

    this.gl.bufferData(this.gl.ARRAY_BUFFER,
      new Float32Array(floatArray),
      this.gl.STATIC_DRAW);
    this.gl.vertexAttribPointer(a_Position, 2, this.gl.FLOAT, false, 0, 0);
    this.gl.enableVertexAttribArray(a_Position);

    this.gl.drawArrays(this.gl.POINTS, 0, n);
  }

  clear(): void {
    this.gl.clear(this.gl.COLOR_BUFFER_BIT);
  }

}
