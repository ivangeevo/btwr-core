package btwr.core.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class VectorUtils
{

    public static Direction getMiningDirection(PlayerEntity player, World world, BlockPos pos)
    {
        // Get the player's eye position
        Vec3d start = player.getCameraPosVec(1.0F);

        // Calculate the look vector based on the player's pitch and yaw
        Vec3d end = getVec3d(player, start);

        RaycastContext context = new RaycastContext(
                start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player
        );

        BlockHitResult result = world.raycast(context);

        if (result != null)
        {
            Direction hitDirection = result.getSide();
            return hitDirection.getOpposite(); // Get the opposite direction
        }
        else
        {
            return null;
        }
    }

    @NotNull
    private static Vec3d getVec3d(PlayerEntity player, Vec3d start)
    {
        float pitch = player.getPitch();
        float yaw = player.getYaw();

        // Adjust the look vector to point at the center of the block
        double reachDistance = 5.0D; // Adjust the reach distance as needed
        double x = start.x + Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * reachDistance;
        double y = start.y + Math.sin(Math.toRadians(pitch)) * reachDistance;
        double z = start.z - Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * reachDistance;

        return new Vec3d(x, y, z);
    }



    public static Vec3d tiltVector(Vec3d originalVector, int facing)
    {
        double x = originalVector.x;
        double y = originalVector.y;
        double z = originalVector.z;

        switch (facing)
        {
            case 0 ->
            {
                // j - 1
                y = 1D - y;
                x = 1D - x;
            }
            case 2 ->
            {
                // k - 1
                double tempZ = 1D - y;
                y = originalVector.z;
                z = tempZ;
            }
            case 3 ->
            {
                // k + 1
                double tempZ = y;
                y = 1D - z;
                z = tempZ;
            }
            case 4 ->
            {
                // i - 1
                double tempY = x;
                x = 1D - y;
                y = tempY;
            }
            case 5 ->
            {
                // i + 1
                double tempY = 1D - x;
                x = y;
                y = tempY;
            }
        }

        return new Vec3d(x, y, z);

    }





}
