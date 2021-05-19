package components;

import editor.PropertiesWindow;
import input.MouseInput;
import org.joml.Vector2f;
import org.joml.Vector4f;
import run.GameObject;
import run.Prefabs;
import run.Window;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class Gizmo extends Component {
    private Vector4f xAxisColour = new Vector4f(1, 0.3f, 0.3f, 1f);
    private Vector4f xAxisColourHover = new Vector4f(1, 0, 0, 1);
    private Vector4f yAxisColour = new Vector4f(0.3f, 1, 0.3f, 1f);
    private Vector4f yAxisColourHover = new Vector4f(0, 1, 0, 1);

    private GameObject xAxisObject;
    private GameObject yAxisObject;
    private SpriteRenderer xAxisSprite;
    private SpriteRenderer yAxisSprite;
    protected GameObject activeGameObject = null;

    private Vector2f xAxisOffset = new Vector2f(64, -5);
    private Vector2f yAxisOffset = new Vector2f(16, 61);

    private int gizmoWidth = 16;
    private int gizmoHeight = 48;

    protected boolean xAxisActive = false;
    protected boolean yAxisActive = false;

    private PropertiesWindow propertiesWindow;
    private boolean using = false;


    public Gizmo(Sprite arrowSprite, PropertiesWindow propertiesWindow) {
        this.xAxisObject = Prefabs.generateSpriteObject(arrowSprite, 16, 48);
        this.yAxisObject = Prefabs.generateSpriteObject(arrowSprite, 16, 48);
        this.xAxisSprite = this.xAxisObject.getComponent(SpriteRenderer.class);
        this.yAxisSprite = this.yAxisObject.getComponent(SpriteRenderer.class);
        this.propertiesWindow = propertiesWindow;

        this.xAxisObject.addComponent(new NonPickable());
        this.yAxisObject.addComponent(new NonPickable());

        Window.getScene().addGameObjectToScene(this.xAxisObject);
        Window.getScene().addGameObjectToScene(this.yAxisObject);
    }

    @Override
    public void start() {
        this.xAxisObject.transform.rotation = 90;
        this.yAxisObject.transform.rotation = 180;
        this.xAxisObject.transform.zIndex = -100;
        this.yAxisObject.transform.zIndex = -100;
        this.xAxisObject.setNoSerialize();
        this.yAxisObject.setNoSerialize();
    }

    @Override
    public void update(float dt) {
        if (!using) return;

        this.activeGameObject = this.propertiesWindow.getActiveGameObject();
        if (this.activeGameObject != null) {
            this.setActive();
        } else {
            this.setInactive();
            return;
        }

        boolean xAxisHot = checkXHoverState();
        boolean yAxisHot = checkYHoverState();

        if ((xAxisHot || xAxisActive) && MouseInput.isDragging() && MouseInput.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            xAxisActive = true;
            yAxisActive = false;
        } else if ((yAxisHot || yAxisActive) && MouseInput.isDragging() && MouseInput.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            yAxisActive = true;
            xAxisActive = false;
        } else {
            yAxisActive = false;
            xAxisActive = false;
        }


        if (this.activeGameObject != null) {
            this.xAxisObject.transform.position.set(this.activeGameObject.transform.position);
            this.yAxisObject.transform.position.set(this.activeGameObject.transform.position);
            this.xAxisObject.transform.position.add(this.xAxisOffset);
            this.yAxisObject.transform.position.add(this.yAxisOffset);
        }
    }

    private void setActive() {
        this.xAxisSprite.setColour(xAxisColour);
        this.yAxisSprite.setColour(yAxisColour);
    }

    private void setInactive() {
        this.activeGameObject = null;
        this.xAxisSprite.setColour(new Vector4f(0, 0, 0, 0));
        this.yAxisSprite.setColour(new Vector4f(0, 0, 0, 0));
    }

    private boolean checkXHoverState() {
        Vector2f mousePos = new Vector2f(MouseInput.getOrthoX(), MouseInput.getOrthoY());
        if (mousePos.x <= xAxisObject.transform.position.x &&
                mousePos.x >= xAxisObject.transform.position.x - gizmoHeight &&
                mousePos.y >= xAxisObject.transform.position.y &&
                mousePos.y <= xAxisObject.transform.position.y + gizmoWidth) {
            xAxisSprite.setColour(xAxisColourHover);
            return true;
        }

        xAxisSprite.setColour(xAxisColour);
        return false;
    }

    private boolean checkYHoverState() {
        Vector2f mousePos = new Vector2f(MouseInput.getOrthoX(), MouseInput.getOrthoY());
        if (mousePos.x <= yAxisObject.transform.position.x &&
                mousePos.x >= yAxisObject.transform.position.x - gizmoWidth &&
                mousePos.y <= yAxisObject.transform.position.y &&
                mousePos.y >= yAxisObject.transform.position.y - gizmoHeight) {
            yAxisSprite.setColour(yAxisColourHover);
            return true;
        }

        yAxisSprite.setColour(yAxisColour);
        return false;
    }

    public void setUsing() {
        this.using = true;
    }

    public void setNotUsing() {
        this.using = false;
        this.setInactive();
    }
}
