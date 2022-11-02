package dev.venturex.scenes;

import dev.venturex.components.SpriteRenderer;
import dev.venturex.engine.Camera;
import dev.venturex.engine.GameObject;
import dev.venturex.engine.Scene;
import dev.venturex.engine.Transform;
import dev.venturex.engine.inputs.KeyboardHandler;
import dev.venturex.engine.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    @Override
    public void init() {
        this.cam = new Camera(new Vector2f());

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("res/assets/textures/testImage.png")));
        this.addGameObject(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("res/assets/textures/testImage2.png")));
        this.addGameObject(obj2);

        loadResources();
    }

    private void loadResources(){
        AssetPool.getShader("DefaultShader", "res/assets/shaders/vDefault.glsl", "res/assets/shaders/fDefault.glsl");
    }

    @Override
    public void update(float deltaTime) {
        if (KeyboardHandler.isKeyPressed(GLFW_KEY_RIGHT)){
            cam.position.x += 100f * deltaTime;
        } else if (KeyboardHandler.isKeyPressed(GLFW_KEY_LEFT)){
            cam.position.x -= 100f * deltaTime;
        }

        if (KeyboardHandler.isKeyPressed(GLFW_KEY_UP)){
            cam.position.y += 100f * deltaTime;
        } else if (KeyboardHandler.isKeyPressed(GLFW_KEY_DOWN)){
            cam.position.y -= 100f * deltaTime;
        }

        for (GameObject go : this.gameObjects){
            go.update(deltaTime);
        }

        this.renderer.render();
    }
}
