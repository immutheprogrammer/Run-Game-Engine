package run.scenes;

import components.Sprite;
import components.SpriteRenderer;
import components.SpriteSheet;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;
import run.Camera;
import run.GameObject;
import run.Transform;
import run.input.KeyListener;
import util.AssetPool;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private GameObject obj1;

    SpriteSheet spriteSheet;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();


        this.camera = new Camera(new Vector2f(0, 0));

        spriteSheet = AssetPool.getSpriteSheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), -2);
        obj1.addComponent(new SpriteRenderer(new Sprite(
                AssetPool.getTexture("assets/images/blendImage1.png")
        )));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 1);
        obj2.addComponent(new SpriteRenderer(new Vector4f(1f, 1f, 1f, 0.5f)));
        this.addGameObjectToScene(obj2);
        this.activeGameObject = obj2;



    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheet.png",
                new SpriteSheet(
                        AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
    }

    @Override
    public void update(float dt) {

        System.out.println("FPS: " + (int)(1.0f / dt));


        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    public void imgui() {
        ImGui.begin("Test window");
        ImGui.text("Random Text");
        ImGui.end();
    }
}