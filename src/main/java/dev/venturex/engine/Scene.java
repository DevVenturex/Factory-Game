package dev.venturex.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

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
        }
        isRunning = true;
    }

    public void addGameObject(GameObject go){
        if (!isRunning){
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.init();
        }
    }

    public abstract void update(float deltaTime);
}
