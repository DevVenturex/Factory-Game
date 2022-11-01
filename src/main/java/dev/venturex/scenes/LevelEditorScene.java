package dev.venturex.scenes;

import dev.venturex.engine.Camera;
import dev.venturex.engine.Scene;
import dev.venturex.engine.Window;
import dev.venturex.engine.inputs.KeyboardHandler;
import dev.venturex.engine.renderer.Shader;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private String vertexSource =
            "#version 330 core\n" +
            "\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";

    private String fragmentSource =
            "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main() {\n" +
            "    color = fColor;\n" +
            "}\n";

    private float vertices[] = {
            // position                     // color
            100.5f,   0.5f, 0.0f,          1.0f, 0.0f, 0.0f, 1.0f, // Bottom right 0
              0.5f, 100.5f, 0.0f,          0.0f, 1.0f, 0.0f, 1.0f, // Top left     1
            100.5f, 100.5f, 0.0f,          1.0f, 0.0f, 1.0f, 1.0f, // Top right    2
              0.5f,   0.5f, 0.0f,          1.0f, 1.0f, 0.0f, 1.0f, // Bottom left  3
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

    @Override
    public void init() {
        this.cam = new Camera(new Vector2f());
        System.out.println("LevelEditorScene");
        shader = new Shader("res/assets/shaders/vDefault.glsl", "res/assets/shaders/fDefault.glsl");

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
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * Float.BYTES;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float deltaTime) {
        shader.bind();
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
    }
}
