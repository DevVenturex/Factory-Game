package dev.venturex.engine.inputs;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardHandler {

    private static KeyboardHandler instance;
    private boolean keyPressed[] = new boolean[350];

    private KeyboardHandler() {}

    public static KeyboardHandler get(){
        if (KeyboardHandler.instance == null)
            KeyboardHandler.instance = new KeyboardHandler();
        return KeyboardHandler.instance;
    }

    public static void keyCallback(long glfwWindow, int key, int scancode, int action, int mods){
        if (action == GLFW_PRESS){
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int key){
        return get().keyPressed[key];
    }
}
