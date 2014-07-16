package modmuss50.network.blocks;

import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityPowerUserBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPowerUserBase extends BlockBase {

	public BlockPowerUserBase() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPowerUserBase();
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		TileEntityPowerUserBase tile = (TileEntityPowerUserBase) par1World.getTileEntity(par2, par3, par4);

		ItemStack stack = par5EntityPlayer.inventory.getCurrentItem();

		TileEntityPowerUserBase tileentity = (TileEntityPowerUserBase) par1World.getTileEntity(par2, par3, par4);

		if (tileentity != null) {
			par5EntityPlayer.openGui(NetworkCore.instance, 4, par1World, par2, par3, par4);
		}

		return true;

	}

}
