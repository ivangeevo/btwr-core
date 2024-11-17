package btwr.core.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
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
        // Ensure the player and world are not null
        if (player == null || world == null) {
            return Direction.NORTH; // Default direction
        }

        // Get the player's eye position
        Vec3d eyePosition = player.getCameraPosVec(1.0F);

        // Get the direction the player is facing
        Vec3d lookVector = player.getRotationVec(1.0F);

        // Calculate the endpoint of the raycast (max reach of 5 blocks)
        Vec3d reachEnd = eyePosition.add(lookVector.multiply(5.0D));

        // Perform the raycast in the world
        RaycastContext raycastContext = new RaycastContext(
                eyePosition,
                reachEnd,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                player
        );

        BlockHitResult hitResult = world.raycast(raycastContext);

        // Check if the raycast hit the target block
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK && hitResult.getBlockPos().equals(pos)) {
            // Return the opposite face of the block hit
            return hitResult.getSide().getOpposite();
        }

        // Default to NORTH if no valid block is hit
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
