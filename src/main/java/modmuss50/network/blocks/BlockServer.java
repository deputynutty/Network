package modmuss50.network.blocks;

import java.util.ArrayList;
import java.util.Random;

import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityServer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockServer extends BlockBase {

	private final Random	random	= new Random();

	public BlockServer() {
		super(Material.anvil);
	}

	@SideOnly(Side.CLIENT)
	private IIcon	side;
	@SideOnly(Side.CLIENT)
	private IIcon	other;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		side = icon.registerIcon("network:server_side");
		other = icon.registerIcon("network:server_other");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int currentSide, int meta) {
		if (currentSide == 0 || currentSide == 1) {
			return other;
		}
		else {
			return side;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityServer();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {

		TileEntityServer tileentity = (TileEntityServer) par1World.getTileEntity(par2, par3, par4);

		par1World.markTileEntityChunkModified(par2, par3, par4, tileentity);

		if (tileentity != null) {
			par5EntityPlayer.openGui(NetworkCore.instance, 6, par1World, par2, par3, par4);
		}

		return true;
	}

	// @Override
	// public void breakBlock(World world, int x, int y, int z, Block par5, int
	// par6)
	// {
	// ForgeChunkManager.releaseTicket(((TileEntityServer)
	// world.getTileEntity(x, y, z)).heldChunk);
	// super.breakBlock(world, x, y, z, par5, par6);
	//
	// }

	/**
	 * ejects contained items into the world, and notifies neighbours of an
	 * update, as appropriate
	 */
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileEntityServer tileentitychest = (TileEntityServer) par1World.getTileEntity(par2, par3, par4);

		if (tileentitychest != null) {
			for (int j1 = 0; j1 < tileentitychest.getSizeInventory(); ++j1) {
				ItemStack itemstack = tileentitychest.getStackInSlot(j1);

				if (itemstack != null) {
					float f = this.random.nextFloat() * 0.8F + 0.1F;
					float f1 = this.random.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem)) {
						int k1 = this.random.nextInt(21) + 10;

						if (k1 > itemstack.stackSize) {
							k1 = itemstack.stackSize;
						}

						itemstack.stackSize -= k1;
						entityitem = new EntityItem(par1World, (double) ((float) par2 + f), (double) ((float) par3 + f1), (double) ((float) par4 + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
					}
				}
			}

		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	/**
	 * Gets the inventory of the chest at the specified coords, accounting for
	 * blocks or ocelots on top of the chest, and double chests.
	 */
	public IInventory getInventory(World par1World, int par2, int par3, int par4) {
		Object object = (TileEntityServer) par1World.getTileEntity(par2, par3, par4);

		return (IInventory) object;

	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();

		TileEntity t = world.getTileEntity(x, y, z);

		if (t instanceof TileEntityServer) {
			TileEntityServer tile = (TileEntityServer) t;
			int Netuu = tile.getNetworkNetUnits();

			ItemStack stack = new ItemStack(world.getBlock(x, y, z), 1, metadata);
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			stack.getTagCompound().setInteger("netuu", Netuu);
			items.add(stack);
		}

		return items;

	}

}
