package dev.iseal.sealparticleplayer.client.Shaders.Flash;


import dev.iseal.sealparticleplayer.client.HFPPClient;
import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.postprocess.MultiInstancePostProcessor;

public class FlashPostProcessor extends MultiInstancePostProcessor<FlashShaderFx> {
    public static final FlashPostProcessor INSTANCE = new FlashPostProcessor();
    private JsonEffectShaderProgram effectFlash;

    @Override
    public Identifier getPostChainLocation() {
        return new Identifier(HFPPClient.MODID, "flash_screen");
    }
    // Max amount of FxInstances that can be added to the post processor at once
    @Override
    protected int getMaxInstances() {
        return 16;
    }

    // We passed in a total of 6 floats/uniforms to the shader inside our LightingFx class so this should return 6, will crash if it doesn't match
    @Override
    protected int getDataSizePerInstance() {
        return 4;
    }

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectFlash = effects[0];
        }
    }

    @Override
    public void beforeProcess(MatrixStack viewModelStack) {
        super.beforeProcess(viewModelStack);
        setDataBufferUniform(effectFlash, "DataBuffer", "InstanceCount");
    }

    @Override
    public void afterProcess() {

    }
}