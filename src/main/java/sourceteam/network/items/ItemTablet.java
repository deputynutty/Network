package sourceteam.network.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sourceteam.network.NetworkCore;
import sourceteam.network.api.IWirelessItem;
import sourceteam.network.client.gui.GuiHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemTablet extends IWirelessItem {

    public Tablet[] types = {new Tablet("Basic", "basic"), new Tablet("Basic+ ", "basic_mk2")};
    public String[] states = {"uncharged", "charged", "broken"};
    @SideOnly(Side.CLIENT)
    private IIcon[] tabletIcons;

    public ItemTablet() {
        super();
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item.sourceteam.network.tablet_" + stack.getItemDamage();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister icon) {
        tabletIcons = new IIcon[types.length * states.length];

        for (int i = 0; i < types.length * states.length; i++) {
            tabletIcons[i] = icon.registerIcon("network:tablet_" + types[i / states.length].textureName + "_" + states[i % states.length]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int dmg) {
        return tabletIcons[dmg];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < types.length * states.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() % states.length == 1) {
            player.openGui(NetworkCore.instance, GuiHandler.tabletGuiID, world, (int) player.posX, (int) player.posY, (int) player.posZ);
            return stack;
        } else {
            return stack;
        }
    }

}
