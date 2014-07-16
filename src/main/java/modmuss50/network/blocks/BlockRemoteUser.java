package modmuss50.network.blocks;

import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityRemoteUser;
import modmuss50.network.client.gui.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRemoteUser extends BlockBase {

	public BlockRemoteUser() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityRemoteUser();
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {

		TileEntityRemoteUser tileentity = (TileEntityRemoteUser) par1World.getTileEntity(par2, par3, par4);

		if (tileentity != null) {
			par5EntityPlayer.openGui(NetworkCore.instance, GuiHandler.BlockRemoteUserID, par1World, par2, par3, par4);
		}

		return true;

	}

}
