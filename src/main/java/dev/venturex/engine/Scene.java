package dev.venturex.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.venturex.Game;
import dev.venturex.engine.io.ComponentSerializer;
import dev.venturex.engine.io.GameObjectDeserializer;
import dev.venturex.engine.renderer.Renderer;
import imgui.ImGui;
import org.joml.Vector2f;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
    protected Camera cam = new Camera(new Vector2f(0, 0));
    protected List<GameObject> gameObjects;
    private boolean isRunning = false;
    protected  GameObject activeGameObject = null;
    protected boolean ready = false;

    public Scene() {
        this.gameObjects = new ArrayList<>();
    }

    public abstract void init();

    public void start() {
        for (GameObject go : gameObjects) {
            go.init();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void onReady() {}

    public void addGameObject(GameObject go) {
        if (!isRunning) {
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

    public void sceneImgui() {
        if (activeGameObject != null){
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }

        imgui();
    }

    public void imgui() {}

    public void saveOnExit(){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentSerializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        try {
            FileWriter writer = new FileWriter("Game.json");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentSerializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();
        String inFile = "";

        try {
            inFile = new String(Files.readAllBytes(Paths.get("Game.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inFile.equals("")){
            GameObject[] objects = gson.fromJson(inFile, GameObject[].class);
            for (GameObject object : objects){
                addGameObject(object);
            }
            this.ready = true;
            onReady();
        }
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
