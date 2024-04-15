package btwr.core.material;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ToolMaterial;

public interface ModifyMaterialEvent
{
    Event<Listener> EVENT = EventFactory.createArrayBacked(Listener.class,
            (listeners) -> (material, currentDurability, currentSpeed) ->
            {
                ModifyResult result = ModifyResult.pass();

                for (Listener listener : listeners)
                {
                    result = listener.modify(material, currentDurability, currentSpeed);

                    if (result.isModified())
                    {
                        return result;
                    }
                }
                return result;
            }
            );

    interface Listener
    {
        ModifyResult modify(ToolMaterial material, int currentDurability, float currentSpeed);
    }

    class ModifyResult
    {
        private final boolean modified;
        private final int newValueInt;
        private final float newValueFloat;

        private ModifyResult(boolean modified, int newValueInt, float newValueFloat) {
            this.modified = modified;
            this.newValueInt = newValueInt;
            this.newValueFloat = newValueFloat;
        }

        public boolean isModified()
        {
            return modified;
        }

        public int getNewValueInt() {
            return newValueInt;
        }

        public float getNewValueFloat() {
            return newValueFloat;
        }

        public static ModifyResult pass()
        {
            return new ModifyResult(false, 0, 0);
        }

        public static ModifyResult success(int newValueInt, float newValueFloat)
        {
            return new ModifyResult(true, newValueInt, newValueFloat);
        }
    }
}
