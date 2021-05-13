package physics2D.primitives;

import org.joml.Vector2f;
import physics2D.rigidbody.RigidBody2D;

// Axis Aligned Bounding Box
// aka a not rotated box
public class AABB {
    private Vector2f size = new Vector2f();
    private RigidBody2D rigidBody2D;
    private Vector2f halfSize = new Vector2f();

    public AABB() {
        this.halfSize = new Vector2f(size).mul(0.5f);
    }

    public AABB(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max.sub(min));
    }

    public Vector2f getMin() {
        return new Vector2f(this.rigidBody2D.getPosition()).sub(this.halfSize);
    }


    public Vector2f getMax() {
        return new Vector2f(this.rigidBody2D.getPosition().add(this.halfSize));
    }
}
