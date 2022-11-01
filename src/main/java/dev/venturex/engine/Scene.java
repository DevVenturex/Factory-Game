package dev.venturex.engine;

public abstract class Scene {

    protected Camera cam;

    public abstract void init();
    public abstract void update(float deltaTime);
}
