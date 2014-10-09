package modmuss50.network.dataSystems.itemSystem.tileEntitys;

import modmuss50.network.api.WorldCoordinate;
import modmuss50.network.api.data.DataType;
import modmuss50.network.api.data.IDataPer;
import modmuss50.network.blocks.tileentities.BaseTile;
import modmuss50.network.compact.FMP.Multipart;
import modmuss50.network.compact.FMP.PartWireNFC;
import modmuss50.network.dataSystems.itemSystem.ItemSystem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourceteam.mods.lib.Location;
import sourceteam.mods.lib.invUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Mark on 11/08/2014.
 */

public class TileEnityImport extends BaseTile implements IInventory, IDataPer {

	public ArrayList<Location> scanned = new ArrayList<Location>();
	private ItemStack[] Contents = new ItemStack[1];

	@Override
	public int getSizeInventory() {
		return Contents.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return var1 >= this.Contents.length ? null : this.Contents[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		if (this.Contents[var1] != null) {
			ItemStack itemstack;

			if (this.Contents[var1].stackSize <= var2) {
				itemstack = this.Contents[var1];
				this.Contents[var1] = null;
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.Contents[var1].splitStack(var2);

				if (this.Contents[var1].stackSize == 0) {
					this.Contents[var1] = null;
				}

				this.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		if (this.Contents[var1] != null) {
			ItemStack itemstack = this.Contents[var1];
			this.Contents[var1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		if (var1 < this.Contents.length) {
			this.Contents[var1] = var2;

			if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
				var2.stackSize = this.getInventoryStackLimit();
			}

			this.markDirty();
		}
	}

	@Override
	public String getInventoryName() {
		return "Input";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (getStackInSlot(0) != null) {
			TileEntityBlockStorageContainer chest = getChest(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			if (chest != null) {
				int amount = invUtil.addToInventory(chest.getWorldObj(), chest.xCoord, chest.yCoord, chest.zCoord, this.getStackInSlot(0));
				if (amount != 0)
					this.decrStackSize(0, amount);
			}
		}
	}

	public TileEntityBlockStorageContainer getChest(World world, int xs, int ys, int zs) {
		scanned.clear();

		List<WorldCoordinate> visited = new ArrayList<WorldCoordinate>();
		int cableMaxLenghth = 128;
		Queue<WorldCoordinate> queue = new PriorityQueue<WorldCoordinate>();
		WorldCoordinate start = new WorldCoordinate(xs, ys, zs, 0);
		queue.add(start);
		visited.add(start);

		while (!queue.isEmpty()) {
			WorldCoordinate element = queue.poll();

			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						if (Math.abs(x) + Math.abs(y) + Math.abs(z) == 1) {
							WorldCoordinate target = new WorldCoordinate(element.getX() + x, element.getY() + y, element.getZ() + z, element.getDepth() + 1);
							if (!visited.contains(target)) {
								visited.add(target);
								if (element.getDepth() < cableMaxLenghth) {
									Block block = worldObj.getBlock(target.getX(), target.getY(), target.getZ());
									TileEntity tile = worldObj.getTileEntity(target.getX(), target.getY(), target.getZ());
									int meta = worldObj.getBlockMetadata(target.getX(), target.getY(), target.getZ());
									if (block == ItemSystem.storageChest) {
										TileEntity tileEntity = worldObj.getTileEntity(target.getX(), target.getY(), target.getZ());
										if (tileEntity != null && tileEntity instanceof TileEntityBlockStorageContainer) {
											TileEntityBlockStorageContainer server = (TileEntityBlockStorageContainer) tileEntity;
											return server;
										}
									} else if (isCable(tile) && target.getDepth() < cableMaxLenghth) {
										queue.add(target);
									}
									if (Multipart.hasPartWireNFC(tile)) {
										PartWireNFC wire = Multipart.getWireNFC(tile);
										for (int i = 0; i < wire.conecatable.size(); i++) {
											Location loc = wire.conecatable.get(i);
											queue.add(new WorldCoordinate(loc.getX(), loc.getY(), loc.getZ()));
										}
									}
								}
							}
						}
					}
				}
			}

		}
		return null;
	}

	public boolean hascanned(Location loc) {
		for (int i = 0; i < scanned.size(); i++) {
			if (loc.getX() == scanned.get(i).getX() && loc.getY() == scanned.get(i).getY() && loc.getZ() == scanned.get(i).getZ()) {
				return true;
			}
		}
		return false;
	}


	public boolean isCable(TileEntity tile) {
		if (Multipart.hasPartWireNFC(tile) || Multipart.hasPartWire(tile))
			return true;
		return tile instanceof TileEntityBlockStorageContainer;
	}

	@Override
	public DataType type() {
		return DataType.processor;
	}
}

