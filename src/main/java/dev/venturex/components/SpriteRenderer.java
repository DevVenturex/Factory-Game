package dev.venturex.components;

import dev.venturex.engine.Component;
import dev.venturex.engine.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    Vector4f color;
    private Vector2f[] texCoords;
    private Texture texture;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.texture = null;
    }

    public SpriteRenderer(Vector4f color, Texture texture) {
        this.color = color;
        this.texture = texture;
    }

    public SpriteRenderer(Texture texture) {
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.texture = texture;
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float deltaTime) {

    }

    public Vector4f getColor() {
        return color;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2f[] getTexCoords() {
        Vector2f[] texCoords = {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };
        return texCoords;
    }
}
