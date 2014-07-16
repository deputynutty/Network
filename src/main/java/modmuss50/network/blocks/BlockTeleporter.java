package modmuss50.network.blocks;

import modmuss50.network.blocks.tileentities.TileEntityTeleporter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Mark on 19/04/14.
 */
public class BlockTeleporter extends BlockBase{


	public BlockTeleporter() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTeleporter();
	}



	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {

		TileEntityTeleporter te;
		te = (TileEntityTeleporter) par1World.getTileEntity(par2, par3, par4);

		if (!par1World.isRemote)
			par5EntityPlayer.addChatMessage(new ChatComponentText("The Cable is conected to:" + Integer.toString(te.getSerX()) + " " + Integer.toString(te.getSerY()) + " " + Integer.toString(te.getSerZ())));

		return true;
	}

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }


    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }


}
