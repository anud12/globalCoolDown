attribute vec4 a_Vertices;
attribute vec4 a_Normals;
attribute vec4 a_Colors;


uniform vec3 u_Angle;
uniform vec4 u_Position;
uniform vec4 u_Scale;

uniform vec3 u_LightColor;
uniform vec3 u_LightDirection;
uniform vec3 u_AmbientLight;
varying vec4 v_Color;
void position();
void light();

void main() {
    position();
    light();
}

void light(){

    //Color
    vec3 normal = normalize(vec3(a_Normals));
    float nDotL = max(dot(u_LightDirection, normal), 0.0);
    vec3 diffuse = u_LightColor * vec3(a_Colors) * nDotL;
    vec3 ambient = u_AmbientLight * a_Colors.rgb;
    v_Color = vec4(diffuse + ambient, a_Colors.a);
}

void position (){

    mat4 zRotationMatrix = mat4(
    cos(radians(u_Angle.z)), sin(radians(u_Angle.z)), 0, 0,
    -sin(radians(u_Angle.z)), cos(radians(u_Angle.z)), 0, 0,
    0, 0, 1, 0,
    0, 0, 0, 1
    );

    mat4 xRotationMatrix = mat4(
    1, 0, 0, 0,
    0, cos(radians(u_Angle.x)), sin(radians(u_Angle.x)), 0,
    0, -sin(radians(u_Angle.x)), cos(radians(u_Angle.x)), 0,
    0, 0, 0, 1
    );

    mat4 yRotationMatrix = mat4(
    cos(radians(u_Angle.y)), 0, -sin(radians(u_Angle.y)), 0,
    0, 1, 0, 0,
    sin(radians(u_Angle.y)), 0, cos(radians(u_Angle.y)), 0,
    0, 0, 0, 1
    );

    mat4 translationMatrix = mat4(
    1, 0, 0, u_Position.x,
    0, 1, 0, u_Position.y,
    0, 0, 1, u_Position.z,
    0, 0, 0, u_Position.w
    );

    mat4 scaleMatrix = mat4(
    u_Scale.x, 0, 0, 0,
    0, u_Scale.y, 0, 0,
    0, 0, u_Scale.z, 0,
    0, 0, 0, u_Scale.w
    );

    gl_Position =  a_Vertices
    * zRotationMatrix
    * xRotationMatrix
    * yRotationMatrix
    * scaleMatrix
    * translationMatrix
    ;

    gl_PointSize = 10.0;
}