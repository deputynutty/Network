package modmuss50.network.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import modmuss50.network.items.NetworkItems;

public class DropItemEvent {

    @SubscribeEvent
    public void onItemTossed(ItemTossEvent e) {
        ItemStack item = e.entityItem.getEntityItem();
        if (item.getItem() == NetworkItems.tablet && item.getItemDamage() != 2) {
            //This breaks the tablet when dropped
            e.entityItem.getEntityItem().setItemDamage(2);
        }
    }
}
