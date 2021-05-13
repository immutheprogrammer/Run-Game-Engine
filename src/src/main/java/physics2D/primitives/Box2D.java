package physics2D.primitives;

import org.joml.Vector2f;
import physics2D.rigidbody.RigidBody2D;

public class Box2D {
    private Vector2f size = new Vector2f();
    private Vector2f halfSize = new Vector2f();

    private RigidBody2D rigidBody2D;

    public Box2D() {
        this.halfSize = new Vector2f(size).mul(0.5f);
    }

    public Box2D(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max.sub(min));
    }

    public Vector2f getMin() {
        return new Vector2f(this.rigidBody2D.getPosition()).sub(this.halfSize);
    }


    public Vector2f getMax() {
        return new Vector2f(this.rigidBody2D.getPosition().add(this.halfSize));
    }

    public Vector2f[] getVertices() {
        Vector2f min = getMin();
        Vector2f max = getMax();

        Vector2f vertices[] = {
            new Vector2f(min.x, max.y), new Vector2f(min.x, max.y),
            new Vector2f(max.x, min.y), new Vector2f(max.x, max.y)
        };

        if (rigidBody2D.getRotation() != 0.0f) {
            for (Vector2f vert : vertices) {
                // TODO: Implement this
                // Rotates point(Vector2f) about center (Vector2f) by rotation (float in degrees)
                //JMath.rotate(vert, this.rigidBody2D.getPosition(), this.rigidBody2D.getRotation());
            }
        }

        return vertices;
    }

    public RigidBody2D getRigidBody2D() {
        return rigidBody2D;
    }
}
