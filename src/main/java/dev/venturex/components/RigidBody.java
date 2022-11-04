package dev.venturex.components;

import dev.venturex.engine.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class RigidBody extends Component {

    private int colliderType = 0;
    private float friction = 0.8f;
    public Vector2f velocity = new Vector2f(0, 0.5f);
    public transient Vector4f tmp = new Vector4f(0, 0, 0, 0);

    @Override
    public void init() {

    }

    @Override
    public void update(float deltaTime) {

    }
}
