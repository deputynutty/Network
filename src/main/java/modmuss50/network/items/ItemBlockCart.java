package modmuss50.network.items;

import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import modmuss50.network.entity.minecart.ServerCart;

public class ItemBlockCart extends ItemMinecart {

    public int minecartType;

    public ItemBlockCart(int type) {
        super(type);
        minecartType = type;
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (BlockRailBase.func_150051_a(par3World.getBlock(par4, par5, par6))) {
            if (!par3World.isRemote) {
                EntityMinecart entityminecart = new ServerCart(par3World, par4 + 0.5F, par5 + 0.5F, par6 + 0.5F);

                if (par1ItemStack.hasDisplayName()) {
                    entityminecart.setMinecartName(par1ItemStack.getDisplayName());
                }

                par3World.spawnEntityInWorld(entityminecart);
            }

            --par1ItemStack.stackSize;
            return true;
        } else {
            return false;
        }
    }

}
