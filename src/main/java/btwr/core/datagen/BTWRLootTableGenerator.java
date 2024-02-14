package btwr.core.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;

public class BTWRLootTableGenerator extends FabricBlockLootTableProvider {


    protected BTWRLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

    }

    @Override
    public String getName() {
        return "BTWR Block Loot Tables";
    }
}
