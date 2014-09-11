package modmuss50.network.compact;


import cpw.mods.fml.common.registry.GameRegistry;
import modmuss50.network.blocks.NetworkBlocks;
import modmuss50.network.items.ItemPart;
import modmuss50.network.multiparts.Multipart;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CommonFMP {

    public static void init(){
        Multipart.init();

        GameRegistry.addRecipe(new ItemStack(Multipart.cablepartitem, 16), "ddd", "rrr", "ddd", 'r', ItemPart.getItemstack("FiberCable"), 'd', ItemPart.getItemstack("ReinforcedIron"));
        GameRegistry.addRecipe(new ItemStack(Multipart.itemPartWire), "   ", "fff", "   ", 'f', ItemPart.getItemstack("FiberCable"));
        GameRegistry.addRecipe(new ItemStack(Multipart.itemPartWireNFC), " d ", "fff", "   ", 'f', ItemPart.getItemstack("FiberCable"), 'd', Items.diamond);
        GameRegistry.addRecipe(new ItemStack(NetworkBlocks.infusionFurnace), "ldg", "cfc", "ehe", 'l', Blocks.lapis_block, 'd', Blocks.diamond_block, 'g', Blocks.gold_block, 'c', Multipart.cablepartitem, 'f', Blocks.furnace, 'e', Blocks.enchanting_table, 'h', Blocks.ender_chest);
    }

}
