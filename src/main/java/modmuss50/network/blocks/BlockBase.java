package modmuss50.network.blocks;

import modmuss50.network.blocks.tileentities.BaseTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockBase extends BlockContainer {

	protected BlockBase(Material met) {
		super(met);
	}

	public static boolean canBlockBeused(World world, int x, int y, int z, EntityPlayer player) {
		if (isBlockLocked(world, x, y, z, player)) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity != null && tileEntity instanceof BaseTile) {
				BaseTile baseTile = (BaseTile) tileEntity;
				if (!baseTile.getOwner().equals("[Network]")) {
					if (baseTile.getOwner().equals(player.getDisplayName())) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean isBlockLocked(World world, int x, int y, int z, EntityPlayer player) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof BaseTile) {
			BaseTile baseTile = (BaseTile) tileEntity;
			return baseTile.islocked;

		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof BaseTile) {
			((BaseTile) tile).onBlockPlacedBy(entity, stack);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
		super.breakBlock(world, x, y, z, block, par6);
	}

}
