package modmuss50.network.blocks;

import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityFluidGen;
import modmuss50.network.netty.ChannelHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockFluidGen extends BlockBase {
	IIcon	breakIcon;
	IIcon	topIcon;
	IIcon	bottomIcon;
	IIcon	sideIcon;

	public BlockFluidGen() {
		super(Material.glass);
		setCreativeTab(NetworkCore.Network);
		setHardness(2.0F);
		setResistance(10.0F);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return getDropWithNBT(world, x, y, z);
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
	public int getRenderType() {
		return 0;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public IIcon getIcon(int side, int b) {
		return side == 0 ? bottomIcon : side == 1 ? topIcon : sideIcon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		breakIcon = iconregister.registerIcon("network:fluidgen/fluid");
		topIcon = iconregister.registerIcon("network:fluidgen/Top");
		bottomIcon = iconregister.registerIcon("network:fluidgen/Bottom");
		sideIcon = iconregister.registerIcon("network:fluidgen/Side");
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean onBlockActivated(World worldObj, int x, int y, int z, EntityPlayer entityplayer, int blockID, float offsetX, float offsetY, float offsetZ) {
		ItemStack current = entityplayer.inventory.getCurrentItem();

		if (current != null) {
			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);
			TileEntityFluidGen tank = (TileEntityFluidGen) worldObj.getTileEntity(x, y, z);

			if (liquid != null) {
				int amountFilled = tank.fill(ForgeDirection.UNKNOWN, liquid, true);

				if (amountFilled != 0 && !entityplayer.capabilities.isCreativeMode) {
					if (current.stackSize > 1) {
						entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem].stackSize -= 1;
						entityplayer.inventory.addItemStackToInventory(current.getItem().getContainerItem(current));
					}
					else {
						entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = current.getItem().getContainerItem(current);
					}
				}

				return true;

				// Handle empty containers
			}
			else {

				FluidStack available = tank.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
				if (available != null) {
					ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, current);

					liquid = FluidContainerRegistry.getFluidForFilledItem(filled);

					if (liquid != null) {
						if (!entityplayer.capabilities.isCreativeMode) {
							if (current.stackSize > 1) {
								if (!entityplayer.inventory.addItemStackToInventory(filled)) {
									tank.fill(ForgeDirection.UNKNOWN, new FluidStack(liquid, liquid.amount), true);
									return false;
								}
								else {
									entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem].stackSize -= 1;
								}
							}
							else {
								entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = filled;
							}
						}
						tank.drain(ForgeDirection.UNKNOWN, liquid.amount, true);
						return true;
					}
				}
			}
		}

		TileEntityFluidGen te;
		te = (TileEntityFluidGen) worldObj.getTileEntity(x, y, z);
		if (te != null) {
			entityplayer.openGui(NetworkCore.instance, 5, worldObj, x, y, z);
			return true;
		}

		return false;
	}

	public ItemStack getDropWithNBT(World world, int x, int y, int z) {
		NBTTagCompound tileEntity = new NBTTagCompound();
		TileEntity worldTE = world.getTileEntity(x, y, z);
		if (worldTE != null && worldTE instanceof TileEntityFluidGen) {
			ItemStack dropStack = new ItemStack(NetworkBlocks.FluidGen, 1);

			((TileEntityFluidGen) worldTE).writeToNBTWithoutCoords(tileEntity);

			dropStack.setTagCompound(new NBTTagCompound());
			dropStack.stackTagCompound.setTag("tileEntity", tileEntity);
			return dropStack;

		}
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFluidGen();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		if (!world.isRemote) {
			ChannelHandler.sendPacketToAllPlayers(world.getTileEntity(x, y, z).getDescriptionPacket(), world);
		}
	}
}
