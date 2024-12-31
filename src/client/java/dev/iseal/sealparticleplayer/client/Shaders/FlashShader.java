package dev.iseal.sealparticleplayer.client.Shaders;

import dev.iseal.sealparticleplayer.client.HFPPClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class FlashShader extends PostProcessor {
    public static final FlashShader INSTANCE = new FlashShader();
    // Static block added to turn the shader off by default
    static {
        INSTANCE.setActive(false);
    }

    @Override
    public Identifier getPostChainLocation() {
        return new Identifier(HFPPClient.MODID, "shaders/post/flash.json");
    }

    @Override
    public void beforeProcess(MatrixStack matrixStack) {

    }

    @Override
    public void afterProcess() {

    }
}
