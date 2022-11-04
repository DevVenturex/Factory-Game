package dev.venturex.engine.io;

import com.google.gson.*;
import dev.venturex.engine.Component;
import dev.venturex.engine.GameObject;
import dev.venturex.engine.Transform;

import java.lang.reflect.Type;

public class GameObjectDeserializer implements JsonDeserializer<GameObject> {

    @Override
    public GameObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String name = object.get("name").getAsString();
        JsonArray components = object.getAsJsonArray("components");
        Transform transform = context.deserialize(object.get("transform"), Transform.class);
        int zIndex = context.deserialize(object.get("zIndex"), int.class);

        GameObject go = new GameObject(name, transform, zIndex);
        for (JsonElement e : components){
            Component c = context.deserialize(e, Component.class);
            go.addComponent(c);
        }
        return go;
    }
}
