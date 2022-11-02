package dev.venturex.components;

import dev.venturex.engine.Component;
import dev.venturex.engine.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    Vector4f color;
    private Sprite sprite;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Vector4f color, Sprite sprite) {
        this.color = color;
        this.sprite = sprite;
    }

    public SpriteRenderer(Sprite sprite) {
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.sprite = sprite;
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
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }
}
