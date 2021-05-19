package physics2dtmp.rigidbody;

import org.joml.Vector2f;
import physics2dtmp.primitives.AABB;
import physics2dtmp.primitives.Box2D;
import physics2dtmp.primitives.Circle;
import physics2dtmp.primitives.Collider2D;
import util.JMath;

public class Collisions {
    public static CollisionManifold findCollisionFeatures(Collider2D c1, Collider2D c2) {
        if (c1 instanceof Circle && c2 instanceof Circle) {
            return findCollisionFeatures((Circle)c1, (Circle)c2);
        } else if (c1 instanceof Box2D && c2 instanceof Box2D) {
            return findCollisionFeatures((Box2D)c1, (Box2D)c2);
        } else if (c1 instanceof Circle && c2 instanceof Box2D) {
            return new CollisionManifold();
            //return findCollisionFeatures((Circle)c1, (Box2D)c2);
        } else if (c1 instanceof Box2D && c2 instanceof Circle) {
            return new CollisionManifold();
            //return findCollisionFeatures((Box2D)c1, (Circle)c2);
        } else {
            assert false : "Unknown collider '" + c1.getClass() + "' vs '" + c2.getClass() + "'";
        }

        return null;
    }

    public static CollisionManifold findCollisionFeatures(Circle a, Circle b) {
        CollisionManifold result = new CollisionManifold();
        float sumRadii = a.getRadius() + b.getRadius();
        Vector2f distance = new Vector2f(b.getCenter()).sub(a.getCenter());
        if (distance.lengthSquared() - (sumRadii * sumRadii) > 0) {
            return result;
        }

        // Multiply by 0.5 because we want to separate each circle the same
        // amount. Consider updating to factor in the momentum and velocity
        float depth = Math.abs(distance.length() - sumRadii) * 0.5f;
        Vector2f normal = new Vector2f(distance);
        normal.normalize();
        float distanceToPoint = a.getRadius() - depth;
        Vector2f contactPoint = new Vector2f(a.getCenter()).add(
                new Vector2f(normal).mul(distanceToPoint));

        result = new CollisionManifold(normal, depth);
        result.addContactPoint(contactPoint);
        return result;
    }

    public static CollisionManifold findCollisionFeatures(Box2D b1, Box2D b2) {
        CollisionManifold result = new CollisionManifold();
        Vector2f axesToTest[] = {
                new Vector2f(0, 1), new Vector2f(1, 0),
                new Vector2f(0, 1), new Vector2f(1, 0)
        };

        JMath.rotate(axesToTest[0], b1.getRigidbody().getRotation(), new Vector2f());
        JMath.rotate(axesToTest[1], b2.getRigidbody().getRotation(), new Vector2f());
        JMath.rotate(axesToTest[2], b2.getRigidbody().getRotation(), new Vector2f());
        JMath.rotate(axesToTest[3], b2.getRigidbody().getRotation(), new Vector2f());

        boolean found = false;
        result.setDepth(Float.MAX_VALUE);
        for (int i=0; i < axesToTest.length; i++) {
            float depth = penetrationDepth(getInterval(b1, axesToTest[i]), getInterval(b2, axesToTest[i]));
            if (depth <= 0.0f) {
                return result;
            } else if (depth < result.getDepth()) {
                found = true;
                result.setDepth(depth);
                result.setNormal(axesToTest[i].mul(-1));
            }
        }

        if (!found) {
            return result;
        }

        result.addContactPoint(new Vector2f());
        result.setColliding();
        return result;
    }

    // =============================================================================
    // SAT helpers
    // =============================================================================
    private static float penetrationDepth(Vector2f interval1, Vector2f interval2) {
        boolean colliding = (interval2.x <= interval1.y) && (interval1.x <= interval2.y);
        if (!colliding) {
            return 0.0f;
        }

        float len1 = interval1.y - interval1.x;
        float len2 = interval2.y - interval2.x;

        float min = Math.min(interval1.x, interval2.x);
        float max = Math.max(interval1.y, interval2.y);

        float length = max - min;

        return (len1 + len2) - length;
    }

    private static Vector2f getInterval(AABB rect, Vector2f axis) {
        Vector2f result = new Vector2f(0, 0);

        Vector2f min = rect.getMin();
        Vector2f max = rect.getMax();

        Vector2f vertices[] = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
                new Vector2f(max.x, min.y), new Vector2f(max.x, max.y)
        };

        result.x = axis.dot(vertices[0]);
        result.y = result.x;
        for (int i=1; i < 4; i++) {
            float projection = axis.dot(vertices[i]);
            if (projection < result.x) {
                result.x = projection;
            }
            if (projection > result.y) {
                result.y = projection;
            }
        }
        return result;
    }

    private static Vector2f getInterval(Box2D rect, Vector2f axis) {
        Vector2f result = new Vector2f(0, 0);

        Vector2f vertices[] = rect.getVertices();

        result.x = axis.dot(vertices[0]);
        result.y = result.x;
        for (int i=1; i < 4; i++) {
            float projection = axis.dot(vertices[i]);
            if (projection < result.x) {
                result.x = projection;
            }
            if (projection > result.y) {
                result.y = projection;
            }
        }
        return result;
    }

    private class ProjectionResult {
        public Vector2f axis;
        public float depth;
    }
}
