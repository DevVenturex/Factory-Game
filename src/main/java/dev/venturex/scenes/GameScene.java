package dev.venturex.scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.venturex.Game;
import dev.venturex.components.RigidBody;
import dev.venturex.components.Sprite;
import dev.venturex.components.SpriteRenderer;
import dev.venturex.components.SpriteSheet;
import dev.venturex.engine.*;
import dev.venturex.engine.inputs.KeyboardHandler;
import dev.venturex.engine.io.ComponentSerializer;
import dev.venturex.engine.io.GameObjectDeserializer;
import dev.venturex.engine.util.AssetPool;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.enums.ImGuiWindowFlags;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {

    private GameObject obj1;
    private SpriteSheet sheet;

    @Override
    public void init() {
        loadResources();
        this.cam = new Camera(new Vector2f());
        if (ready) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        sheet = AssetPool.getSpriteSheet("res/assets/textures/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(1, 0, 0, 1));
        obj1.addComponent(obj1Sprite);
        obj1.addComponent(new RigidBody());
        this.addGameObject(obj1);
        this.activeGameObject = obj1;

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(300, 100), new Vector2f(256, 256)));
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTexture(AssetPool.getTexture("res/assets/textures/testImage.png"));
        obj2SpriteRenderer.setSprite(obj2Sprite);
        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObject(obj2);
    }

    private void loadResources() {
        AssetPool.getShader("DefaultShader", "res/assets/shaders/vDefault.glsl", "res/assets/shaders/fDefault.glsl");
        AssetPool.addSpriteSheet("res/assets/textures/spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("res/assets/textures/spritesheet.png"),
                        16, 16, 26, 0, 0));
        AssetPool.getTexture("res/assets/textures/testImage.png");
    }

    @Override
    public void update(float deltaTime) {
        if (KeyboardHandler.isKeyPressed(GLFW_KEY_RIGHT)) {
            cam.position.x += 100f * deltaTime;
        } else if (KeyboardHandler.isKeyPressed(GLFW_KEY_LEFT)) {
            cam.position.x -= 100f * deltaTime;
        }

        if (KeyboardHandler.isKeyPressed(GLFW_KEY_UP)) {
            cam.position.y += 100f * deltaTime;
        } else if (KeyboardHandler.isKeyPressed(GLFW_KEY_DOWN)) {
            cam.position.y -= 100f * deltaTime;
        }

        for (GameObject go : this.gameObjects) {
            go.update(deltaTime);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        int location = 0;
        ImGuiIO io = ImGui.getIO();
        int windowFlags = ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoSavedSettings | ImGuiWindowFlags.NoFocusOnAppearing | ImGuiWindowFlags.NoNav | ImGuiWindowFlags.NoMove;
        ImGui.setNextWindowBgAlpha(0.35f);
        ImGui.setNextWindowPos(10f, 10f);
        if (ImGui.begin("Infos", windowFlags)){
            ImGui.text("Statistics");
            ImGui.separator();
            ImGui.text("FPS: " + Window.getFps());
            ImGui.text("DeltaTime: " + Window.getDeltaTime());
        }
        ImGui.end();
    }
}
