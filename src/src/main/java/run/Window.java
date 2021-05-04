package run;


import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import run.Input.KeyListener;
import run.Input.MouseListener;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    int width, height;
    String title;

    

    private static Window windowInstance;
    private long window;

    private Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public static Window get(int width, int height, String title) {
        if (Window.windowInstance == null) {
            Window.windowInstance = new Window(width, height, title);
        }

        return Window.windowInstance;
    }

    private void init() {
        // Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();
        // Init GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to init GLFW");
        }

        // Config GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (window == NULL) {
            throw new IllegalStateException("Failed to create the glfwWindow");
        }

        // Sets the mouse callbacks
        glfwSetCursorPosCallback(window, MouseListener::mousePositionCallback);
        glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the memory allocated
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            // Poll events
            glfwPollEvents();

            // Change the colour of the screen
            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            // Clears the window
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                System.out.println("Space key is pressed!");
            }

            glfwSwapBuffers(window);
        }
    }
}
