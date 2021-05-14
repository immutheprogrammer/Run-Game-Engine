package input;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import run.Camera;
import run.Window;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseInput {
    private static MouseInput instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private final boolean[] mouseButtonsPressed = new boolean[9];
    private boolean isDragging;

    private Vector2f gameViewportPos = new Vector2f();

    private Vector2f gameViewportSize = new Vector2f();
    private MouseInput() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseInput get() {
        if (MouseInput.instance == null) {
            MouseInput.instance = new MouseInput();
        }

        return instance;
    }

    public static void mousePositionCallback(long window, double xpos, double yPos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = yPos;
        get().isDragging = get().mouseButtonsPressed[0] || get().mouseButtonsPressed[1] || get().mouseButtonsPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < get().mouseButtonsPressed.length) {
                get().mouseButtonsPressed[button] = true;
            }
        }
        else if (action == GLFW_RELEASE) {
            if (button < get().mouseButtonsPressed.length) {
                get().mouseButtonsPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;


    }

    public static void endFrame() {
        get().scrollX = 0.0;
        get().scrollY = 0.0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float)get().xPos;
    }

    public static float getY() {
        return (float)get().yPos;
    }

    public static float getScreenX() {
        float currentX = getX() - get().gameViewportPos.x;
        currentX = (currentX / get().gameViewportSize.x) * Window.getWidth();
        return currentX;
    }

    public static float getScreenY() {
        float currentY = getY() - get().gameViewportPos.y;
        currentY = Window.getHeight() - ((currentY / get().gameViewportSize.y) * Window.getHeight());
        return currentY;
    }

    public static float getOrthoX() {
        float currentX = getX() - get().gameViewportPos.x;
        currentX = (currentX / get().gameViewportSize.x) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);

        Camera camera = Window.getScene().camera();
        Matrix4f viewProjection = new Matrix4f();
        camera.getInverseViewMatrix().mul(camera.getInverseProjectMatrix(), viewProjection);
        tmp.mul(viewProjection);
        currentX = tmp.x;

        return currentX;
    }

    public static float getOrthoY() {
        float currentY = getY() - get().gameViewportPos.y;
        currentY = -((currentY / get().gameViewportSize.y) * 2.0f - 1.0f);
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);

        Camera camera = Window.getScene().camera();
        Matrix4f viewProjection = new Matrix4f();
        camera.getInverseViewMatrix().mul(camera.getInverseProjectMatrix(), viewProjection);
        tmp.mul(viewProjection);
        currentY = tmp.y;

        return currentY;
    }

    public static float getDx() {
        return (float)(get().lastX - get().xPos);
    }

    public static float getDy() {
        return (float)(get().lastY- get().yPos);
    }

    public static float getScrollX() {
        return (float)get().scrollX;
    }

    public static float getScrollY() {
        return (float)get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }


    public static boolean mouseButtonDown(int button) {
        return get().mouseButtonsPressed[button];
    }


    public static double getLastX() {
        return get().lastX;
    }

    public static double getLastY() { return get().lastY; }

    public static void setGameViewportPos(Vector2f gameViewportPos) {
        get().gameViewportPos.set(gameViewportPos);
    }

    public static  void setGameViewportSize(Vector2f gameViewportSize) {
        get().gameViewportSize.set(gameViewportSize);
    }
}
