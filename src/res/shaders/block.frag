#version 330 core

uniform sampler2D texture;

in vec2 texCoord;

out vec4 color;

void main() {
  //color = texture(texture, texCoord);
  color = vec4(1.0);
}
