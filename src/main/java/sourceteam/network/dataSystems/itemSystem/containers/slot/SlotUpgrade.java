package sourceteam.network.dataSystems.itemSystem.containers.slot;

import sourceteam.mods.lib.api.IupgradeItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Mark on 28/08/2014.
 */
public class SlotUpgrade extends Slot {
    public SlotUpgrade(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Override
        public boolean isItemValid(ItemStack itemStack)
        {
                return itemStack.getItem() instanceof IupgradeItem;
        }
}
