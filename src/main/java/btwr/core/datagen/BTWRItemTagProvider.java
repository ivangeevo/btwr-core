package btwr.core.datagen;

import btwr.core.tag.BTWRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;

public class BTWRItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public BTWRItemTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {

        getOrCreateTagBuilder(BTWRTags.Items.AXES_MAKE_PLANKS)
                .add(Items.IRON_AXE)
                .add(Items.DIAMOND_AXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.NETHERITE_AXE);


    }
}
