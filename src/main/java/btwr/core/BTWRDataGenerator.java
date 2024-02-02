package btwr.core;

import btwr.core.datagen.BTWRBlockTagProvider;
import btwr.core.datagen.BTWRItemTagProvider;
import btwr.core.datagen.BTWRModelGenerator;
import btwr.core.datagen.BTWRRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BTWRDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(BTWRRecipeProvider::new);
        fabricDataGenerator.addProvider(BTWRBlockTagProvider::new);
        fabricDataGenerator.addProvider(BTWRItemTagProvider::new);
        fabricDataGenerator.addProvider(BTWRModelGenerator::new);
    }

}
