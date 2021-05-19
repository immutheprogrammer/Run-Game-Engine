package components;

<<<<<<< Updated upstream
<<<<<<< Updated upstream

import editor.JImGui;
=======
=======
>>>>>>> Stashed changes
import imgui.ImGui;
>>>>>>> Stashed changes
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;


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
        Vector4f imColour = new Vector4f(colour.x, colour.y, colour.z, colour.w);
        JImGui.colourPicker4("Colour Picker: ", imColour);
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
            this.isDirty = true;
            this.colour = colour;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setTexture(Texture texture) {
        this.sprite.setTexture(texture);
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}

