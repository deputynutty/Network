package modmuss50.network.items;

import modmuss50.network.api.Item.IPowerSinkUpgrade;
import net.minecraft.item.Item;

public class ItemBattery extends Item implements IPowerSinkUpgrade {

    public ItemBattery() {
        super();
        setMaxStackSize(16);
    }

    @Override
    public int capacity() {
        return 1000000;
    }
}
