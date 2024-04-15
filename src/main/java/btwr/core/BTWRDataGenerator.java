package btwr.core;

import btwr.core.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class BTWRDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(BTWRRecipeProvider::new);
        pack.addProvider(BTWRBlockTagProvider::new);
        pack.addProvider(BTWRItemTagProvider::new);
        pack.addProvider(BTWRModelGenerator::new);

    }

}
