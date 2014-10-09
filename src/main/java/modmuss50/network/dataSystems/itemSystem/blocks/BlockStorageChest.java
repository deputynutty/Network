package modmuss50.network.dataSystems.itemSystem.blocks;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.NetworkCore;
import modmuss50.network.dataSystems.itemSystem.containers.ContainerStorageChest;
import modmuss50.network.dataSystems.itemSystem.tileEntitys.TileEntityBlockStorageContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mark on 11/08/2014.
 */
public class BlockStorageChest extends BlockContainer {

	private Random random;

	@SideOnly(Side.CLIENT)
	private IIcon side;
	@SideOnly(Side.CLIENT)
	private IIcon other;

	public BlockStorageChest() {
		super(Material.iron);
		random = new Random();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		side = icon.registerIcon("network:storagechest_side");
		other = icon.registerIcon("network:storagechest_other");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int currentSide, int meta) {
		if (currentSide == 0 || currentSide == 1) {
			return other;
		} else {
			return side;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBlockStorageContainer();
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> items = Lists.newArrayList();
		ItemStack stack = new ItemStack(this, 1, metadata);
		items.add(stack);
		return items;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3) {
		TileEntity te = world.getTileEntity(i, j, k);

		if (te == null || !(te instanceof TileEntityBlockStorageContainer)) {
			return true;
		}

		if (world.isSideSolid(i, j + 1, k, ForgeDirection.DOWN)) {
			return true;
		}

		if (world.isRemote) {
			return true;
		}

		player.openGui(NetworkCore.instance, 14, world, i, j, k);
		return true;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		world.markBlockForUpdate(i, j, k);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack itemStack) {
		byte chestFacing = 0;
		int facing = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if (facing == 0) {
			chestFacing = 2;
		}
		if (facing == 1) {
			chestFacing = 5;
		}
		if (facing == 2) {
			chestFacing = 3;
		}
		if (facing == 3) {
			chestFacing = 4;
		}
		TileEntity te = world.getTileEntity(i, j, k);
		if (te != null && te instanceof TileEntityBlockStorageContainer) {
			TileEntityBlockStorageContainer teic = (TileEntityBlockStorageContainer) te;
			teic.wasPlaced(entityliving, itemStack);
			teic.setFacing(chestFacing);
			world.markBlockForUpdate(i, j, k);
		}
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block i1, int i2) {
		TileEntityBlockStorageContainer tileentitychest = (TileEntityBlockStorageContainer) world.getTileEntity(i, j, k);
		if (tileentitychest != null) {
			tileentitychest.removeAdornments();
			dropContent(0, tileentitychest, world, tileentitychest.xCoord, tileentitychest.yCoord, tileentitychest.zCoord);
		}
		super.breakBlock(world, i, j, k, i1, i2);
	}

	public void dropContent(int newSize, IInventory chest, World world, int xCoord, int yCoord, int zCoord) {
		for (int l = newSize; l < chest.getSizeInventory(); l++) {
			ItemStack itemstack = chest.getStackInSlot(l);
			if (itemstack == null) {
				continue;
			}
			float f = random.nextFloat() * 0.8F + 0.1F;
			float f1 = random.nextFloat() * 0.8F + 0.1F;
			float f2 = random.nextFloat() * 0.8F + 0.1F;
			while (itemstack.stackSize > 0) {
				int i1 = random.nextInt(21) + 10;
				if (i1 > itemstack.stackSize) {
					i1 = itemstack.stackSize;
				}
				itemstack.stackSize -= i1;
				EntityItem entityitem = new EntityItem(world, (float) xCoord + f, (float) yCoord + (newSize > 0 ? 1 : 0) + f1, (float) zCoord + f2,
						new ItemStack(itemstack.getItem(), i1, itemstack.getItemDamage()));
				float f3 = 0.05F;
				entityitem.motionX = (float) random.nextGaussian() * f3;
				entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float) random.nextGaussian() * f3;
				if (itemstack.hasTagCompound()) {
					entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
				}
				world.spawnEntityInWorld(entityitem);
			}
		}
	}


	@Override
	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
		TileEntity te = par1World.getTileEntity(par2, par3, par4);
		if (te instanceof IInventory) {
			return ContainerStorageChest.calcRedstoneFromInventory((IInventory) te);
		}
		return 0;
	}


}
