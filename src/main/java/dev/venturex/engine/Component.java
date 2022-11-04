package dev.venturex.engine;

import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Component {

    public transient GameObject gameObject = null;

    public abstract void init();

    public abstract void update(float deltaTime);

    public void imgui() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields){
                boolean isTransient = Modifier.isTransient(field.getModifiers());
                if (isTransient)
                    continue;

                boolean isPrivate = Modifier.isPrivate(field.getModifiers());
                if (isPrivate)
                    field.setAccessible(true);

                Class type = field.getType();
                Object value = field.get(this);
                String name = field.getName();

                if (type == int.class){
                    int val = (int) value;
                    int[] imInt = {val};
                    if (ImGui.dragInt(name + ": ", imInt))
                        field.set(this, imInt[0]);
                } else if (type == float.class) {
                    float val = (float) value;
                    float[] imFloat = {val};
                    if (ImGui.dragFloat(name + ": ", imFloat))
                        field.set(this, imFloat[0]);
                } else if (type == Vector2f.class){
                    Vector2f val = (Vector2f) value;
                    float[] imVec = {val.x, val.y};
                    if (ImGui.dragFloat2(name + ": ", imVec))
                        val.set(imVec[0], imVec[1]);
                } else if (type == Vector4f.class){
                    Vector4f val = (Vector4f) value;
                    float[] imVec = {val.x, val.y, val.z, val.w};
                    if (ImGui.dragFloat4(name + ": ", imVec))
                        val.set(imVec[0], imVec[1], imVec[2], imVec[3]);
                }

                if (isPrivate){
                    field.setAccessible(false);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
