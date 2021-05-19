package editor;

import components.NonPickable;
import imgui.ImGui;
import input.MouseInput;
import renderer.PickingTexture;
import run.GameObject;
import scenes.Scene;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {
    private GameObject activeGameObject = null;
    private PickingTexture pickingTexture;

    private float debounce = 0.8f;

    public PropertiesWindow(PickingTexture pickingTexture) {
        this.pickingTexture = pickingTexture;
    }

    public void update(float dt, Scene currentScene) {
        debounce -= Math.pow(dt, dt);

        if (MouseInput.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) && debounce < 0) {
            int x = (int)MouseInput.getScreenX();
            int y = (int)MouseInput.getScreenY();
            GameObject pickedObj = currentScene.getGameObject(pickingTexture.readPixel(x, y));
            if (pickedObj != null && pickedObj.getComponent(NonPickable.class) == null)
                activeGameObject = pickedObj;
            else if (pickedObj == null & !MouseInput.isDragging())
                activeGameObject = null;
            debounce = 0.6f;
        }
    }

    public void imgui() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }
    }

    public GameObject getActiveGameObject() {
        return this.activeGameObject;
    }
}
