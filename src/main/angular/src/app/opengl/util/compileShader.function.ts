export const compileShader = (gl: WebGLRenderingContext,
                              shaderType: any,
                              code: string) => {
  var shader = gl.createShader(shaderType);
  gl.shaderSource(shader, code);
  gl.compileShader(shader);
  return shader;
};
