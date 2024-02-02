package btwr.core.client.fx;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class EffectHandler {
    public static Map<Integer, Effect> effectMap = new HashMap<>();

    public static boolean playEffect(int effectID, MinecraftServer server, World world, PlayerEntity player, BlockPos pos , BlockState state) {
        Effect effect = effectMap.get(state);

        BlockPos soundPos = new BlockPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

        if (effect != null) {
            effect.playEffect(server, world, player, soundPos, state);
            return true;
        }

        return false;
    }

    public interface Effect {
        void playEffect(MinecraftServer server, World world, PlayerEntity player, BlockPos pos, BlockState state);
    }
}
