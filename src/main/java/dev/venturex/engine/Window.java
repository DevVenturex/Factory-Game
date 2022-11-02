package dev.venturex.engine;

import dev.venturex.engine.inputs.KeyboardHandler;
import dev.venturex.engine.inputs.MouseHandler;
import dev.venturex.engine.util.Time;
import dev.venturex.scenes.LevelEditorScene;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    static Window window;
    private int width, height;
    private String title;
    private long glfwWindow;

    private static Scene currentScene = null;

    public float r, g, b, a;

    private Window(){
        this.width = 1200;
        this.height = 1080;
        this.title = "Factory Game";
        r = .1f;
        g = .1f;
        b = .1f;
        a = 1;
    }

    public static void changeToScene(Scene scene){
        currentScene = scene;
        currentScene.init();
        currentScene.start();
    }

    public static Window get(){
        if (Window.window == null)
            Window.window = new Window();
        return Window.window;
    }

    public static Scene getScene() {
        return get().currentScene;
    }

    public void run(){
        System.out.println("LWJGL Version: " + Version.getVersion());

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init(){
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // the window will be maximized

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if ( glfwWindow == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetFramebufferSizeCallback(glfwWindow, (window, width, height) -> {
           glViewport(0, 0, width, height);
        });

        glfwSetKeyCallback(glfwWindow, KeyboardHandler::keyCallback);
        glfwSetCursorPosCallback(glfwWindow, MouseHandler::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseHandler::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseHandler::mouseScrollCallback);

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(glfwWindow, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    glfwWindow,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        Window.changeToScene(new LevelEditorScene());
    }

    private void loop(){
        float beginTime = Time.getTime();
        float endTime;
        float deltaTime = -1f;

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(glfwWindow) ) {

            glClearColor(r, g, b, a); // Set the clear color
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            if (deltaTime >= 0) {
                int fps = (int) (1 / deltaTime);
                glfwSetWindowTitle(glfwWindow, title + " || FPS: " + fps);
                currentScene.update(deltaTime);
            }

            glfwSwapBuffers(glfwWindow); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
