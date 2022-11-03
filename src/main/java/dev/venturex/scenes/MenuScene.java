package dev.venturex.scenes;

import dev.venturex.engine.Scene;
import dev.venturex.engine.Window;
import dev.venturex.engine.inputs.KeyboardHandler;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class MenuScene extends Scene {

    @Override
    public void init() {
        System.out.println("LevelScene");
    }

    @Override
    public void update(float deltaTime) {
        if (KeyboardHandler.isKeyPressed(GLFW_KEY_SPACE))
            Window.changeToScene(new GameScene());
    }
}
