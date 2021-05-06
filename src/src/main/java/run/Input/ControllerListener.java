package run.Input;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class ControllerListener {

    private static boolean presentState;
    private static FloatBuffer axes;
    private static ByteBuffer buttons;


    public static float getAxis(int joystick, int axis) {
        presentState = glfwJoystickPresent(joystick);

        if (presentState) {
            axes = glfwGetJoystickAxes(joystick);
            return axes.get(axis);
        }
        else if (!presentState) {
            System.out.println("Please Connect a Controller");
        }
        return 0;
    }


    public static boolean isButtonDown(int joystick, int button) {
        presentState = glfwJoystickPresent(joystick);
        if (presentState) {
            buttons = glfwGetJoystickButtons(joystick);
            if (buttons.get(button) != GLFW_RELEASE)
                return true;
        } else if (!presentState) {
            System.out.println("Please Connect a Controller");
        }
        return false;
    }

    public static boolean isButtonReleased(int joystick, int button) {
        presentState = glfwJoystickPresent(joystick);
        if (presentState) {
            buttons = glfwGetJoystickButtons(joystick);
            if (buttons.get(button) == GLFW_RELEASE)
                return true;
        } else if (!presentState) {
            System.out.println("Please Connect a Controller");
        }
        return false;
    }




}
