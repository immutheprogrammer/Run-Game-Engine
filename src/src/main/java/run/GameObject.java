package run;

import components.Component;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private static int ID_COUNTER = 0;
    private int uid = -1;

    private String name;
    private List<Component> components;
    public Transform transform;


    private int zIndex;

    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = zIndex;

        this.uid = ID_COUNTER++;
    }

    // T is like a placeholder
    // Basically any class that extends Component will be able to get passed
    // Through this function

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                return componentClass.cast(c);
            } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "ERROR: Casting component";
                }
            }
        }
        return null;
    }
    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        c.generateID();
        this.components.add(c);
        c.gameObject = this;
    }

    public void update(float dt) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(dt);
        }


    }

    public void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public void imgui() {
        for (Component c : components) {
           c.imgui();
        }
        float positions[] = {
            transform.position.x, transform.position.y
        };

        float scale[] = {
            transform.scale.x, transform.scale.y
        };

        if (ImGui.dragFloat2("Position: ", positions)) {
            transform.position.x = positions[0];
            transform.position.y = positions[1];
        }

        if (ImGui.dragFloat2("Scale: ", scale)) {
            transform.scale.x = scale[0];
            transform.scale.y = scale[1];
        }
    }

    public int zIndex() {
        return zIndex;
    }

    public static void init(int maxID) {
        ID_COUNTER = maxID;
    }

    public int getUID() {
        return this.uid;
    }

    public List<Component> getAllComponents() {
        return this.components;
    }
}
