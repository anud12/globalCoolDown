import {createProgramWithShadersAnd} from "./util/createProgramWithShadersAnd.function";
import {compileShader} from "./util/compileShader.function";
import {GameObjectModel, LocationTrait, Point2D, RenderTrait} from "../java.models";
import {GameObjectGLService} from "./GameObjectGL.service";
import {drawSelection} from "./util/drawSelection";

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
        this.gl.enable(this.gl.BLEND);
        this.gl.blendFunc(this.gl.ONE, this.gl.ONE_MINUS_SRC_ALPHA);
        this.gl.clearColor(0, 0, 0, 1.0);
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.gl.createBuffer());
    }

    draw(array: Array<GameObjectModel>, camera: { x: number, y: number, scale: number }): void {
        array.forEach(gameObjectModel => {
            const position = this.gameObjectGLService.transformGameObject(gameObjectModel, this.clientRect, camera);
            const n = position.length / 2;

            this.gl.bufferData(this.gl.ARRAY_BUFFER,
                new Float32Array(position),
                this.gl.STATIC_DRAW);

            const a_Position = this.gl.getAttribLocation(this.program, 'a_Position');
            this.gl.vertexAttribPointer(a_Position, 2, this.gl.FLOAT, false, 0, 0);
            this.gl.enableVertexAttribArray(a_Position);

            let u_FragColor = this.gl.getUniformLocation(this.program, "u_FragColor");
            this.gl.uniform4fv(u_FragColor, new Float32Array(this.gameObjectGLService.getPolygonColorArray(gameObjectModel)));
            this.gl.drawArrays(this.gl.TRIANGLE_FAN, 0, n);
            this.gl.drawArrays(this.gl.LINE_LOOP, 0, n);

            u_FragColor = this.gl.getUniformLocation(this.program, "u_FragColor");
            this.gl.uniform4fv(u_FragColor, new Float32Array(this.gameObjectGLService.getVertexColorArray(gameObjectModel)));
            this.gl.drawArrays(this.gl.LINE_LOOP, 0, n);

            u_FragColor = this.gl.getUniformLocation(this.program, "u_FragColor");
            this.gl.uniform4fv(u_FragColor, new Float32Array(this.gameObjectGLService.getVertexColorArray(gameObjectModel)));

            const locationTrait = gameObjectModel.traitMap.LocationTrait as LocationTrait;
            const renderTrait = gameObjectModel.traitMap.RenderTrait as RenderTrait;
            const selection = drawSelection(renderTrait.modelRadius,
                locationTrait.point2D,
                camera,
                this.clientRect);
            this.gl.bufferData(this.gl.ARRAY_BUFFER,
                new Float32Array(selection.points),
                this.gl.STATIC_DRAW);
            this.gl.drawArrays(this.gl.LINE_LOOP, 0, selection.size);
        })
    }

    clear(): void {
        this.gl.clear(this.gl.COLOR_BUFFER_BIT);
    }

}
