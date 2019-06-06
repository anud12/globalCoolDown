import {createProgramWithShadersAnd} from "./util/createProgramWithShadersAnd.function";
import {compileShader} from "./util/compileShader.function";
import {GameObjectModel, LocationTrait, Point2D, RenderTrait} from "../java.models";
import {GameObjectGLService} from "./GameObjectGL.service";
import {drawSelection} from "./util/drawSelection";
import {GameSettingsService} from "../game/game-settings.service";
import {HttpClient} from "@angular/common/http";

type  glObject = {
    position: Point2D,
    model: Array<number>
}

export class GlService {
    private gl: WebGLRenderingContext;
    private clientRect: ClientRect;
    private program: WebGLProgram;
    private gameObjectGLService = new GameObjectGLService(this.httpClient);

    constructor(canvas: HTMLCanvasElement,
                private httpClient: HttpClient,
                private gameSettingsService: GameSettingsService) {

        const load = async () => {
            this.clientRect = canvas.getBoundingClientRect();
            this.gl = canvas.getContext("webgl");
            const VERTEX_SHADER_SOURCE = await this.gameObjectGLService.getVertexShaderSource()
                .toPromise();
            const FRAGMENT_SHADER_SOURCE = await this.gameObjectGLService.getFragmentShaderSource()
                .toPromise();
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
        };
        load();
    }

    drawSelection(gameObjectModel, camera: { x: number, y: number, scale: number }) {
        if(this.gl === undefined){
            return
        }
        const u_FragColor = this.gl.getUniformLocation(this.program, "u_FragColor");
        this.gl.uniform4fv(u_FragColor, new Float32Array(this.gameObjectGLService.getVertexColorArray(gameObjectModel)));
        const locationTrait = gameObjectModel.traitMap.LocationTrait as LocationTrait;
        const renderTrait = gameObjectModel.traitMap.RenderTrait as RenderTrait;
        const radius = Math.max(renderTrait.modelRadius, this.gameSettingsService.minSelectRadius);


        const selection = drawSelection(radius,
            locationTrait.point2D,
            camera,
            this.clientRect);
        this.gl.bufferData(this.gl.ARRAY_BUFFER,
            new Float32Array(selection.points),
            this.gl.STATIC_DRAW);
        this.gl.drawArrays(this.gl.LINE_LOOP, 0, selection.size);
    }

    drawModel(gameObjectModel: GameObjectModel, camera: { x: number, y: number, scale: number }) {
        if(this.gl === undefined){
            return
        }
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
        this.gl.drawArrays(this.gl.TRIANGLES, 0, n);
        // this.gl.drawArrays(this.gl.LINE_LOOP, 0, n);

        u_FragColor = this.gl.getUniformLocation(this.program, "u_FragColor");
        this.gl.uniform4fv(u_FragColor, new Float32Array(this.gameObjectGLService.getVertexColorArray(gameObjectModel)));
        // this.gl.drawArrays(this.gl.LINE_LOOP, 0, n);
    }


    clear(): void {
        if(this.gl === undefined){
            return
        }
        this.gl.clear(this.gl.COLOR_BUFFER_BIT);
    }

}
