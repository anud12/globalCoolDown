import {get} from "/get.js";
import {createProgramWithShadersAnd} from "./util/createProgramWithShadersAnd.js";
import {compileShader} from "./util/compileShader.js";
import {loadObj} from "./util/objLoader/loadObj.js";
import {createBuffer} from "./util/createBuffer.js";

(async () => {
    let gl = document.querySelector("canvas").getContext("webgl");
    let program = createProgramWithShadersAnd(gl,
        compileShader(gl,
            gl.VERTEX_SHADER,
            await get("shader.vert")),
        compileShader(gl,
            gl.FRAGMENT_SHADER,
            await get("shader.frag"))
    )
    .linkAndUseProgram();
    gl.enable(gl.DEPTH_TEST);
    gl.clearColor(0.0, 0.0, 0.0, 1.0);
    const {vertices, colors, normals, indices} = await loadObj("models/ship.obj");
    createBuffer(gl, program, "a_Vertices", new Float32Array(vertices), 3, gl.FLOAT);
    createBuffer(gl, program, "a_Normals", new Float32Array(normals), 3, gl.FLOAT);
    createBuffer(gl, program, "a_Colors", new Float32Array(colors), 3, gl.FLOAT);

    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, gl.createBuffer());
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER,
        new Uint16Array(indices),
        gl.STATIC_DRAW);

    const u_LightDirection = gl.getUniformLocation(program, "u_LightDirection");
    gl.uniform3fv(u_LightDirection, [0, 0, -1]);
    const u_AmbientLight = gl.getUniformLocation(program, "u_AmbientLight");
    gl.uniform3fv(u_AmbientLight, [0.1, 0.1, 0.1]);
    const u_LightColor = gl.getUniformLocation(program, "u_LightColor");
    gl.uniform3f(u_LightColor, 0, 0.5, 0.5);


    const draw = () => {
        const u_Angle = gl.getUniformLocation(program, 'u_Angle');
        gl.uniform3fv(u_Angle, new Float32Array([
            document.querySelector("#rotation-x").value,
            document.querySelector("#rotation-y").value,
            document.querySelector("#rotation-z").value
        ]));


        let u_Position = gl.getUniformLocation(program, "u_Position");
        gl.uniform4fv(u_Position, new Float32Array([0, 0, 0, 1]));

        let u_Scale = gl.getUniformLocation(program, "u_Scale");
        gl.uniform4fv(u_Scale, new Float32Array([0.25, 0.25, 0.25, 1]));

        let u_FragColor = gl.getUniformLocation(program, "u_FragColor");
        gl.uniform4fv(u_FragColor, new Float32Array([0, 0, 0, 1]));

        gl.clear(gl.COLOR_BUFFER_BIT);
        // gl.drawArrays(gl.TRIANGLES, 0, (vertices.length / 3));
        gl.drawElements(gl.TRIANGLES, indices.length, gl.UNSIGNED_SHORT, 0);
        requestAnimationFrame(draw)
    };
    draw();

    console.log("Done")
})();