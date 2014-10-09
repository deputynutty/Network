package modmuss50.network.blocks.tileentities;

import modmuss50.network.api.IRemoteTile;
import modmuss50.network.items.ItemPart;
import modmuss50.network.items.NetworkItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourceteam.mods.lib.Location;

import java.util.ArrayList;

public class TileEntityMover extends BaseTile implements IInventory, IRemoteTile {

	public final int invSize = 7;
	public ItemStack[] Contents = new ItemStack[invSize];
	public ArrayList<MovingBlock> movingBlocks = new ArrayList<MovingBlock>();
	public int powerMulti = 10;

	public TileEntityMover() {

	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		// moveBlock(this.xCoord, this.yCoord + 1, this.zCoord,
		// this.getWorldObj(), getMoveDirection());

	}

	public void addBlockToMove(Location orign, ForgeDirection dir) {
		if (this.worldObj.getTileEntity(orign.getX(), orign.getY(), orign.getZ()) == null) {
			addMovingBlock(this.worldObj.getBlock(orign.getX(), orign.getY(), orign.getZ()), this.worldObj.getBlockMetadata(orign.getX(), orign.getY(), orign.getZ()), orign, dirToLocation(dir, orign), dir);
		} else {
			NBTTagCompound nbt = new NBTTagCompound();
			this.worldObj.getTileEntity(orign.getX(), orign.getY(), orign.getZ()).writeToNBT(nbt);
			addMovingBlock(this.worldObj.getBlock(orign.getX(), orign.getY(), orign.getZ()), this.worldObj.getBlockMetadata(orign.getX(), orign.getY(), orign.getZ()), nbt, orign, dirToLocation(dir, orign), dir);
		}
	}

	public void active() {
		if (getMoveDirFromItems() != ForgeDirection.UNKNOWN) {
			movingBlocks.clear();
			int up = getScaleUp();
			int down = getScaleDown();
			int north = getScaleNorth();
			int east = getScaleEast();
			int south = getScaleSouth();
			int west = getScaleWest();

			for (int x = -west; x < east; x++) {
				for (int y = -down; y < up; y++) {
					for (int z = -north; z < south; z++) {
						this.addBlockToMove(new Location(this.xCoord + x, this.yCoord + y, this.zCoord + z), getMoveDirFromItems());
					}
				}
			}
			if (canmove()) {
				//TODO add power requiments back in
				removeblocks();
				addblocks();
			}
		}
	}

	public void removeblocks() {
		for (int i = 0; i < movingBlocks.size(); i++) {
			worldObj.removeTileEntity(movingBlocks.get(i).origin.getX(), movingBlocks.get(i).origin.getY(), movingBlocks.get(i).origin.getZ());
			worldObj.setBlock(movingBlocks.get(i).origin.getX(), movingBlocks.get(i).origin.getY(), movingBlocks.get(i).origin.getZ(), Blocks.air, 0, 3);

		}
	}

	public void addblocks() {
		for (int i = 0; i < movingBlocks.size(); i++) {
			worldObj.setBlock(movingBlocks.get(i).destiantion.getX(), movingBlocks.get(i).destiantion.getY(), movingBlocks.get(i).destiantion.getZ(), movingBlocks.get(i).getBlock(), movingBlocks.get(i).meta, 2);
			if (movingBlocks.get(i).getNbt() != null) {
				worldObj.setTileEntity(movingBlocks.get(i).destiantion.getX(), movingBlocks.get(i).destiantion.getY(), movingBlocks.get(i).destiantion.getZ(), TileEntity.createAndLoadEntity(movingBlocks.get(i).getNbt()));
				NBTTagCompound nbt = new NBTTagCompound();
				this.writeToNBT(nbt);
				new S35PacketUpdateTileEntity(movingBlocks.get(i).destiantion.getX(), movingBlocks.get(i).destiantion.getY(), movingBlocks.get(i).destiantion.getZ(), 1, nbt);
			}
		}
	}

	public boolean canmove() {

		for (int i = 0; i < movingBlocks.size(); i++) {
			if (canBlockMove(movingBlocks.get(i).origin, movingBlocks.get(i).getDir()) == false) {
				System.out.println("Found block that carnt move! " + movingBlocks.get(i).getBlock().getUnlocalizedName() + canBlockMove(movingBlocks.get(i).origin, movingBlocks.get(i).getDir()));
				return false;
			}
		}

		return true;
	}

	public boolean canBlockMove(Location block, ForgeDirection dir) {
		Location nextblock = dirToLocation(dir, block);

		if (nextblock != null && worldObj != null && worldObj.getBlock(nextblock.getX(), nextblock.getY(), nextblock.getZ()) == Blocks.air) {
			return true;
		}

		return true;

		// for (int i = 0; i < movingBlocks.size(); i++) {
		// if (movingBlocks.get(i).getOrigin().getX() == nextblock.getX() &&
		// movingBlocks.get(i).getOrigin().getY() == nextblock.getY() &&
		// movingBlocks.get(i).getOrigin().getZ() == nextblock.getZ()) {
		// System.out.println("this block will also move");
		// return true;
		// } else {
		// return false;
		// }
		// }
		// return false;
	}

	public void addMovingBlock(Block block, int meta, NBTTagCompound nbt, Location origin, Location destiantion, ForgeDirection direction) {
		movingBlocks.add(new MovingBlock(block, meta, nbt, origin, destiantion, direction));
	}

	public void addMovingBlock(Block block, int meta, Location origin, Location destiantion, ForgeDirection direction) {
		movingBlocks.add(new MovingBlock(block, meta, null, origin, destiantion, direction));
	}

	public void moveBlock(int x, int y, int z, World world, ForgeDirection dir) {
		moveBlock(x, y, z, world, dir, 1);
	}

	public void moveBlock(int x, int y, int z, World world, ForgeDirection dir, int distance) {
		if (dir == ForgeDirection.UP) {
			moveBlock(new Location(x, y, z), new Location(x, y + distance, z), world);
			return;
		}

		if (dir == ForgeDirection.DOWN) {
			moveBlock(new Location(x, y, z), new Location(x, y - distance, z), world);
			return;
		}

		if (dir == ForgeDirection.NORTH) {
			moveBlock(new Location(x, y, z), new Location(x, y, z - distance), world);
			return;
		}

		if (dir == ForgeDirection.SOUTH) {
			moveBlock(new Location(x, y, z), new Location(x, y, z + distance), world);
			return;
		}

		if (dir == ForgeDirection.EAST) {
			moveBlock(new Location(x, y, z), new Location(x + distance, y, z), world);
			return;
		}

		if (dir == ForgeDirection.WEST) {
			moveBlock(new Location(x, y, z), new Location(x - distance, y, z), world);
			return;
		}

	}

	public void moveBlock(Location origin, Location target, World world) {
		int Sx = origin.getX();
		int Sy = origin.getY();
		int Sz = origin.getZ();

		int Ex = target.getX();
		int Ey = target.getY();
		int Ez = target.getZ();

		if (world.getBlock(Ex, Ey, Ez) == Blocks.air && world.getBlock(Sx, Sy, Sz) != Blocks.air) {
			// For blocks without a tile entity
			if (world.getTileEntity(Sx, Sy, Sz) == null) {
				Block block = world.getBlock(Sx, Sy, Sz);
				int meta = world.getBlockMetadata(Sx, Sy, Sz);
				world.setBlockToAir(Sx, Sy, Sz);
				world.setBlock(Ex, Ey, Ez, block, meta, 2);
			} else {
				// For blocks with a tile entity
				Block block = world.getBlock(Sx, Sy, Sz);
				int meta = world.getBlockMetadata(Sx, Sy, Sz);
				TileEntity tile = world.getTileEntity(Sx, Sy, Sz);
				NBTTagCompound savedNBT = new NBTTagCompound();
				tile.writeToNBT(savedNBT);
				world.removeTileEntity(Sx, Sy, Sz);
				world.setBlockToAir(Sx, Sy, Sz);
				world.setBlock(Ex, Ey, Ez, block, meta, 2);
				world.setTileEntity(Ex, Ey, Ez, TileEntity.createAndLoadEntity(savedNBT));

				NBTTagCompound nbt = new NBTTagCompound();
				this.writeToNBT(nbt);
				new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
			}

		}

	}

	public ForgeDirection getMoveDirection() {
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);

		int metaDataToSet = 0;
		switch (meta) {
			case 0:
				return ForgeDirection.NORTH;
			case 1:
				return ForgeDirection.EAST;
			case 2:
				return ForgeDirection.SOUTH;
			case 3:
				return ForgeDirection.WEST;
		}
		return ForgeDirection.UNKNOWN;
	}

	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		readFromNBT(packet.func_148857_g());
	}

	public void syncTile() {
		// ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(),
		// worldObj);
	}

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
		return "BlockMover";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}

	public void invalidate() {
		super.invalidate();
		this.updateContainingBlockInfo();
	}

	public Location dirToLocation(ForgeDirection dir, Location location) {
		if (dir == ForgeDirection.UP) {
			return new Location(location.getX(), location.getY() + 1, location.getZ());
		}

		if (dir == ForgeDirection.DOWN) {
			return new Location(location.getX(), location.getY() - 1, location.getZ());
		}

		if (dir == ForgeDirection.NORTH) {
			return new Location(location.getX(), location.getY(), location.getZ() - 1);
		}

		if (dir == ForgeDirection.SOUTH) {
			return new Location(location.getX(), location.getY(), location.getZ() + 1);
		}

		if (dir == ForgeDirection.EAST) {
			return new Location(location.getX() + 1, location.getY(), location.getZ());
		}

		if (dir == ForgeDirection.EAST) {
			return new Location(location.getX() - 1, location.getY(), location.getZ());
		}

		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTTagList nbttaglist = tag.getTagList("Items", 10);
		this.Contents = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < this.Contents.length) {
				this.Contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		syncTile();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.Contents.length; ++i) {
			if (this.Contents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.Contents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		tag.setTag("Items", nbttaglist);
	}

	public ForgeDirection getMoveDirFromItems() {

		if (getStackInSlot(0) != null) {
			if (getStackInSlot(0).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("North") == getStackInSlot(0).getItemDamage()) {
					return ForgeDirection.NORTH;
				}
				if (ItemPart.getMeta("South") == getStackInSlot(0).getItemDamage()) {
					return ForgeDirection.SOUTH;
				}
				if (ItemPart.getMeta("East") == getStackInSlot(0).getItemDamage()) {
					return ForgeDirection.EAST;
				}
				if (ItemPart.getMeta("West") == getStackInSlot(0).getItemDamage()) {
					return ForgeDirection.WEST;
				}
				if (ItemPart.getMeta("Up") == getStackInSlot(0).getItemDamage()) {
					return ForgeDirection.UP;
				}
				if (ItemPart.getMeta("Down") == getStackInSlot(0).getItemDamage()) {
					return ForgeDirection.DOWN;
				}
			}
		}
		return ForgeDirection.UNKNOWN;
	}

	public int getScaleUp() {
		int slotnumber = 1;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 0;
	}

	public int getScaleDown() {
		int slotnumber = 2;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 0;
	}

	public int getScaleNorth() {
		int slotnumber = 3;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 0;
	}

	public int getScaleSouth() {
		int slotnumber = 6;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 0;
	}

	public int getScaleEast() {
		int slotnumber = 5;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 0;
	}

	public int getScaleWest() {
		int slotnumber = 4;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 0;
	}

	@Override
	public int guiID() {
		return 0;
	}

	public int blocksToMove() {
		int b2m = getScaleDown1() * getScaleEast1() * getScaleNorth1() * getScaleSouth1() * getScaleUp1() * getScaleWest1();
		if (b2m == 1)
			b2m = 0;

		return b2m;
	}

	public int getScaleUp1() {
		int slotnumber = 1;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 1;
	}

	public int getScaleDown1() {
		int slotnumber = 2;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 1;
	}

	public int getScaleNorth1() {
		int slotnumber = 3;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 1;
	}

	public int getScaleSouth1() {
		int slotnumber = 6;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 1;
	}

	public int getScaleEast1() {
		int slotnumber = 5;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 1;
	}

	public int getScaleWest1() {
		int slotnumber = 4;
		if (getStackInSlot(slotnumber) != null) {
			if (getStackInSlot(slotnumber).getItem() == NetworkItems.parts) {
				if (ItemPart.getMeta("Scale") == getStackInSlot(slotnumber).getItemDamage()) {
					return getStackInSlot(slotnumber).stackSize;
				}

			}
		}
		return 1;
	}


}
