package dev.iseal.sealparticleplayer.client.Shaders.Flash;

import org.joml.Vector3f;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.postprocess.DynamicShaderFxInstance;

import java.util.function.BiConsumer;

public class FlashShaderFx extends DynamicShaderFxInstance {

    public Vector3f color;
    public float intensity;
    private float duration;
    private final float initialDuration;
    private final float initialIntensity;

    public FlashShaderFx(Vector3f color, int ticks, float intensity) {
        this.color = color;
        this.duration = ticks / 20.0f; // Convert ticks to seconds
        this.initialDuration = duration;
        this.intensity = intensity;
        this.initialIntensity = intensity;
    }

    @Override
    public void writeDataToBuffer(BiConsumer<Integer, Float> writer) {
        writer.accept(0, color.x);
        writer.accept(1, color.y);
        writer.accept(2, color.z);
        writer.accept(3, intensity);
    }

    @Override
    public void update(double deltaTime) {
        float frameTimeInSeconds = (float) deltaTime / 20.0f;
        if (duration > 0) {
            duration -= frameTimeInSeconds;
            float elapsed = initialDuration - duration;
            // Update intensity using an easing function
            intensity = Easing.QUAD_IN_OUT.ease(elapsed, initialIntensity, -initialIntensity, initialDuration);
            // Ensure intensity does not go below zero
            intensity = Math.max(intensity, 0);
        } else {
            remove();
            duration = 0;
            color = new Vector3f(0, 0, 0);
            intensity = 0;
        }
        super.update(deltaTime);
    }
}