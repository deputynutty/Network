package modmuss50.network.blocks;

import modmuss50.network.blocks.tileentities.TileEntityCable;
import modmuss50.network.blocks.tileentities.TileEntityPowerImputCable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 24/02/14 Time: 13:12
 */
public class BlockNetworkCable extends BlockBase {

	public BlockNetworkCable() {
		super(Material.circuits);
		// setBlockBounds(0.3F, 0.3F, 0.3F, 0.6F, 0.6F, 0.6F);
	}

	// @Override
	// public TileEntity createNewTileEntity(World var1, int var2) {
	// return new TileEntityCable();
	// }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityCable();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}


	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity) {
		//Doing this to fix old tiles in the world
		//Can be removed in 1.9
		if(world.getTileEntity(x, y, z) instanceof TileEntityPowerImputCable){
			world.setTileEntity(x, y, z, new TileEntityCable());
		}
		TileEntityCable theTile = (TileEntityCable) world.getTileEntity(x, y, z);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);

		TileEntityCable theTile = (TileEntityCable) world.getTileEntity(x, y, z);

		if (theTile != null) {
			if (theTile.sideCache[1] != 0 & theTile.sideCache[0] != 0) {
				setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
				super.setBlockBoundsBasedOnState(world, x, y, z);
				return;
			}
			if (theTile.sideCache[0] != 0) {
				setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.7F, 0.7F);
				super.setBlockBoundsBasedOnState(world, x, y, z);
				return;
			}
			if (theTile.sideCache[1] != 0) {
				setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 1.0F, 0.7F);
				super.setBlockBoundsBasedOnState(world, x, y, z);
				return;
			}
			if (theTile.sideCache[4] != 0 & theTile.sideCache[5] != 0) {
				setBlockBounds(0.0F, 0.3F, 0.3F, 1.0F, 0.7F, 0.7F);
				super.setBlockBoundsBasedOnState(world, x, y, z);
				return;
			}
			if (theTile.sideCache[2] != 0 & theTile.sideCache[3] != 0) {
				setBlockBounds(0.3F, 0.3F, 0.0F, 0.7F, 0.7F, 1.0F);
				super.setBlockBoundsBasedOnState(world, x, y, z);
				return;
			}
			if (theTile.sideCache[2] != 0) {
				setBlockBounds(0.3F, 0.3F, 0.0F, 0.7F, 0.7F, 0.7F);
				super.setBlockBoundsBasedOnState(world, x, y, z);
				return;
			}
			if (theTile.sideCache[3] != 0) {
				setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 1.0F);
				super.setBlockBoundsBasedOnState(world, x, y, z);
				return;
			}
			if (theTile.sideCache[4] != 0) {
				setBlockBounds(0.0F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
				super.setBlockBoundsBasedOnState(world, x, y, z);
				return;
			}
			if (theTile.sideCache[5] != 0) {
				setBlockBounds(0.3F, 0.3F, 0.3F, 1.0F, 0.7F, 0.7F);
				super.setBlockBoundsBasedOnState(world, x, y, z);
				return;
			}
		}
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		super.setBlockBoundsBasedOnState(world, x, y, z);
	}

}
