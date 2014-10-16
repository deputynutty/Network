package modmuss50.network.blocks;

import modmuss50.mods.lib.multiblock.BlockMultiblockBase;
import modmuss50.mods.lib.multiblock.MultiblockValidationException;
import modmuss50.network.blocks.tileentities.multiblock.TileEntityMultiblockPower;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class BlockMultiBlockPower extends BlockMultiblockBase {
	public BlockMultiBlockPower() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMultiblockPower();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityMultiblockPower) {
			TileEntityMultiblockPower power = (TileEntityMultiblockPower) tileEntity;
			if (!power.getMultiblockController().isAssembled() && player.getCurrentEquippedItem() == null) {
				MultiblockValidationException ex = power.getMultiblockController().getLastValidationException();
				if (ex != null) {
					player.addChatMessage(new ChatComponentTranslation(ex.getMessage()));
					;
					return false;
				}
			} else {
				if (!world.isRemote)
					player.addChatMessage(new ChatComponentTranslation("POWER: " + Integer.toString(((TileEntityMultiblockPower) tileEntity).ENERGY_SYSTEM().getPower())));
				;
			}

		}
		return false;
	}
}
