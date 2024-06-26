package btwr.core.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

/** Contains all the default food components used in BTWR food items. **/
public class ModFoodComponents
{
    // Raw
    public static final FoodComponent EGG_RAW =
            new FoodComponent.Builder()
                    .hunger(2)
                    .saturationModifier(0.003f)
                    .statusEffect(silentHungerStatusEffect(1200, 2), 0.4f)
                    .build();
    public static final FoodComponent EGG_SCRAMBLED_RAW =
            new FoodComponent.Builder()
                    .hunger(3)
                    .saturationModifier(0.008f)
                    .statusEffect(silentHungerStatusEffect(1200, 2), 0.4f)
                    .build();


    // Cooked

    /** Poached ang Fried egg have the same food component. **/
    public static final FoodComponent EGG_COOKED =
            new FoodComponent.Builder().hunger(3).saturationModifier(0.008f).build();
    public static final FoodComponent EGG_SCRAMBLED_COOKED =
            new FoodComponent.Builder().hunger(4).saturationModifier(0.012f).build();

    public static final FoodComponent SANDWICH =
            new FoodComponent.Builder().hunger(5).saturationModifier(0.012f).build();

    private static StatusEffectInstance silentHungerStatusEffect(int dur, int amp)
    {
        return new StatusEffectInstance(StatusEffects.HUNGER, dur, amp, false, false, false);
    }

}
