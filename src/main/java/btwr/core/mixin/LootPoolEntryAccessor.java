package btwr.core.mixin;

import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.LootPoolEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(LootPoolEntry.class)
public interface LootPoolEntryAccessor
{

    // Accessor method to get the conditions
    @Accessor("conditions")
    List<LootCondition> getConditions();

    // Accessor method to set the conditions
    @Accessor("conditions")
    void setConditions(List<LootCondition> conditions);
}
