package dev.iseal.sealparticleplayer.client.Handlers;

import dev.iseal.ExtraKryoCodecs.Enums.Effekt;
import dev.iseal.ExtraKryoCodecs.Holders.ScreenFlashHolder;
import dev.iseal.ExtraKryoCodecs.Holders.WorldParticleBuilderHolder;
import dev.iseal.ExtraKryoCodecs.Utils.WorldParticleHolderToBuilder;
import dev.iseal.sealparticleplayer.client.Misc.Utils;
import dev.iseal.sealparticleplayer.client.Shaders.Flash.FlashPostProcessor;
import dev.iseal.sealparticleplayer.client.Shaders.Flash.FlashShaderFx;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
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
                handleParticle(holder);
                break;
            case SCREEN_FLASH:
                ScreenFlashHolder flashHolder = (ScreenFlashHolder) effekt;
                Vector3f color = new Vector3f(flashHolder.getColorR(), flashHolder.getColorG(), flashHolder.getColorB());
                FlashPostProcessor.INSTANCE.addFxInstance(new FlashShaderFx(color, flashHolder.getDuration(), flashHolder.getIntensity()));
                break;
        }

    }

    private static void handleParticle(WorldParticleBuilderHolder holder) {
        MinecraftClient client = MinecraftClient.getInstance();
        WorldParticleBuilder builder = WorldParticleHolderToBuilder.getInstance().create(holder);
        if (client.world == null) {
            return;
        }

        switch (holder.getParticleEffect()) {
            case SPAWN:
                    builder.spawn(client.world, holder.getXLocation(), holder.getYLocation(), holder.getZLocation());
                    break;
            case REPEAT:
                    builder.repeat(client.world, holder.getXLocation(), holder.getYLocation(), holder.getZLocation(),
                            holder.getRepeatCount());
                    break;
            case SURROUND_BLOCK:
                int[] sbDirections = holder.getDirections();
                Direction[] sbConvDirections = new Direction[sbDirections.length];
                for (int i = 0; i < sbDirections.length; i++) {
                    sbConvDirections[i] = Direction.byId(sbDirections[i]);
                }
                builder.surroundBlock(client.world, Utils.fromCoords(holder.getXLocation(), holder.getYLocation(), holder.getZLocation()),
                        sbConvDirections);
                break;
            case REPEAT_SURROUND_BLOCK:
                int[] rsbDirections = holder.getDirections();
                Direction[] rsbConvDirections = new Direction[rsbDirections.length];
                for (int i = 0; i < rsbDirections.length; i++) {
                    rsbConvDirections[i] = Direction.byId(rsbDirections[i]);
                }
                builder.repeatSurroundBlock(client.world, Utils.fromCoords(holder.getXLocation(), holder.getYLocation(), holder.getZLocation()),
                        holder.getRepeatCount(), rsbConvDirections);
                break;
            case SPAWN_AT_RANDOM_FACE:
                    builder.spawnAtRandomFace(client.world, Utils.fromCoords(holder.getXLocation(), holder.getYLocation(), holder.getZLocation()));
                    break;
            case REPEAT_AT_RANDOM_FACE:
                    builder.repeatRandomFace(client.world, Utils.fromCoords(holder.getXLocation(), holder.getYLocation(), holder.getZLocation()),
                            holder.getRepeatCount());
                    break;
            case CREATE_BLOCK_OUTLINE:
                BlockPos cboBlockPos = Utils.fromCoords(holder.getXLocation(), holder.getYLocation(), holder.getZLocation());
                builder.createBlockOutline(client.world, cboBlockPos, client.world.getBlockState(cboBlockPos));
                break;
            case SPAWN_LINE:
                builder.spawnLine(client.world,
                    new Vec3d(holder.getStartingXLocation(), holder.getStartingYLocation(), holder.getStartingZLocation()),
                    new Vec3d(holder.getEndingXLocation(), holder.getEndingYLocation(), holder.getEndingZLocation()));
                break;
            default:
                System.out.println(holder.getParticleEffect().name() + " is not implemented yet! Returning...");
        }
    }

}
