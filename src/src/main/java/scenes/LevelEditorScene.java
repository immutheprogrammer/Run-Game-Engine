package scenes;

import components.GridLines;
import components.MouseControls;
import components.Sprite;
import components.SpriteSheet;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.type.ImBoolean;
import input.MouseListener;
import org.joml.Vector2f;
import run.Camera;
import run.GameObject;
import run.Prefabs;
import run.Transform;
import util.AssetPool;
import util.Settings;

import static org.lwjgl.opengl.GL11.*;

public class LevelEditorScene extends Scene {


    SpriteSheet sprites = new SpriteSheet(AssetPool.getTexture("assets/images/spritesheet.png"), 16, 16, 26, 0);
    private ImBoolean printFPS = new ImBoolean(false);
    private ImBoolean showWireFrame = new ImBoolean(false);
    private float deltaTime;

    GameObject levelEditorStuff = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());

        loadResources();
        this.camera = new Camera(new Vector2f(0f, 0f));
        if (loadedFile) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }
/*
        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(0, 0),
                new Vector2f(256, 256)), 2);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setSprite(new Sprite());
        obj1.addComponent(obj1Sprite);
        this.addGameObjectToScene(obj1);
        this.activeGameObject = obj1;*/

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
        levelEditorStuff.update(dt);

        deltaTime = dt;

        // I know this panning system is bad but im not really working on it right now
        if (MouseListener.isDragging()) {
            if (MouseListener.getX() < (MouseListener.getLastX() + 10)) {
                   camera.position.x += 10;
            }
            if (MouseListener.getX() > (MouseListener.getLastX() - 10)) {
                camera.position.x -= 10;
            }
            if (MouseListener.getY() < (MouseListener.getLastY() - 10)) {
                camera.position.y -= 10;
            }
            if (MouseListener.getY() > (MouseListener.getLastY() + 10)) {
                camera.position.y += 10;
            }
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.checkbox("Show Fps", printFPS);
        if (printFPS.get()) System.out.println("FPS: " + (int) (1.0f / deltaTime));
        ImGui.checkbox("Show WireFrame", showWireFrame);
        if (showWireFrame.get()) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        ImGui.begin("Texture Picker");


        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing();

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexID();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, Settings.GRID_WIDTH, Settings.GRID_HEIGHT);
                levelEditorStuff.getComponent(MouseControls.class).pickUpObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }

        }

        ImGui.end();

    }
}