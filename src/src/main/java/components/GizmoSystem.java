package components;

import input.KeyInput;
import run.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class GizmoSystem extends Component {
    private SpriteSheet gizmos;
    private int usingGizmos = 0;

    public GizmoSystem(SpriteSheet gizmoSprite) {
        gizmos = gizmoSprite;
    }

    @Override
    public void start() {
        gameObject.addComponent(new TranslateGizmo(gizmos.getSprite(1), Window.getImGuiLayer().getPropertiesWindow()));
        gameObject.addComponent(new ScaleGizmo(gizmos.getSprite(2), Window.getImGuiLayer().getPropertiesWindow()));
    }

    @Override
    public void update(float dt) {
        if (usingGizmos == 0) {
            gameObject.getComponent(TranslateGizmo.class).setUsing();
            gameObject.getComponent(ScaleGizmo.class).setNotUsing();
        } else if (usingGizmos == 1) {
            gameObject.getComponent(TranslateGizmo.class).setNotUsing();
            gameObject.getComponent(ScaleGizmo.class).setUsing();
        }

        if (KeyInput.isKeyPressed(GLFW_KEY_E)) {
            usingGizmos = 0;
        } else if (KeyInput.isKeyPressed(GLFW_KEY_W)) {
            usingGizmos = 1;
        }
    }
}
