package dev.iseal.sealparticleplayer.client;

import dev.iseal.sealparticleplayer.client.Listeners.ClientNetworkingListener;
import dev.iseal.sealparticleplayer.client.items.ParticleTestItem;
import dev.iseal.sealparticleplayer.client.items.ScreenshakeTestItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

public class HFPPClient implements ClientModInitializer {

    public static final String MODID = "sealparticleplayer";

    private final ScreenshakeTestItem screenshakeTestItem = Registry.register(
            Registries.ITEM, new Identifier(MODID, "screenshaketest"), new ScreenshakeTestItem(new FabricItemSettings())
    );

    private final ParticleTestItem particleTestItem = Registry.register(
            Registries.ITEM, new Identifier(MODID, "particletest"), new ParticleTestItem(new FabricItemSettings())
    );

    @Override
    public void onInitializeClient() {
        ClientNetworkingListener clientNetworkingListener = new ClientNetworkingListener();
        clientNetworkingListener.initialize();
    }
}