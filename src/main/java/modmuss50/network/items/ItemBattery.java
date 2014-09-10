package modmuss50.network.items;

import net.minecraft.item.Item;
import modmuss50.network.api.Item.IPowerSinkUpgrade;

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
