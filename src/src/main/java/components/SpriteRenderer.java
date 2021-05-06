package components;

import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;
import run.Component;


public class SpriteRenderer extends Component {

    private Vector4f colour;
    private Vector2f[] texCoords;
    private Texture texture;

    public SpriteRenderer(Vector4f colour) {
        this.colour = colour;
        this.texture = null;
    }

    public SpriteRenderer(Texture texture) {
        this.texture = texture;
        this.colour = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void start() {
    }

    @Override
    public void update(float dt) {

    }

    public Vector4f getColour() {
        return this.colour;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2f[] getTexCoords() {
        Vector2f[] texCoords = {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };

        return texCoords;
    }
}

