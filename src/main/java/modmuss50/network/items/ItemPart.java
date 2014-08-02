package modmuss50.network.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemPart extends Item {

    public static String[] parts = {"ReinforcedIron", "HardenedIron", "HardenedIronPlate", "ReinforcedIronPlate", "Scale", "North", "South", "East", "West", "Up", "Down", "InfusedIron", "InfusedIronPlate", "GlassStrip", "ReinforcedGlassStrip", "InfusedGlassStrip", "FiberCable", "GlassPlate", "Coil", "BasicTank", "ReinforcedTank", "InfusedTank", "Player", "Item", "Fluid"};

    public IIcon[] icons = new IIcon[parts.length];

    public ItemPart() {
        super();
        setMaxStackSize(64);
        hasSubtypes = true;
    }

    public static ItemStack getItemstack(String name) {
        return getItemstack(name, 1);
    }

    public static ItemStack getItemstack(String name, int size) {
        int meta = getMeta(name);
        if (meta == 777666555)
            return null;
        return new ItemStack(NetworkItems.parts, size, meta);
    }

    public static int getMeta(String name) {
        if (name.equals(parts[0]))
            return 0;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals(name)) {
                return i;
            }
        }
        return 777666555;
    }

    @Override
    public void registerIcons(IIconRegister icon) {
        for (int j = 0; j < parts.length; j++) {
            icons[j] = icon.registerIcon("network:" + parts[j]);
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int j = 0; j < parts.length; j++) {
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return icons[par1];
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + parts[itemstack.getItemDamage()];
    }
}
