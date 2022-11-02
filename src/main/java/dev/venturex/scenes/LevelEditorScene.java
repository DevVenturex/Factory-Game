package dev.venturex.scenes;

import dev.venturex.components.SpriteRenderer;
import dev.venturex.engine.Camera;
import dev.venturex.engine.GameObject;
import dev.venturex.engine.Scene;
import dev.venturex.engine.Window;
import dev.venturex.engine.inputs.KeyboardHandler;
import dev.venturex.engine.renderer.Shader;
import dev.venturex.engine.renderer.Texture;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private float vertices[] = {
            // position                     // color                        // UV Coordinates
            100.5f,   0.5f, 0.0f,           1.0f, 1.0f, 1.0f, 1.0f,         1, 0, // Bottom right 0
              0.5f, 100.5f, 0.0f,           1.0f, 1.0f, 1.0f, 1.0f,         0, 1, // Top left     1
            100.5f, 100.5f, 0.0f,           1.0f, 1.0f, 1.0f, 1.0f,         1, 1, // Top right    2
              0.5f,   0.5f, 0.0f,           1.0f, 1.0f, 1.0f, 1.0f,         0, 0 // Bottom left  3
    };

    private int indices[] = {
            /*
                    1       2


                    3       0
             */
            2, 1, 0, 0, 1, 3
    };

    private int vaoId, vboId, eboId;
    private Shader shader;
    private Texture texture;

    GameObject gameObj;

    @Override
    public void init() {
        this.gameObj = new GameObject("Game Object");
        this.gameObj.addComponent(new SpriteRenderer());
        this.addGameObject(this.gameObj);

        this.cam = new Camera(new Vector2f());
        System.out.println("LevelEditorScene");
        shader = new Shader("res/assets/shaders/vDefault.glsl", "res/assets/shaders/fDefault.glsl");
        texture = new Texture("res/assets/textures/smile.png");

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();
        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        int positionsSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    @Override
    public void update(float deltaTime) {
        shader.bind();

        shader.uploadTexture("texSampler", 0);
        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        shader.setMat4f("uProjection", cam.getProjectionMatrix());
        shader.setMat4f("uView", cam.getViewMatrix());

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        shader.unbind();

        for (GameObject go : this.gameObjects){
            go.update(deltaTime);
        }
    }
}
