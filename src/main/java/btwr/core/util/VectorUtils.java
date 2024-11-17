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

    /**
     * Determines the direction in which the block is being mined.
     *
     * @param player The player mining the block (can be null on the server).
     * @param world  The world where the block is located.
     * @param pos    The block position.
     * @return The direction from which the block is being mined, or NORTH if unavailable.
     */
    public static Direction getMiningDirection(PlayerEntity player, World world, BlockPos pos) {
        // Check if the player is null
        if (player == null) {
            // Default to NORTH when player is unavailable
            return Direction.NORTH;
        }

        // Get the player's eye position
        Vec3d start = player.getCameraPosVec(1.0F);

        // Calculate the end vector for the raycast
        Vec3d end = calculateReachVector(player, start, 5.0D); // 5 blocks reach distance

        // Create a raycast context
        RaycastContext context = new RaycastContext(
                start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player
        );

        // Perform the raycast
        BlockHitResult result = world.raycast(context);

        if (result != null && result.getBlockPos().equals(pos)) {
            // Return the opposite of the hit side to represent the mining direction
            return result.getSide().getOpposite();
        }

        // Default to NORTH if no valid result
        return Direction.NORTH;
    }

    /**
     * Calculates the reach vector based on the player's rotation.
     *
     * @param player       The player (must not be null).
     * @param start        The starting position (eye position).
     * @param reachDistance The maximum reach distance.
     * @return The endpoint of the raycast vector.
     */
    private static Vec3d calculateReachVector(PlayerEntity player, Vec3d start, double reachDistance) {
        float pitch = player.getPitch();
        float yaw = player.getYaw();

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
