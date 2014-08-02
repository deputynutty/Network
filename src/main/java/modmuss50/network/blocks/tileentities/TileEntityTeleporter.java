package modmuss50.network.blocks.tileentities;

import modmuss50.network.api.INetworkComponent;
import modmuss50.network.api.IPeripheral;
import modmuss50.network.netty.ChannelHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import sourceteam.mods.lib.client.IColour;
import sourceteam.mods.lib.client.IRGBColour;

/**
 * Created by Mark on 19/04/14.
 */
public class TileEntityTeleporter extends TileEntityCable implements IPeripheral, INetworkComponent, IColour, IRGBColour, IFluidHandler {
    public int fq = 0;
    public FluidTank tank = new FluidTank(1000) {
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
    int ticktime = 0;
    boolean goingdown = false;
    int colour = 0;
    private FluidStack lastBeforeUpdate = null;

    public TileEntityTeleporter() {

    }

    @Override
    public boolean canConnectViaWireless() {
        return false;
    }

    //red = 0
    //red + green = 1
    //green = 2
    //green + blue = 3
    //blue = 4
    //blue + red = 5


    @Override
    public void updateEntity() {
        super.updateEntity();
        updateBlock();

        if (ticktime >= 244) {
            goingdown = true;
        } else {
            if (ticktime == 0) {
                goingdown = false;
                if (colour == 5) {
                    colour = 0;
                } else {
                    colour += 1;
                }
            }
        }

        if (goingdown) {
            ticktime -= 1;
        } else {
            ticktime += 1;
        }


    }

    public void updateBlock() {

    }


    @Override
    public int colour() {
        return 0;
    }

    @Override
    public boolean isAnimated() {
        return true;
    }

    @Override
    public int Cred() {
        if (colour == 0 || colour == 1 || colour == 5) {
            return ticktime;
        }
        return 0;
    }

    @Override
    public int Cgreen() {
        if (colour == 1 || colour == 2 || colour == 3) {
            return ticktime;
        }
        return 0;
    }

    @Override
    public int Cblue() {
        if (colour == 3 || colour == 4 || colour == 5) {
            return ticktime;
        }
        return 0;
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
        //  NetworkCore.packetPipeline.sendToAll(new PacketSetTeleporterFQ(new Location(xCoord, yCoord, zCoord), fq));
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        fq = tag.getInteger("fq");
        // if(fq != 0)
        // syncTile();
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("fq", fq);
        System.out.println("TEP " + fq);
    }


    //FLUID STUFSS


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
            return true;
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
            if (offTE instanceof TileEntityTeleporter) {
                TileEntityTeleporter tank = (TileEntityTeleporter) offTE;
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
        TileEntityTeleporter mainTank = this;
        while (true) {
            if (offTE != null && offTE instanceof TileEntityTeleporter) {
                if ((((TileEntityTeleporter) offTE).getFluid() == null || ((TileEntityTeleporter) offTE).getFluid() == getFluid())) {
                    mainTank = (TileEntityTeleporter) worldObj.getTileEntity(xCoord, yCoord - yOff, zCoord);
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
            if (offTE != null && offTE instanceof TileEntityTeleporter) {
                mainTank = (TileEntityTeleporter) offTE;
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

}
