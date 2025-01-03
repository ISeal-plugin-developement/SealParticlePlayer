package dev.iseal.sealparticleplayer.client;

import dev.iseal.sealparticleplayer.client.Listeners.ClientNetworkingListener;
import dev.iseal.sealparticleplayer.client.Shaders.Flash.FlashPostProcessor;
import net.fabricmc.api.ClientModInitializer;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

public class HFPPClient implements ClientModInitializer {

    public static final String MODID = "sealparticleplayer";

    @Override
    public void onInitializeClient() {
        ClientNetworkingListener clientNetworkingListener = new ClientNetworkingListener();
        clientNetworkingListener.initialize();
        PostProcessHandler.addInstance(FlashPostProcessor.INSTANCE);
    }
}