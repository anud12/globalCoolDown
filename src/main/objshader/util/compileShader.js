export const compileShader = (gl,
                              shaderType,
                              code) => {
    let shader = gl.createShader(shaderType);
    gl.shaderSource(shader, code);
    gl.compileShader(shader);
    return shader;
};
