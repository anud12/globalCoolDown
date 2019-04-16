import {createProgramWithShadersAnd} from "./util/createProgramWithShadersAnd.function";
import {compileShader} from "./util/compileShader.function";
import {GameObjectModel, Point2D} from "../java.models";
import {GameObjectGLService} from "./GameObjectGL.service";

type  glObject = {
    position: Point2D,
    model: Array<number>
}

export class GlService {
    private gl: WebGLRenderingContext;
    private clientRect: ClientRect;
    private program: WebGLProgram;
    private gameObjectGLService = new GameObjectGLService();

    constructor(canvas: HTMLCanvasElement) {

        this.clientRect = canvas.getBoundingClientRect();
        let VERTEX_SHADER_SOURCE = this.gameObjectGLService.getVertexShaderSource();
        let FRAGMENT_SHADER_SOURCE = this.gameObjectGLService.getFragmentShaderSource();
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
        this.gl.clearColor(0, 0, 0, 1.0);
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.gl.createBuffer());
    }

    draw(array: Array<GameObjectModel>): void {
        array.forEach(gameObjectModel => {
            const position = this.gameObjectGLService.transformGameObject(gameObjectModel, this.clientRect);
            const n = position.length / 2;

            this.gl.bufferData(this.gl.ARRAY_BUFFER,
                new Float32Array(position),
                this.gl.STATIC_DRAW);

            const a_Position = this.gl.getAttribLocation(this.program, 'a_Position');
            this.gl.vertexAttribPointer(a_Position, 2, this.gl.FLOAT, false, 0, 0);
            this.gl.enableVertexAttribArray(a_Position);

            let u_FragColor = this.gl.getUniformLocation(this.program, "u_FragColor");
            this.gl.uniform4fv(u_FragColor, new Float32Array(this.gameObjectGLService.getColorArray(gameObjectModel)));

            this.gl.drawArrays(this.gl.LINE_LOOP, 0, n);

        })
    }

    clear(): void {
        this.gl.clear(this.gl.COLOR_BUFFER_BIT);
    }

}
