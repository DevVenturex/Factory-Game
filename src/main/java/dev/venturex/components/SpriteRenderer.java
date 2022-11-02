package dev.venturex.components;

import dev.venturex.engine.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    Vector4f color;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
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
}
