package physics2D.primitives;

import org.joml.Vector2f;
import physics2D.rigidbody.RigidBody2D;

public class Circle {
    private float radius = 1.0f;
    private RigidBody2D body2D = null;

    public float getRadius() {
        return this.radius;
    }

    public Vector2f getCenter() {
        return body2D.getPosition();
    }
}
