package sourceteam.network.items;

import net.minecraft.item.Item;
import sourceteam.network.api.Item.IPowerSinkUpgrade;

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
