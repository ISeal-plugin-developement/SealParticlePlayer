package dev.iseal.sealparticleplayer.client.Listeners;

import dev.iseal.ExtraKryoCodecs.Enums.Effekt;
import dev.iseal.ExtraKryoCodecs.Holders.WorldParticleBuilderHolder;
import dev.iseal.ExtraKryoCodecs.Utils.WorldParticleHolderToBuilder;
import net.minecraft.client.MinecraftClient;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

public class EffektHandler {

    public static void handleEffekt(Object effekt, Effekt effektType) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) {
            return;
        }
        if (effekt == null || effektType == null) {
            return;
        }
        switch (effektType) {
            case SCREENSHAKE:
                ScreenshakeInstance inst = (ScreenshakeInstance) effekt;
                ScreenshakeHandler.addScreenshake(inst);
                break;
            case PARTICLE:
                WorldParticleBuilderHolder holder = (WorldParticleBuilderHolder) effekt;
                WorldParticleHolderToBuilder.getInstance().create(holder)
                        .spawn(client.world, holder.getXLocation(), holder.getYLocation(), holder.getZLocation());
                break;
        }

    }
}
