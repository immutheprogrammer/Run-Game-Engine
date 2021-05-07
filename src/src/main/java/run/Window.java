package run;


import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import run.im_gui.ImGuiLayer;
import run.input.KeyListener;
import run.input.MouseListener;
import run.scenes.LevelEditorScene;
import run.scenes.LevelScene;
import run.scenes.Scene;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    int width, height;
    String title;

    public float r = 1.0f;
    public float g = 1.0f;
    public float b = 1.0f;
    public float a = 1.0f;

    private static Window windowInstance;
    private long window;
    private ImGuiLayer imGuiLayer;

    private static Scene currentScene;

    private Window() {
        this.width = 1280;
        this.height = 720;
        this.title = "Run Engine Tests";
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : "Unknown Scene " + newScene;
                break;
        }
    }

    public static Window get() {
        if (Window.windowInstance == null) {
            Window.windowInstance = new Window();
        }

        return Window.windowInstance;
    }

    public static Scene getScene() {
        return get().currentScene;
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

        window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (window == NULL) {
            throw new IllegalStateException("Failed to create the glfwWindow");
        }

        // Sets the mouse callbacks
        glfwSetCursorPosCallback(window, MouseListener::mousePositionCallback);
        glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(window, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

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

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        this.imGuiLayer = new ImGuiLayer(window);
        this.imGuiLayer.initImGui();

        Window.changeScene(0);
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
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        while (!glfwWindowShouldClose(window)) {
            // Poll events
            glfwPollEvents();

            // Change the colour of the screen
            glClearColor(r, g, b, a);
            // Clears the window
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }

            this.imGuiLayer.update(dt, currentScene);
            glfwSwapBuffers(window);

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;

            beginTime = endTime;
        }
    }

    public static int getWidth() {
        return get().width;
    }

    public static int getHeight() {
        return get().height;
    }

    private static void setWidth(int newWidth) {
        get().width = newWidth;
    }

    private static void setHeight(int newHeight) {
        get().height = newHeight;
    }
}
