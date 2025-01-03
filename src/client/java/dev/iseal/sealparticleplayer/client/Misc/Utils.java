package dev.iseal.sealparticleplayer.client.Misc;

import net.minecraft.util.math.BlockPos;

public class Utils {

    public static BlockPos fromCoords(double x, double y, double z) {
        return new BlockPos((int) x, (int) y, (int) z);
    }

}
