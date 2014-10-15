package modmuss50.network.blocks.tileentities;


import modmuss50.network.api.IRemoteTile;
import modmuss50.network.api.power.EnergySystem;
import modmuss50.network.api.power.IEnergyFace;
import modmuss50.network.client.gui.GuiHandler;
import modmuss50.network.netty.ChannelHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import modmuss50.mods.lib.Location;

public class TileEntityFluidGen extends BaseTile implements IFluidHandler, IInventory, IRemoteTile, IEnergyFace {

	public FluidTank tank = new FluidTankRestricted(8000, new String[]{"lava"}) {
		public FluidTank readFromNBT(NBTTagCompound nbt) {
			if (!nbt.hasKey("Empty")) {
				FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
				setFluid(fluid);
			} else {
				setFluid(null);
			}
			return this;
		}
	};
	EnergySystem energySystem = new EnergySystem(10000, 0, true, false);
	private FluidStack lastBeforeUpdate = null;

	@Override
	public void updateEntity() {

		if (tank.getFluid() != null) {
			if (energySystem.tryInsertEnergy(100)) {
				tank.drain(1, true);
			}

		}
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

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		readFromNBTWithoutCoords(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		writeToNBTWithoutCoords(tag);
	}

	public void writeToNBTWithoutCoords(NBTTagCompound tag) {
		tank.writeToNBT(tag);
	}

	public void readFromNBTWithoutCoords(NBTTagCompound tag) {
		tank.readFromNBT(tag);
	}

	/* IFluidHandler */
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource == null || (tank.getFluid() != null && resource.fluidID != tank.getFluid().fluidID))
			return 0;
		return fill(resource, doFill, true);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (tank.getFluid() == null || resource == null || resource.fluidID != tank.getFluid().fluidID)
			return null;

		return drain(resource, doDrain, true);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (tank.getFluid() == null)
			return null;

		return drain(from, new FluidStack(tank.getFluid(), maxDrain), doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (fluid != null) {
			if (FluidRegistry.getFluidName(fluid.getID()).contentEquals("lava")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return tank.getFluid() == null || tank.getFluid().getFluid() == fluid;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return getTankInfo(true);
	}

	public FluidStack drain(FluidStack fluid, boolean doDrain, boolean findMainTank) {

		FluidStack drained = tank.drain(fluid.amount, doDrain);
		compareAndUpdate();

		if (drained == null || drained.amount < fluid.amount) {
			TileEntity offTE = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if (offTE instanceof TileEntityFluidGen) {
				TileEntityFluidGen tank = (TileEntityFluidGen) offTE;
				FluidStack externallyDrained = tank.drain(new FluidStack(fluid.fluidID, fluid.amount - (drained != null ? drained.amount : 0)), doDrain, false);

				if (externallyDrained != null)
					return new FluidStack(fluid.fluidID, (drained != null ? drained.amount : 0) + externallyDrained.amount);
				else
					return drained;
			}
		}

		return drained;
	}

	public int fill(FluidStack fluid, boolean doFill, boolean findMainTank) {
		int filled = tank.fill(fluid, doFill);
		compareAndUpdate();

		if (filled < fluid.amount) {
			return 0;
		}

		return filled;
	}

	public FluidTankInfo[] getTankInfo(boolean goToMainTank) {
		if (!goToMainTank)
			return new FluidTankInfo[]{tank.getInfo()};

		int amount = 0, capacity = 0;
		Fluid fluid = null;

		int yOff = 0;
		TileEntity offTE = worldObj.getTileEntity(xCoord, yCoord - yOff, zCoord);
		TileEntityFluidGen mainTank = this;
		while (true) {
			if (offTE != null && offTE instanceof TileEntityFluidGen) {
				if ((((TileEntityFluidGen) offTE).getFluid() == null || ((TileEntityFluidGen) offTE).getFluid() == getFluid())) {
					mainTank = (TileEntityFluidGen) worldObj.getTileEntity(xCoord, yCoord - yOff, zCoord);
					yOff++;
					offTE = worldObj.getTileEntity(xCoord, yCoord - yOff, zCoord);
					continue;
				}
			}
			break;
		}

		yOff = 0;
		offTE = worldObj.getTileEntity(xCoord, yCoord + yOff, zCoord);
		while (true) {
			if (offTE != null && offTE instanceof TileEntityFluidGen) {
				mainTank = (TileEntityFluidGen) offTE;
				if ((mainTank.getFluid() == null || mainTank.getFluid() == getFluid())) {
					FluidTankInfo info = mainTank.getTankInfo(false)[0];
					if (info != null) {
						capacity += info.capacity;
						if (info.fluid != null) {
							amount += info.fluid.amount;
							if (info.fluid.getFluid() != null)
								fluid = info.fluid.getFluid();
						}
					}
					yOff++;
					offTE = worldObj.getTileEntity(xCoord, yCoord + yOff, zCoord);
					continue;
				}
			}
			break;
		}

		return new FluidTankInfo[]{new FluidTankInfo(fluid != null ? new FluidStack(fluid, amount) : null, capacity)};
	}

	public Fluid getFluid() {
		FluidStack tankFluid = tank.getFluid();
		return tankFluid != null && tankFluid.amount > 0 ? tankFluid.getFluid() : null;
	}

	public void compareAndUpdate() {
		if (!worldObj.isRemote) {
			FluidStack current = tank.getFluid();

			if (current != null) {
				if (lastBeforeUpdate != null) {
					if (Math.abs(current.amount - lastBeforeUpdate.amount) >= 500) {
						ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), worldObj);
						lastBeforeUpdate = current.copy();
					} else if (lastBeforeUpdate.amount < tank.getCapacity() && current.amount == tank.getCapacity() || lastBeforeUpdate.amount == tank.getCapacity() && current.amount < tank.getCapacity()) {
						ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), worldObj);
						lastBeforeUpdate = current.copy();
					}
				} else {
					ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), worldObj);
					lastBeforeUpdate = current.copy();
				}
			} else if (lastBeforeUpdate != null) {
				ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), worldObj);
				lastBeforeUpdate = null;
			}
		}
	}

	public Fluid getRenderFluid() {
		return tank.getFluid() != null ? tank.getFluid().getFluid() : null;
	}

	public float getRenderScale() {
		return (float) tank.getFluidAmount() / tank.getCapacity();
	}

	// INV STUFF

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
		return "Tank";
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

	@Override
	public int guiID() {
		return GuiHandler.FluidGenID;
	}

	@Override
	public EnergySystem ENERGY_SYSTEM() {
		return energySystem;
	}

	@Override
	public Location getLocation() {
		return new Location(this.xCoord, this.yCoord, this.zCoord);
	}
}
