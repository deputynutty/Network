package modmuss50.network.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.NetworkCore;
import modmuss50.network.api.IWirelessItem;
import modmuss50.network.client.gui.GuiHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemTablet extends IWirelessItem {

	public Tablet[]	types	= { new Tablet("Basic", "basic"), new Tablet("Basic+ ", "basic_mk2")};
	public String[]	states	= { "uncharged", "charged", "broken" };

	public ItemTablet() {
		super();
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item.tablet_" + stack.getItemDamage();
	}

	@SideOnly(Side.CLIENT)
	private IIcon[]	tabletIcons;

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

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (stack.getItemDamage() % states.length == 1) {
			player.openGui(NetworkCore.instance, GuiHandler.tabletGuiID, world, x, y, z);
			return true;
		}
		else {
			return false;
		}
	}
}
