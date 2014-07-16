package modmuss50.network.blocks.tileentities;

import modmuss50.network.api.IPowedTileEntity;
import modmuss50.network.netty.ChannelHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import cofh.api.energy.IEnergyStorage;

public class TileEntityPowerUserBase extends IPowedTileEntity implements IInventory, IEnergyStorage {

	public int	PowerStorageSize	= 10000;
	public int	currentPower		= 0;
	public int	powerimputspeed		= 5;

	@Override
	public void doWorkTick() {
		if (currentPower < PowerStorageSize) {
			if (removePowerServer(powerimputspeed)) {
				currentPower += powerimputspeed;
			}
		}

		super.doWorkTick();
	}

	public void updateEntity() {
		if (energy != 0 && energy > 10) {
			currentPower += 10;
			extractEnergy(10, true);
		}

		super.updateEntity();
	}

	public TileEntityPowerUserBase() {

		this.capacity = 100000;
		this.maxReceive = 1000;
		this.maxExtract = 0;
	}

	public int getCurrentPower() {
		return currentPower;
	}

	public int getPowerStorageSize() {
		return PowerStorageSize;
	}

	public boolean removePowerFromBase(int i) {
		if (i >= this.currentPower) {
			this.currentPower -= i;
			return true;
		}
		else {
			return false;
		}
	}

	// ####INV STUFFS

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {

	}

	@Override
	public String getInventoryName() {
		return "PowerUserBase";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return false;
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
		ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), worldObj);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		currentPower = tag.getInteger("currentPower");
		readESFromNBT(tag);
		syncTile();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("currentPower", currentPower);
		writeESToNBT(tag);
	}

	// Redstone Flux Stuff

	protected int	energy;
	protected int	capacity;
	protected int	maxReceive;
	protected int	maxExtract;

	public void setCapacity(int capacity) {

		this.capacity = capacity;

		if (energy > capacity) {
			energy = capacity;
		}
	}

	public void setMaxTransfer(int maxTransfer) {

		setMaxReceive(maxTransfer);
		setMaxExtract(maxTransfer);
	}

	public void setMaxReceive(int maxReceive) {

		this.maxReceive = maxReceive;
	}

	public void setMaxExtract(int maxExtract) {

		this.maxExtract = maxExtract;
	}

	public int getMaxReceive() {

		return maxReceive;
	}

	public int getMaxExtract() {

		return maxExtract;
	}

	/**
	 * This function is included to allow for server -> client sync. Do not call
	 * this externally to the containing Tile Entity, as not all IEnergyHandlers
	 * are guaranteed to have it.
	 * 
	 * @param energy
	 */
	public void setEnergyStored(int energy) {

		this.energy = energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		}
		else
			if (this.energy < 0) {
				this.energy = 0;
			}
	}

	/**
	 * This function is included to allow the containing tile to directly and
	 * efficiently modify the energy contained in the EnergyStorage. Do not rely
	 * on this externally, as not all IEnergyHandlers are guaranteed to have it.
	 * 
	 * @param energy
	 */
	public void modifyEnergyStored(int energy) {

		this.energy += energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		}
		else
			if (this.energy < 0) {
				this.energy = 0;
			}
	}

	/* IEnergyStorage */
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {

		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {

		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored() {

		return energy;
	}

	@Override
	public int getMaxEnergyStored() {

		return capacity;
	}

	public NBTTagCompound readESFromNBT(NBTTagCompound nbt) {

		this.energy = nbt.getInteger("Energy");

		if (energy > capacity) {
			energy = capacity;
		}
		return nbt;
	}

	public NBTTagCompound writeESToNBT(NBTTagCompound nbt) {

		if (energy < 0) {
			energy = 0;
		}
		nbt.setInteger("Energy", energy);
		return nbt;
	}

}
