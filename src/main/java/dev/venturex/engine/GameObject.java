package dev.venturex.engine;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private String name;
    private List<Component> components;

    public GameObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    public <T extends Component> T getComponent(Class<T> componentClass){
        for (Component c : components){
            if (componentClass.isAssignableFrom(c.getClass()))
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass){
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c){
        this.components.add(c);
        c.gameObject = this;
    }

    public void update(float deltaTime){
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(deltaTime);
        }
    }

    public void init(){
        for (int i = 0; i < components.size(); i++) {
            components.get(i).init();
        }
    }
}
