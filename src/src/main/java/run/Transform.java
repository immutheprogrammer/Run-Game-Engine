package run;

import components.Component;
import editor.JImGui;
import org.joml.Vector2f;
import util.Settings;

public class Transform extends Component {

    public Vector2f position;
    public Vector2f scale;
    public float rotation = 0f;
    public int zIndex;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
        this.zIndex = 0;
    }

    public Transform copy() {
        Transform t = new Transform(this.position, this.scale);
        t.rotation = rotation;
        t.zIndex = zIndex;

        return t;
    }

    @Override
    public void imgui() {
        JImGui.drawVec2Control("Position: ", this.position);
        JImGui.drawVec2Control("Scale: ", this.scale, Settings.GRID_WIDTH);
        this.rotation = JImGui.dragFloat("Rotation: ", this.rotation);
    }

    public void copy(Transform to) {
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Transform)) return false;

        Transform t = (Transform)o;
        return t.position.equals(this.position) && t.scale.equals(this.scale) &&
                t.scale.equals(this.rotation) && t.zIndex == this.zIndex;
    }
}
