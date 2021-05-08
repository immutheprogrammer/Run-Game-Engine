package run.scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Component;
import components.Sprite;
import components.SpriteRenderer;
import components.SpriteSheet;
import imgui.ImGui;
import imgui.type.ImBoolean;
import org.joml.Vector2f;
import org.joml.Vector4f;
import run.*;
import run.input.KeyListener;
import util.AssetPool;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    SpriteRenderer obj1Sprite;

    SpriteSheet sprites;
    private ImBoolean printFPS = new ImBoolean(false);
    private float deltaTime;



    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        if (loadedFile) {
            return;
        }

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(0, 0),
                new Vector2f(256, 256)), 2);
        obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColour(new Vector4f(1, 0, 0, 1));
        obj1.addComponent(obj1Sprite);
        this.addGameObjectToScene(obj1);
        this.activeGameObject = obj1;


        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(0, 0),
                new Vector2f(256, 256)), 2);
        SpriteRenderer obj2Sprite = new SpriteRenderer();
        obj2Sprite.setColour(new Vector4f(1f, 0.5f, 0f, 1f));
        obj2.addComponent(obj2Sprite);
        this.addGameObjectToScene(obj2);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.getSpriteSheet("assets/images/spritesheet.png",
                new SpriteSheet(
                        AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
    }

    @Override
    public void update(float dt) {

        deltaTime = dt;

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.checkbox("Show Fps", printFPS);
        if (printFPS.get())
            System.out.println("FPS: " + (int)(1.0f / deltaTime));
    }
}