package renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Texture {

    private String filepath;
    private transient int texID;

    private int width, height;

    public Texture(int width, int height) {
        this.filepath = filepath;

        // Generate the texture on GPU
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height,
                0, GL_RGB,GL_UNSIGNED_BYTE, NULL);
    }

    public Texture() {
        texID = -1;
        width = -1;
        height = -1;
    }


    public void init(String filepath) {

        this.filepath = "Generated";

        // Generate the texture
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);


        // Set texture parameters
        // Repeat the image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // When stretching image don't blur
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        // When shrinking image don't blur
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);


        stbi_set_flip_vertically_on_load(true);
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        // Load image
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if (image != null) {

            this.width = width.get(0);
            this.height = height.get(0);

            if (channels.get(0) == 3)
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                        0, GL_RGB,GL_UNSIGNED_BYTE, image);
            else if (channels.get(0) == 4)
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                        0, GL_RGBA,GL_UNSIGNED_BYTE, image);
            else
                assert false : "ERROR: (Texture) Unknown number of channels" + channels.get(0);

        } else {
            assert false : "ERROR: (Texture) Could not load image" + filepath;
        }

        stbi_image_free(image);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Texture)) return false;
        Texture oTex = (Texture)o;
        return oTex.getWidth() == this.width && oTex.getHeight() == this.height
        && oTex.getID() == this.texID
        && oTex.getFilepath().equals(this.filepath);
    }


    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return width;
    }

    public  int getHeight() {
        return height;
    }

    public int getID() {
        return texID;
    }

    public String getFilepath() {
        return this.filepath;
    }

}
