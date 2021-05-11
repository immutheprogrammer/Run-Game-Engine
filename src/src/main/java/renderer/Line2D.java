package renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Line2D {
    private Vector2f start;
    private Vector2f end;
    private Vector3f colour;
    private int lifeTime;

    public Line2D(Vector2f start, Vector2f end, Vector3f colour, int lifeTimeInFrames) {
        this.start = start;
        this.end = end;
        this.colour = colour;
        this.lifeTime = lifeTimeInFrames;
    }

    public int beginFrame() {
        this.lifeTime--;
        return this.lifeTime;
    }

    public Vector2f getStart() {
        return start;
    }

    public Vector2f getEnd() {
        return end;
    }

    public Vector3f getColour() {
        return colour;
    }
}
