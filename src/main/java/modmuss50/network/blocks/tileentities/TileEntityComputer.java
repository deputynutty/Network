package modmuss50.network.blocks.tileentities;

import modmuss50.network.api.INetworkComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import modmuss50.network.api.IPowedTileEntity;

public class TileEntityComputer extends IPowedTileEntity implements IInventory, INetworkComponent {

    private int name;

    public TileEntityComputer() {
        // super(maxenergy, tier1, oldDischargeIndex);

    }

    public void updateEntity() {
        super.updateEntity();

    }

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
    public int getInventoryStackLimit() {

        return 0;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {

        return false;
    }

    /**
     * Read from NBT
     */
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        name = compound.getInteger("name");
    }

    /**
     * Write to NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("name", name);
    }

    @Override
    public String getInventoryName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void openInventory() {
        // TODO Auto-generated method stub

    }

    @Override
    public void closeInventory() {
        // TODO Auto-generated method stub

    }

}
