package components;

import org.joml.Vector4f;
import run.Component;


public class SpriteRenderer extends Component {

    private Vector4f colour;

    public SpriteRenderer(Vector4f colour) {
        this.colour = colour;
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
}

