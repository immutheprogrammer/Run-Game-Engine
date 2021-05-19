#type vertex
#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColour;
layout (location = 2) in vec2 aTexCoords;
layout (location = 3) in float aTexID;


uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColour;
out vec2 fTexCoords;
out float fTexID;

void main()
{
    fColour = aColour;
    fTexCoords = aTexCoords;
    fTexID = aTexID;
    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColour;
in vec2 fTexCoords;
in float fTexID;

uniform sampler2D uTextures[8];

out vec4 colour;

void main()
{
    if (fTexID > 0) {
        int id = int(fTexID);
        colour = fColour * texture(uTextures[id], fTexCoords);

        if (colour.w < 0.5) {
            discard;
        }
    } else {
        colour = fColour;

        if (colour.w < 0.1) {
            discard;
        }
    }
}

