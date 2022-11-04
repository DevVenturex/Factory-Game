package dev.venturex.engine.io;

import com.google.gson.*;
import dev.venturex.engine.Component;

import java.lang.reflect.Type;

public class ComponentSerializer implements JsonSerializer<Component>, JsonDeserializer<Component> {

    @Override
    public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String type = object.get("type").getAsString();
        JsonElement element = object.get("properties");

        try {
            return context.deserialize(element, Class.forName(type));
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
        object.add("properties", context.serialize(src, src.getClass()));
        return object;
    }
}
