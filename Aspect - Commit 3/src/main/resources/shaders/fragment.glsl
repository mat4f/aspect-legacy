#version 330

in vec3 position;
in vec3 out_color;

out vec4 gl_Color;

void main() {
    gl_Color = vec4(out_color, 1.0f);
}
