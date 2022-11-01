package dev.venturex.engine.renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;

public class Shader {

    private int vertexId, fragmentId, programId;
    private String vertexSource, fragmentSource;
    public Shader(String vertexFile, String fragmentFile){
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

    public void bind(){
        glUseProgram(programId);
    }

    public void unbind(){
        glUseProgram(0);
    }
}
