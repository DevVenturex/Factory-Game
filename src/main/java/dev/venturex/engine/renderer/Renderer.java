package dev.venturex.engine.renderer;

import dev.venturex.components.SpriteRenderer;
import dev.venturex.engine.Component;
import dev.venturex.engine.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 10000;
    private List<RenderBatch> batches;

    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void add(GameObject obj){
        SpriteRenderer sprite = obj.getComponent(SpriteRenderer.class);
        if (sprite != null){
            add(sprite);
        }
    }

    public void add(SpriteRenderer sprite){
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom()) {
                batch.addSprite(sprite);
                added = true;
                break;
            }
        }
        if (!added){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.init();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
        }
    }

    public void render(){
        for (RenderBatch batch : batches){
            batch.render();
        }
    }
}
