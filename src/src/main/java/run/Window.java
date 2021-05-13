package run;


import imgui.ImGui;
import imgui.ImGuiLayer;
import imgui.flag.ImGuiConfigFlags;
import input.KeyInput;
import input.MouseInput;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import renderer.DebugDraw;
import scenes.LevelEditorScene;
import scenes.LevelScene;
import scenes.Scene;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
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
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown Scene " + newScene;
                break;
        }

        currentScene.load();
        currentScene.init();
        currentScene.start();
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
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        // Sets the mouse callbacks
        glfwSetCursorPosCallback(window, MouseInput::mousePositionCallback);
        glfwSetMouseButtonCallback(window, MouseInput::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseInput::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyInput::keyCallback);
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

        Window.changeScene(0);

        this.imGuiLayer = new ImGuiLayer(window);
        this.imGuiLayer.initImGui();
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
        imGuiLayer.destroyImGui();
    }

    private void loop() {
        float beginTime = (float) glfwGetTime();
        float endTime;
        float dt = -1.0f;

        while (!glfwWindowShouldClose(window)) {
            // Poll events
            glfwPollEvents();

            DebugDraw.beginFrame();

            // Change the colour of the screen
            glClearColor(r, g, b, a);
            // Clears the window
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (dt >= 0) {
                DebugDraw.draw();
                currentScene.update(dt);
            }
            // Broken camera zooming code
            // getScene().camera().adjustProjection();

            // Can't get multi-view port support working :(
            if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
                ImGui.updatePlatformWindows();
                ImGui.renderPlatformWindowsDefault();
            }
            this.imGuiLayer.update(dt, currentScene);
            glfwSwapBuffers(window);

            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;

            beginTime = endTime;
        }
        currentScene.saveExit();
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
