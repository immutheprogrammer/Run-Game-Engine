package components;

import editor.PropertiesWindow;
import input.MouseInput;

public class ScaleGizmo extends Gizmo{

    public ScaleGizmo(Sprite scaleSprite, PropertiesWindow propertiesWindow) {
        super(scaleSprite, propertiesWindow);
    }

    @Override
    public void update(float dt) {
        if (activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                activeGameObject.transform.scale.x -= MouseInput.getWorldDx();
            } else if (yAxisActive && !xAxisActive) {
                activeGameObject.transform.scale.y -= MouseInput.getWorldDy();
            }
        }

        super.update(dt);
    }
}
