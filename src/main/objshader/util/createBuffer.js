export const createBuffer = (gl, program, attributeName, data, size, type) => {
    const buffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
    gl.bufferData(gl.ARRAY_BUFFER,
        new Float32Array(data),
        gl.STATIC_DRAW);

    const attribute = gl.getAttribLocation(program, attributeName);
    gl.vertexAttribPointer(attribute, size, type, false, 0, 0);
    gl.enableVertexAttribArray(attribute);
};