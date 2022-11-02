package dev.venturex.scenes;

import dev.venturex.components.Sprite;
import dev.venturex.components.SpriteRenderer;
import dev.venturex.components.SpriteSheet;
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
        loadResources();

        this.cam = new Camera(new Vector2f());

        SpriteSheet spriteSheet = AssetPool.getSpriteSheet("res/assets/textures/spritesheet.png");

        GameObject obj1 = new GameObject("Mario", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(spriteSheet.getSprite(0)));
        this.addGameObject(obj1);

        GameObject obj2 = new GameObject("Goomba", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(spriteSheet.getSprite(10)));
        this.addGameObject(obj2);

    }

    private void loadResources(){
        AssetPool.getShader("DefaultShader", "res/assets/shaders/vDefault.glsl", "res/assets/shaders/fDefault.glsl");
        AssetPool.addSpriteSheet("res/assets/textures/spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("res/assets/textures/spritesheet.png"),
                        16, 16, 26, 0, 0));
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
