package run;

import imgui.ImGuiLayer;
import input.MouseInput;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import util.JMath;

public class Camera {
    private Matrix4f projectionMatrix, viewMatrix, inverseProjectMatrix, inverseViewMatrix;
    public Vector2f position;

    private Vector2f projectionSize = new Vector2f(1280f, 720f);


    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.inverseProjectMatrix = new Matrix4f();
        this.inverseViewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection() {
        this.projectionMatrix.identity();
        this.projectionMatrix.ortho(0.0f, projectionSize.x, 0.0f, projectionSize.y, 0.0f, 100.0f);
        this.projectionMatrix.invert(inverseProjectMatrix);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.viewMatrix.identity();
        this.viewMatrix = this.viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp);

        this.viewMatrix.invert(inverseViewMatrix);

        return this.viewMatrix;
    }

    public void panCamera() {
        if (MouseInput.isDragging() && ImGuiLayer.getGameViewWindow().getWantCaptureMouse()) {
            if (MouseInput.getX() < (MouseInput.getLastX() + 4)) {
                this.position.x = JMath.lerp(position.x, position.x + 15.0f, 1.0f);
            }
            if (MouseInput.getX() > (MouseInput.getLastX() - 4)) {
                this.position.x = JMath.lerp(position.x, position.x - 15.0f, 1.0f);
            }
            if (MouseInput.getY() < (MouseInput.getLastY() - 4)) {
                this.position.y = JMath.lerp(position.y, position.y - 15.0f, 1.0f);
            }
            if (MouseInput.getY() > (MouseInput.getLastY() + 4)) {
                this.position.y = JMath.lerp(position.y , position.y + 15.0f, 1.0f);
            }
        }
    }


    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f getInverseProjectMatrix() {
        return this.inverseProjectMatrix;
    }

    public Matrix4f getInverseViewMatrix() {
        return this.inverseViewMatrix;
    }

    public Vector2f getProjectionSize() {
        return projectionSize;
    }

}