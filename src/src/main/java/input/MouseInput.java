package input;

import org.joml.Vector4f;
import run.Window;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseInput {
    private static MouseInput instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private final boolean[] mouseButtonsPressed = new boolean[9];
    private boolean isDragging;

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

        // Broken zooming code
        /*

        if (Window.getScene().camera().getProjectionSize().x < 1)
            Window.getScene().camera().getProjectionSize().x = 1;
        else if (Window.getScene().camera().getProjectionSize().y < 1)
            Window.getScene().camera().getProjectionSize().y = 1;
        if (Window.getScene().camera().getProjectionSize().x > 1919)
        Window.getScene().camera().getProjectionSize().x += yOffset * 20;
        Window.getScene().camera().getProjectionSize().y += yOffset * 20;
        */
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

    public static float getOrthoX() {
        float currentX = getX();
        currentX = (currentX / (float) Window.getWidth()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0f, 0f, 1f);
        // Makes our mouse coords form range -1 -> 1
        // to world coords 0 -> 1920
        tmp.mul(Window.getScene().camera().getInverseProjectMatrix()).mul(Window.getScene().camera().getViewMatrix());
        currentX = tmp.x + Window.getScene().camera().position.x * 2;

        return currentX;
    }

    public static float getOrthoY() {
        float currentY = Window.getHeight() - getY();

        currentY = (currentY / (float) Window.getHeight()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(0f, currentY, 0f, 1f);
        // Makes our mouse coords form range -1 -> 1
        // to world coords 0 -> 1080
        tmp.mul(Window.getScene().camera().getInverseProjectMatrix()).mul(Window.getScene().camera().getViewMatrix());
        currentY = tmp.y + Window.getScene().camera().position.y * 2;
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
}
