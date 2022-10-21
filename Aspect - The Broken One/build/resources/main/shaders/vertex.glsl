#version 330 core

layout (location=0) in vec3 in_position;
layout (location=1) in vec3 in_color;

out vec3 position;
out vec3 out_color;

uniform mat4 projection;
uniform mat4 worldMatrix;

void main() {
    gl_Position = projection * worldMatrix * vec4(in_position, 1.0);
    position = in_position;
    out_color = in_color;
}