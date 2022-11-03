package dev.venturex.components;

import dev.venturex.engine.Component;
import dev.venturex.engine.Transform;
import dev.venturex.engine.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    Vector4f color;
    private Sprite sprite;

    private Transform lastTransform;
    private boolean isDirty = false;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
        isDirty = true;
    }

    public SpriteRenderer(Vector4f color, Sprite sprite) {
        this.color = color;
        this.sprite = sprite;
        isDirty = true;
    }

    public SpriteRenderer(Sprite sprite) {
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.sprite = sprite;
        isDirty = true;
    }

    @Override
    public void init() {
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float deltaTime) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.isDirty = true;
            this.color.set(color);
        }
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}
