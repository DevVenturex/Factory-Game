package dev.venturex.engine.util;

import dev.venturex.engine.renderer.Shader;
import dev.venturex.engine.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();

    public static Shader getShader(String shaderName, String resourceName1, String resourceName2){
        File file1 = new File(resourceName1);
        File file2 = new File(resourceName2);
        if (AssetPool.shaders.containsKey(shaderName)){
            return AssetPool.shaders.get(shaderName);
        } else {
            Shader shader = new Shader(resourceName1, resourceName2);
            AssetPool.shaders.put(shaderName, shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName){
        File file = new File(resourceName);
        if (AssetPool.textures.containsKey(file.getAbsolutePath())){
            return AssetPool.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture(resourceName);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }
}
