package modmuss50.network.items;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import modmuss50.network.NetworkCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by mark on 18/09/2014.
 */
public class ItemDrone extends Item {

	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {

		//FMLNetworkHandler.openGui(entityPlayer, NetworkCore.instance, 15, world, x, y, z);
        entityPlayer.openGui(NetworkCore.instance, 15, world, x, y, z);
		return true;
	}
}
