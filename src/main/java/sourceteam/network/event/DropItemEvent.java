package sourceteam.network.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import sourceteam.network.items.NetworkItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;

public class DropItemEvent {

    @SubscribeEvent
    public void onItemTossed(ItemTossEvent e) {
        ItemStack item = e.entityItem.getEntityItem();
        if (item.getItem() == NetworkItems.tablet && item.getItemDamage() != 2) {
            e.entityItem.getEntityItem().setItemDamage(2);
        }
    }
}
