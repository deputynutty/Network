package modmuss50.network.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRTeleporter extends ItemBlock {

	public ItemBlockRTeleporter(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int dmg) {
		return dmg;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "teleporter_" + stack.getItemDamage();
	}

}
