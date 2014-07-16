package modmuss50.network.blocks;

import java.util.List;

import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityMonitor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMonitor extends BlockBase {

	public BlockMonitor() {
		super(Material.circuits);
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int par2, int par3, int par4) {
		ForgeDirection dir = ForgeDirection.getOrientation(blockAccess.getBlockMetadata(par2, par3, par4));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.05F, 1.0F);
	}

	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
		setBlockBoundsBasedOnState(world, i, j, k);
		super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, par7Entity);
	}

	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.05F, 1.0F);
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		int var6 = ((MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
		System.out.println(var6);
	}

	protected boolean isRotatable() {
		return true;
	}

	protected boolean canRotateToTopOrBottom() {
		return true;
	}

	// public boolean rotateBlock(World world, EntityPlayer player, int x, int
	// y, int z, ForgeDirection face)
	// {
	// if (!player.isSneaking()) {
	// TileEntity tile = world.getTileEntity(x, y, z);
	// if ((tile instanceof TileEntityMonitor)) {
	// TileEntityMonitor teAt = (TileEntityMonitor)tile;
	// if (++teAt.textRotation > 3) teAt.textRotation = 0;
	// teAt.sendDescriptionPacket();
	// return true;
	// }
	// return false;
	// }
	//
	// return super.rotateBlock(world, player, x, y, z, face);
	// }

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		player.openGui(NetworkCore.instance, 3, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityMonitor();
	}

}
