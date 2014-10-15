package modmuss50.network.dataSystems.itemSystem.tileEntitys;

import modmuss50.network.api.WorldCoordinate;
import modmuss50.network.api.data.DataType;
import modmuss50.network.api.data.IDataPer;
import modmuss50.network.blocks.tileentities.BaseTile;
import modmuss50.network.compact.FMP.Multipart;
import modmuss50.network.compact.FMP.PartWireNFC;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import modmuss50.mods.lib.Location;
import modmuss50.mods.lib.invUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by mark on 22/09/2014.
 */
public class TileEntityExport extends BaseTile implements IDataPer {

	public ArrayList<Location> tiles = new ArrayList<Location>();

	public int ticks = 0;

	public ItemStack stackToFind = null;

	public ArrayList<Location> scanned = new ArrayList<Location>();

	@Override
	public void updateEntity() {
		if (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			return;
		}
		if (stackToFind == null) {
			tiles.clear();
			return;
		}
		if (ticks == 60) {
			ticks = 0;
			findTiles(this.xCoord, this.yCoord, this.zCoord);
		} else {
			ticks += 1;
		}

		for (int i = 0; i < tiles.size(); i++) {
			TileEntity tile = worldObj.getTileEntity(tiles.get(i).getX(), tiles.get(i).getY(), tiles.get(i).getZ());
			if (tile != null && tile instanceof IDataPer && tile instanceof IInventory) {
				IDataPer iDataPer = (IDataPer) tile;
				if (iDataPer.type() == DataType.inv) {
					if (this.stackToFind != null) {
						for (int j = 0; j < ((IInventory) tile).getSizeInventory(); j++) {
							if (((IInventory) tile).getStackInSlot(j) != null && ((IInventory) tile).getStackInSlot(j).getItem().getUnlocalizedName().equals(stackToFind.getUnlocalizedName())) {
								if (worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) instanceof IInventory) {
									if (invUtil.addToInventory(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord, ((IInventory) tile).getStackInSlot(j)) >= 1) {
										((IInventory) tile).decrStackSize(j, ((IInventory) tile).getStackInSlot(j).stackSize);
									}
								} else {
									float f = worldObj.rand.nextFloat() * 0.8F + 0.1F;
									float f1 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
									float f2 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
									EntityItem entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1 + 0.5F, zCoord + f2, ((IInventory) tile).getStackInSlot(j));

									entityitem.lifespan = 1200;
									entityitem.delayBeforeCanPickup = 10;

									float f3 = 0.05F;
									entityitem.motionX = (float) worldObj.rand.nextGaussian() * f3;
									entityitem.motionY = (float) worldObj.rand.nextGaussian() * f3 + 1.0F;
									entityitem.motionZ = (float) worldObj.rand.nextGaussian() * f3;
									if (!worldObj.isRemote)
										worldObj.spawnEntityInWorld(entityitem);

									((IInventory) tile).decrStackSize(j, ((IInventory) tile).getStackInSlot(j).stackSize);
								}
							}
						}
					}
				}
			}
		}
	}

	public void findTiles(int xs, int ys, int zs) {
		scanned.clear();
		tiles.clear();

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
									if (tile != null && tile instanceof IDataPer) {
										tiles.add(new Location(tile.xCoord, tile.yCoord, tile.zCoord));
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
