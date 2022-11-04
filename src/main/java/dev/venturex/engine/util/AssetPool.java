package dev.venturex.engine.util;

import dev.venturex.components.SpriteSheet;
import dev.venturex.engine.renderer.Shader;
import dev.venturex.engine.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture> textures = new HashMap<>();
    private static final Map<String, SpriteSheet> sheets = new HashMap<>();

    public static Shader getShader(String shaderName, String resourceName1, String resourceName2) {
        File file1 = new File(resourceName1);
        File file2 = new File(resourceName2);
        if (AssetPool.shaders.containsKey(shaderName)) {
            return AssetPool.shaders.get(shaderName);
        } else {
            Shader shader = new Shader(resourceName1, resourceName2);
            AssetPool.shaders.put(shaderName, shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
            return AssetPool.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture();
            texture.init(resourceName);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static void addSpriteSheet(String resourceName, SpriteSheet sheet) {
        File file = new File(resourceName);
        if (!AssetPool.sheets.containsKey(file.getAbsolutePath())) {
            AssetPool.sheets.put(file.getAbsolutePath(), sheet);
        }
    }

    public static SpriteSheet getSpriteSheet(String resourceName) {
        File file = new File(resourceName);
        if (!AssetPool.sheets.containsKey(file.getAbsolutePath()))
            throw new RuntimeException("Error: Tried to access SpriteSheet '" + resourceName + "' and it has not been added to asset pool.");
        return AssetPool.sheets.getOrDefault(file.getAbsolutePath(), null);
    }
}
