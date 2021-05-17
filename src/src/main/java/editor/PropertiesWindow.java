package editor;

import imgui.ImGui;
import input.MouseInput;
import renderer.PickingTexture;
import run.GameObject;
import scenes.Scene;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {
    private GameObject activeGameObject = null;
    private PickingTexture pickingTexture;

    public PropertiesWindow(PickingTexture pickingTexture) {
        this.pickingTexture = pickingTexture;
    }

    public void update(float dt, Scene currentScene) {
        if (MouseInput.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            int x = (int)MouseInput.getScreenX();
            int y = (int)MouseInput.getScreenY();
            activeGameObject = currentScene.getGameObject(pickingTexture.readPixel(x, y));
        }
    }

    public void imgui() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }
    }
}
