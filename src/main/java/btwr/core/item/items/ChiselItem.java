package btwr.core.item.items;

import btwr.core.tag.BTWRTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChiselItem extends MiningToolItem
{
    private final ChiselType chiselType;
    public enum ChiselType { WOOD, STONE, IRON, DIAMOND }

    public ChiselType getType() {
        return chiselType;
    }

    public ChiselItem(float attackDamage, float attackSpeed, ToolMaterials material, ChiselType chiselType, Settings settings)
    {

        super(attackDamage, attackSpeed, material, BTWRTags.Mineable.CHISEL_MINEABLE, settings);
        this.chiselType = chiselType;

    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner)
    {

        if (state.isIn(BlockTags.LOGS) && state.isIn(BTWRTags.Blocks.STUMPS))
        {
            stack.damage(5, miner, p -> p.sendToolBreakStatus(miner.getActiveHand()) );
        }

        return super.postMine(stack, world, state, pos, miner);

    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player)
    {

        BlockPos thisPos = player.getBlockPos();
        SoundEvent craftingSound;

        if (chiselType == ChiselType.WOOD)
        {

            craftingSound = SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
            world.playSound(player, thisPos, craftingSound, SoundCategory.BLOCKS, 0.2f, 1.2f);


        }
        else  if (chiselType == ChiselType.STONE)
        {

            craftingSound = SoundEvents.BLOCK_ANVIL_USE;
            world.playSound(player,thisPos, craftingSound, SoundCategory.BLOCKS,0.2f,0.6f);

        }
        else if (chiselType == ChiselType.IRON || chiselType == ChiselType.DIAMOND)
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_USE;
            world.playSound(player,thisPos, craftingSound, SoundCategory.BLOCKS,0.2f,1.2f);

        }

        player.tick();

    }

}