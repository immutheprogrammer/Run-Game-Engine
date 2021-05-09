package components;


import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;
import run.Transform;
import run.scenes.LevelEditorScene;

import java.util.ArrayList;


public class SpriteRenderer extends Component {

    private Vector4f colour = new Vector4f(1f, 1f, 1f, 1f);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = true;


    @Override
    public void start() {
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void imgui() {
        float[] imColour = {colour.x, colour.y, colour.z, colour.w};
        if (ImGui.colorPicker4("Colour Picker: ", imColour)) {
            this.colour.set(imColour[0], imColour[1], imColour[2], imColour[3]);
            this.isDirty = true;
        }
    }

    public Vector4f getColour() {
        return this.colour;
    }

    public Texture getTexture() {
        return this.sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setColour(Vector4f colour) {
        if (this.colour.equals(colour)) {
            this.isDirty = true;
            this.colour.set(colour);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}

