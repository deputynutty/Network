package modmuss50.network.init;

import cpw.mods.fml.common.registry.GameRegistry;
import modmuss50.network.NetworkCore;
import modmuss50.network.api.InfusionFurnaceRecipes;
import modmuss50.network.blocks.NetworkBlocks;
import modmuss50.network.items.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;

public class initItems {

	public static String prefix = "modmuss50.network.";

	public static void loadItems() {
		NetworkItems.tablet = new ItemTablet().setCreativeTab(NetworkCore.Network);
		registerItem(NetworkItems.tablet);

		//TODO add this back
//        NetworkItems.serverCart = new ItemBlockCart(15).setCreativeTab(NetworkCore.Network).setUnlocalizedName(prefix + "serverCart");
//        registerItem(NetworkItems.serverCart);

		NetworkItems.wifiGoggles = new ItemArmor(ArmorMaterial.IRON, 0, 0).setCreativeTab(NetworkCore.Network).setUnlocalizedName(prefix + "wifiGoggles").setTextureName("network:wifiGoggles");
		registerItem(NetworkItems.wifiGoggles);

		NetworkItems.ItemBattery = new ItemBattery().setCreativeTab(NetworkCore.Network).setUnlocalizedName(prefix + "ItemBattery").setTextureName("network:Battery");
		registerItem(NetworkItems.ItemBattery);

		NetworkItems.parts = new ItemPart().setCreativeTab(NetworkCore.Network).setUnlocalizedName(prefix + "ItemPart").setTextureName("network:ItemPart");
		registerItem(NetworkItems.parts);

		NetworkItems.drone = new ItemDrone().setCreativeTab(NetworkCore.Network).setUnlocalizedName(prefix + "drone").setTextureName("network:drone");
		registerItem(NetworkItems.drone);

		NetworkItems.pinPad = new ItemPinPad().setCreativeTab(NetworkCore.Network).setUnlocalizedName(prefix + "pinPad").setTextureName("network:pinPad");
		registerItem(NetworkItems.pinPad);

	}

	public static void registerItem(Item item) {
		GameRegistry.registerItem(item, prefix + item.getUnlocalizedName());
	}

	public static void Recipes() {
		GameRegistry.addShapelessRecipe(ItemPart.getItemstack("HardenedIron", 9), Blocks.piston, Blocks.iron_block, Blocks.piston, Items.redstone);
		GameRegistry.addSmelting(ItemPart.getItemstack("HardenedIron"), ItemPart.getItemstack("ReinforcedIron"), 0F);
		GameRegistry.addShapelessRecipe(ItemPart.getItemstack("HardenedIronPlate", 1), ItemPart.getItemstack("HardenedIron"), ItemPart.getItemstack("HardenedIron"), ItemPart.getItemstack("HardenedIron"), ItemPart.getItemstack("HardenedIron"));
		GameRegistry.addShapelessRecipe(ItemPart.getItemstack("ReinforcedIronPlate", 1), ItemPart.getItemstack("ReinforcedIron"), ItemPart.getItemstack("ReinforcedIron"), ItemPart.getItemstack("ReinforcedIron"), ItemPart.getItemstack("ReinforcedIron"));

//		GameRegistry.addRecipe(new ItemStack(NetworkItems.tablet, 1), "ddd", "ddd", "ddd", 'd', blocks.dirt);
//		GameRegistry.addRecipe(new ItemStack(NetworkItems.serverCart, 1), "ddd", "ddd", "ddd", 'd', blocks.sand);

		InfusionFurnaceRecipes.smelting().addRecipe(ItemPart.getItemstack("ReinforcedIron"), ItemPart.getItemstack("InfusedIron"));
		InfusionFurnaceRecipes.smelting().addRecipe(ItemPart.getItemstack("ReinforcedIronPlate"), ItemPart.getItemstack("InfusedIronPlate"));
		InfusionFurnaceRecipes.smelting().addRecipe(ItemPart.getItemstack("ReinforcedGlassStrip"), ItemPart.getItemstack("InfusedGlassStrip"));
		InfusionFurnaceRecipes.smelting().addRecipe(ItemPart.getItemstack("ReinforcedTank"), ItemPart.getItemstack("InfusedTank"));

		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.powerSink, 1), "pbp", "pcp", "ppp", 'p', ItemPart.getItemstack("HardenedIronPlate"), 'b', Blocks.iron_bars, 'c', NetworkBlocks.computer);
		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.computer, 1), "pbp", "pcp", "ppp", 'p', ItemPart.getItemstack("HardenedIronPlate"), 'b', Blocks.redstone_block, 'c', Blocks.hopper);
		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.FluidGen, 1), "pbp", "ptp", "ppp", 'p', ItemPart.getItemstack("HardenedIronPlate"), 'b', Items.bucket, 't', ItemPart.getItemstack("BasicTank"));
		GameRegistry.addRecipe(ItemPart.getItemstack("BasicTank"), "ggg", "gbg", "ggg", 'b', Items.bucket, 'g', Blocks.glass_pane);
		GameRegistry.addRecipe(ItemPart.getItemstack("ReinforcedTank"), "iii", "iti", "iii", 'i', ItemPart.getItemstack("ReinforcedIronPlate"), 't', ItemPart.getItemstack("BasicTank"));
		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.ItemConveyor, 3), "   ", "   ", "iii", 'i', ItemPart.getItemstack("InfusedIronPlate"));
		GameRegistry.addShapelessRecipe(new ItemStack(NetworkBlocks.BouncyItemConveyor, 1), NetworkBlocks.ItemConveyor, Blocks.piston);
		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.monitor, 1), "ppp", "pgp", "ppp", 'p', ItemPart.getItemstack("InfusedIronPlate"), 'g', Items.glowstone_dust);
		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.lightPeripheral, 8), "plp", "pgp", "plp", 'p', ItemPart.getItemstack("InfusedIronPlate"), 'g', Items.glowstone_dust, 'l', Blocks.redstone_lamp);
		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.BlockSolarPanel, 1), "ggg", "ccc", "fsf", 'g', ItemPart.getItemstack("InfusedGlassStrip"), 'c', ItemPart.getItemstack("Coil"), 'f', ItemPart.getItemstack("FiberCable"), 's', Items.nether_star);
		GameRegistry.addRecipe(ItemPart.getItemstack("Coil", 4), "i i", " i ", "i i", 'i', ItemPart.getItemstack("InfusedIronPlate"));
		GameRegistry.addRecipe(ItemPart.getItemstack("FiberCable", 4), "ppp", "drd", "ppp", 'p', ItemPart.getItemstack("HardenedIron"), 'r', Items.redstone, 'd', Items.diamond);
		GameRegistry.addRecipe(ItemPart.getItemstack("ReinforcedGlassStrip", 1), "rge", "rgr", "rgr", 'r', ItemPart.getItemstack("ReinforcedIron"), 'g', ItemPart.getItemstack("GlassStrip"));
		GameRegistry.addRecipe(ItemPart.getItemstack("GlassStrip", 1), "rgr", "rgr", "rgr", 'r', ItemPart.getItemstack("HardenedIron"), 'g', Blocks.glass);
		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.NetworkedFurnace), "iii", "ifi", "iii", 'i', ItemPart.getItemstack("InfusedIron"), 'f', Blocks.furnace);
		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.infusionFurnace), "ldg", "cfc", "ehe", 'l', Blocks.lapis_block, 'd', Blocks.diamond_block, 'g', Blocks.gold_block, 'c', NetworkBlocks.networkCable, 'f', Blocks.furnace, 'e', Blocks.enchanting_table, 'h', Blocks.ender_chest);
		GameRegistry.addRecipe(new ItemStack(NetworkItems.ItemBattery), "ppp", "pfp", "pfp", 'p', ItemPart.getItemstack("InfusedIron"), 'f', ItemPart.getItemstack("FiberCable"));
		GameRegistry.addRecipe(new ItemStack(NetworkItems.wifiGoggles), "iii", "i i", "   ", 'i', ItemPart.getItemstack("InfusedIron"));
		GameRegistry.addRecipe(new ItemStack(NetworkBlocks.networkCable, 16), "ddd", "rrr", "ddd", 'r', ItemPart.getItemstack("FiberCable"), 'd', ItemPart.getItemstack("ReinforcedIron"));
	}

}
