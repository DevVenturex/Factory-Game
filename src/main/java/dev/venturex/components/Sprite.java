package dev.venturex.components;

import dev.venturex.engine.renderer.Texture;
import org.joml.Vector2f;

public class Sprite {

    private Texture texture = null;
    private Vector2f[] texCoords = new Vector2f[]{
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };;

//    public Sprite(Texture texture) {
//        this.texture = texture;
//        this.texCoords = new Vector2f[]{
//                new Vector2f(1, 1),
//                new Vector2f(1, 0),
//                new Vector2f(0, 0),
//                new Vector2f(0, 1)
//        };
//    }

//    public Sprite(Texture texture, Vector2f[] texCoords) {
//        this.texture = texture;
//        this.texCoords = texCoords;
//    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2f[] getTexCoords() {
        return texCoords;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }
}
