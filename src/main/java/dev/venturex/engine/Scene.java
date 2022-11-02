package dev.venturex.engine;

import dev.venturex.components.SpriteRenderer;
import dev.venturex.engine.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
    protected Camera cam;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects;

    public Scene() {
        this.gameObjects = new ArrayList<>();
    }

    public abstract void init();

    public void start(){
        for (GameObject go : gameObjects){
            go.init();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObject(GameObject go){
        if (!isRunning){
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.init();
            this.renderer.add(go);
        }
    }

    public abstract void update(float deltaTime);

    public Camera camera() {
        return cam;
    }
}
