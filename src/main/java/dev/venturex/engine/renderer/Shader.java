package dev.venturex.engine.renderer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private final int vertexId;
    private final int fragmentId;
    private final int programId;
    private String vertexSource, fragmentSource;

    public Shader(String vertexFile, String fragmentFile) {
        try {
            vertexSource = new String(Files.readAllBytes(Paths.get(vertexFile)));
            fragmentSource = new String(Files.readAllBytes(Paths.get(fragmentFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, vertexSource);
        glCompileShader(vertexId);
        if (glGetShaderi(vertexId, GL_COMPILE_STATUS) == 0)
            throw new RuntimeException("Error compiling vertex shader code: " + glGetShaderInfoLog(vertexId, 1024));

        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, fragmentSource);
        glCompileShader(fragmentId);
        if (glGetShaderi(fragmentId, GL_COMPILE_STATUS) == 0)
            throw new RuntimeException("Error compiling fragment shader code: " + glGetShaderInfoLog(fragmentId, 1024));

        programId = glCreateProgram();
        glAttachShader(programId, vertexId);
        glAttachShader(programId, fragmentId);
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0)
            throw new RuntimeException("Error linking shader program: " + glGetProgramInfoLog(programId, 1024));
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void setMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(programId, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void setTexture(String varName, int slot) {
        int varLocation = glGetUniformLocation(programId, varName);
        glUniform1i(varLocation, slot);
    }

    public void setIntArray(String varName, int[] array) {
        int varLocation = glGetUniformLocation(programId, varName);
        bind();
        glUniform1iv(varLocation, array);
    }
}
