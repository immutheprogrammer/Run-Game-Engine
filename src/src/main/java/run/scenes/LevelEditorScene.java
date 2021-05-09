package run.scenes;

import components.RigidBody2D;
import components.SpriteRenderer;
import components.SpriteSheet;
import imgui.ImGui;
import imgui.type.ImBoolean;
import org.joml.Vector2f;
import org.joml.Vector4f;
import run.Camera;
import run.GameObject;
import run.Transform;
import util.AssetPool;

import static org.lwjgl.opengl.GL11.*;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    SpriteRenderer obj1Sprite;

    SpriteSheet sprites;
    private ImBoolean printFPS = new ImBoolean(false);
    private ImBoolean showWireFrame = new ImBoolean(false);
    private float deltaTime;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(0f, 0f));
        if (loadedFile) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(0, 0),
                new Vector2f(256, 256)), 2);
        obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColour(new Vector4f(1f, 0f, 0f, 1f));
        obj1.addComponent(obj1Sprite);
        obj1.addComponent(new RigidBody2D());
        this.addGameObjectToScene(obj1);
        this.activeGameObject = obj1;

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
        ImGui.checkbox("Show WireFrame", showWireFrame);
        if (showWireFrame.get()) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

    }
}