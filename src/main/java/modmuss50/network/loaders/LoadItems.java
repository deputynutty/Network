package modmuss50.network.loaders;

import modmuss50.network.NetworkCore;
import modmuss50.network.items.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class LoadItems {

	public static void loadItems() {
		NetworkItems.tablet = new ItemTablet().setCreativeTab(NetworkCore.Network);
		registerItem(NetworkItems.tablet);

		NetworkItems.serverCart = new ItemBlockCart(15).setCreativeTab(NetworkCore.Network).setUnlocalizedName("serverCart");
		registerItem(NetworkItems.serverCart);

		NetworkItems.wifiLinker = new ItemWifiLinker().setCreativeTab(NetworkCore.Network).setUnlocalizedName("wifiLinker").setTextureName("network:wifiLinker");
		registerItem(NetworkItems.wifiLinker);

		NetworkItems.wifiGoggles = new ItemArmor(ArmorMaterial.IRON, 0, 0).setCreativeTab(NetworkCore.Network).setUnlocalizedName("wifiGoggles").setTextureName("network:wifiGoggles");
		registerItem(NetworkItems.wifiGoggles);

		NetworkItems.ItemBattery = new ItemBattery().setCreativeTab(NetworkCore.Network).setUnlocalizedName("ItemBattery").setTextureName("network:Battery");
		registerItem(NetworkItems.ItemBattery);

		NetworkItems.parts = new ItemPart().setCreativeTab(NetworkCore.Network).setUnlocalizedName("ItemPart").setTextureName("network:ItemPart");
		registerItem(NetworkItems.parts);

	}

	public static void registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName().replace("item.", ""));
	}

	public static void Recipes() {
		GameRegistry.addSmelting(Items.iron_ingot, ItemPart.getItemstack("Hardened Iron"), 0F);
		GameRegistry.addSmelting(ItemPart.getItemstack("Hardened Iron"), ItemPart.getItemstack("Reinforced Iron"), 0F);

		GameRegistry.addShapelessRecipe(ItemPart.getItemstack("Hardened Iron Plate", 1), ItemPart.getItemstack("Hardened Iron"), ItemPart.getItemstack("Hardened Iron"), ItemPart.getItemstack("Hardened Iron"), ItemPart.getItemstack("Hardened Iron"));
		GameRegistry.addShapelessRecipe(ItemPart.getItemstack("Reinforced Iron Plate", 1), ItemPart.getItemstack("Reinforced Iron"), ItemPart.getItemstack("Reinforced Iron"), ItemPart.getItemstack("Reinforced Iron"), ItemPart.getItemstack("Reinforced Iron"));

		GameRegistry.addRecipe(new ItemStack(NetworkItems.tablet, 1), "ddd", "ddd", "ddd", 'd', Blocks.dirt);
		GameRegistry.addRecipe(new ItemStack(NetworkItems.serverCart, 1), "ddd", "ddd", "ddd", 'd', Blocks.sand);
	}

}
