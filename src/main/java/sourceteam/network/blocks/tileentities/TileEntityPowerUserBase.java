package sourceteam.network.blocks.tileentities;

import sourceteam.network.api.IPowedTileEntity;
import sourceteam.network.netty.ChannelHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityPowerUserBase extends IPowedTileEntity implements IInventory {

    public int PowerStorageSize = 10000;
    public int currentPower = 0;
    public int powerimputspeed = 5;

    public TileEntityPowerUserBase() {
    }

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

        super.updateEntity();
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
        } else {
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
        syncTile();
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("currentPower", currentPower);
    }

}
