package dev.venturex.engine;

import org.joml.Vector2f;

public class Transform {

    public Vector2f position;
    public Vector2f scale;
    public float rotation;

    public Transform(){
        init(new Vector2f(), 0, new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, 0, new Vector2f());
    }

    public Transform(Vector2f position, float rotation) {
        init(position, rotation, new Vector2f());
    }

    public Transform(Vector2f position, float rotation, Vector2f scale) {
        init(position, rotation, scale);
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, 0, scale);
    }

    public void init(Vector2f position, float rotation, Vector2f scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }
}
