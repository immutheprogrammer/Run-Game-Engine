package components;

import input.KeyInput;
import input.MouseInput;
import org.joml.Vector2f;
import run.Camera;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

public class EditorCamera extends Component{

    private float lerpTime = 0.0f;
    private float dragDeBounce = 0.032f;
    private Camera levelEditorCamera;
    private Vector2f clickOrigin;
    private boolean reset = false;

    private float dragSensitivity = 7.5f;
    private float scrollSensitivity = 0.1f;

    public EditorCamera(Camera levelEditorCamera) {
        this.levelEditorCamera = levelEditorCamera;
        this.clickOrigin = new Vector2f();
    }

    @Override
    public void update(float dt) {
        if (MouseInput.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE) && dragDeBounce > 0) {
            this.clickOrigin = new Vector2f(MouseInput.getOrthoX(), MouseInput.getOrthoY());
            dragDeBounce -= dt;
            return;
        } else if (MouseInput.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            Vector2f mousePosition = new Vector2f(MouseInput.getOrthoX(), MouseInput.getOrthoY());
            Vector2f delta = new Vector2f(MouseInput.getOrthoX(), MouseInput.getOrthoY())
                                    .sub(this.clickOrigin);
            levelEditorCamera.position.sub(delta.mul(dt).mul(dragSensitivity));
            this.clickOrigin.lerp(mousePosition, dt);
        }

        if (dragDeBounce <= 0.0f && !MouseInput.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            dragDeBounce = 0.1f;
        }

        if (MouseInput.getScrollY() != 0.0f) {
            float addValue = (float)Math.pow(Math.abs(MouseInput.getScrollY() * scrollSensitivity),
                    1 / levelEditorCamera.getZoom());
            addValue *= -Math.signum(MouseInput.getScrollY());
            levelEditorCamera.addZoom(addValue);
        }

        if (KeyInput.isKeyPressed(GLFW_KEY_F)){
            reset = true;
        }

        if (reset) {
            levelEditorCamera.position.lerp(new Vector2f(), lerpTime);
            levelEditorCamera.setZoom(this.levelEditorCamera.getZoom() + ((1.0f - levelEditorCamera.getZoom()) * lerpTime));

            this.lerpTime += 0.1f * dt;

            if (Math.abs(levelEditorCamera.position.x) <= 5.0f &&
                    Math.abs(levelEditorCamera.position.y) <= 5.0f) {
                this.lerpTime = 0.0f;
                levelEditorCamera.position.set(0f, 0f);
                this.levelEditorCamera.setZoom(1.0f);
                reset = false;
            }
        }

    }
}
