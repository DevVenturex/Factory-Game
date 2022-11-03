package dev.venturex.engine;

public abstract class Component {

    public GameObject gameObject = null;

    public abstract void init();

    public abstract void update(float deltaTime);
}
