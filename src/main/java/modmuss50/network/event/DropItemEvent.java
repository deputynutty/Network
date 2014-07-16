package modmuss50.network.event;

import modmuss50.network.items.NetworkItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DropItemEvent {

	@SubscribeEvent
	public void onItemTossed(ItemTossEvent e) {
		ItemStack item = e.entityItem.getEntityItem();
		if (item.getItem() == NetworkItems.tablet && item.getItemDamage() != 2) {
			e.entityItem.getEntityItem().setItemDamage(2);
		}
	}
}
