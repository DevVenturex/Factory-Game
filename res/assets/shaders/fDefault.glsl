#version 330 core

uniform sampler2D texSampler;

in vec4 fColor;
in vec2 fTexCoords;

out vec4 color;

void main() {
    color = texture(texSampler, fTexCoords) * fColor;
}
