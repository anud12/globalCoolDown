export const createProgramWithShadersAnd = (gl,
                                            ...shaderList) => {
    const shaderProgram = gl.createProgram();
    shaderList.forEach(shader => {
        gl.attachShader(shaderProgram, shader)
    });
    return {
        getResult: () => {
            return shaderProgram;
        },
        linkAndUseProgram: () => {
            gl.linkProgram(shaderProgram);
            gl.useProgram(shaderProgram);
            return shaderProgram;
        }
    };
};