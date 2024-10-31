package btwr.core.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;

/** Contains all the default food components used in BTWR food items. **/
public class ModFoodComponents
{
    // Cauldron-based food components
    public static final FoodComponent CHOWDER =
            new FoodComponent.Builder()
                    .nutrition(5)
                    .saturationModifier(0.35f)
                    .build();

    public static final FoodComponent BOILED_POTATO =
            new FoodComponent.Builder()
                    .nutrition(1)
                    .saturationModifier(0.25f)
                    .build();


    // Raw foods
    public static final FoodComponent EGGS_SCRAMBLED_RAW =
            new FoodComponent.Builder()
                    .nutrition(3)
                    .saturationModifier(0.2f)
                    .statusEffect(addHungerStatusEffect(600, 2), 0.3f)
                    .build();

    public static final FoodComponent MUSHROOM_OMELETTE_RAW =
            new FoodComponent.Builder()
                    .nutrition(3)
                    .saturationModifier(0.25f)
                    .statusEffect(addHungerStatusEffect(600, 2), 0.3f)
                    .build();

    public static final FoodComponent KEBAB_RAW =
            new FoodComponent.Builder()
                    .nutrition(6)
                    .saturationModifier(0.25f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600,2), 0.3f)
                    .build();


    // Cooked foods
    public static final FoodComponent COOKED_CARROT =
            new FoodComponent.Builder()
                    .nutrition(2)
                    .saturationModifier(0.25f)
                    .build();

    public static final FoodComponent EGG_SCRAMBLED_COOKED =
            new FoodComponent.Builder()
                    .nutrition(4)
                    .saturationModifier(0.35f)
                    .build();

    public static final FoodComponent MUSHROOM_OMELETTE_COOKED =
            new FoodComponent.Builder()
                    .nutrition(4)
                    .saturationModifier(0.35f)
                    .build();

    public static final FoodComponent SANDWICH =
            new FoodComponent.Builder()
                    .nutrition(5)
                    .saturationModifier(0.45f)
                    .build();

    public static final FoodComponent HAM_AND_EGGS =
            new FoodComponent.Builder()
                    .nutrition(6)
                    .saturationModifier(0.40f)
                    .build();

    public static final FoodComponent STEAK_AND_POTATOES =
            new FoodComponent.Builder()
                    .nutrition(6)
                    .saturationModifier(0.40f)
                    .build();

    public static final FoodComponent KEBAB_COOKED =
            new FoodComponent.Builder()
                    .nutrition(8)
                    .saturationModifier(0.45f)
                    .build();

    public static final FoodComponent STEAK_DINNER =
            new FoodComponent.Builder()
                    .nutrition(8)
                    .saturationModifier(0.42f)
                    .build();

    public static final FoodComponent PORK_DINNER =
            new FoodComponent.Builder()
                    .nutrition(8)
                    .saturationModifier(0.42f)
                    .build();

    public static final FoodComponent WOLF_DINNER =
            new FoodComponent.Builder()
                    .nutrition(8)
                    .saturationModifier(0.40f)
                    .build();

    public static final FoodComponent CHICKEN_SOUP = createStew(8, 0.40f).build();
    public static final FoodComponent HEARTY_STEW = createStew(10, 0.46f).build();



    // Weird foods
    public static final FoodComponent CREEPER_OYSTERS =
            new FoodComponent.Builder()
                    .nutrition(1)
                    .saturationModifier(0.10f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100,0),1f)
                    .build();

    public static final FoodComponent BEAST_LIVER_RAW =
            new FoodComponent.Builder()
                    .nutrition(5)
                    .saturationModifier(0.08f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600,1),0.3f)
                    .build();

    public static final FoodComponent BEAST_LIVER_COOKED =
            new FoodComponent.Builder()
                    .nutrition(6)
                    .saturationModifier(0.15f)
                    .build();


    // TODO: create a multiple options handling version of this method
    private static FoodComponent.Builder createStew(int hunger, float saturation) {
        return (new FoodComponent.Builder()).nutrition(hunger).saturationModifier(saturation).usingConvertsTo(Items.BOWL);
    }

    private static StatusEffectInstance addHungerStatusEffect(int dur, int amp)
    {
        return new StatusEffectInstance(StatusEffects.HUNGER, dur, amp, false, false, false);
    }

}
