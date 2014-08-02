package modmuss50.network.blocks.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.api.IRemoteTile;
import modmuss50.network.api.InfusionFurnaceRecipes;
import modmuss50.network.client.gui.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.Constants;

/**
 * Created by Mark on 28/07/2014.
 */
public class TileEntityInfusionFurnace extends TileEntityPowerUserBase implements IInventory, IRemoteTile {
    public ItemStack[] items;
    public boolean isSmelting = false;
    public int timeSmelted = 0;
    public int maxSmeltTime = 140;

    public TileEntityInfusionFurnace() {
        super();
        items = new ItemStack[getSizeInventory()];
        PowerStorageSize = 100000;
    }

    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        if (var1 < 12) {
            return items[var1];
        } else {
            return items[1];
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        ItemStack stack = getStackInSlot(slot);

        if (stack != null) {
            if (stack.stackSize <= count) {
                setInventorySlotContents(slot, null);
            } else {
                ItemStack stack2 = stack.splitStack(count);
            }
        }

        return stack;
    }

    public ItemStack incrStackSize(int slot, int count) {
        ItemStack stack = getStackInSlot(slot).copy();

        if (stack != null) {
            if (stack.stackSize + count > 64) {
                stack.stackSize = 64;
            } else {
                stack.stackSize += count;
            }
        }
        setInventorySlotContents(slot, stack);
        return stack;
    }

    public void determineIfSmelting() {

        if (this.getCurrentPower() >= this.getNeededPower()) {
            if (!isSmelting && getStackInSlot(0) != null && InfusionFurnaceRecipes.smelting().getSmeltingResult(getStackInSlot(0)) != null) {
                ItemStack input = getStackInSlot(0);
                ItemStack output = getStackInSlot(2);

                if (!worldObj.isRemote) {
                    if (output != null) {
                        if (output.getItem() == InfusionFurnaceRecipes.smelting().getSmeltingResult(getStackInSlot(0)).getItem()) {
                            isSmelting = true;
                        } else {

                        }
                    } else {
                        isSmelting = true;
                    }
                }
            }
        } else {
            isSmelting = false;
        }
    }

    public int getNeededPower() {
        return 12;
    }

    public void smelt() {
        boolean removePower = false;

        if (isSmelting && !worldObj.isRemote && currentPower >= getNeededPower()) {
            timeSmelted += 1;

            ItemStack input = getStackInSlot(0);
            ItemStack output = getStackInSlot(2);

            currentPower -= getNeededPower();

            if (input == null) {
                isSmelting = false;
                timeSmelted = 0;
                return;
            }

            if (timeSmelted >= maxSmeltTime) {
                timeSmelted = 0;
                isSmelting = false;

                if (output != null) {
                    if ((getStackInSlot(0) != null) && output.getItem() == InfusionFurnaceRecipes.smelting().getSmeltingResult(getStackInSlot(0)).copy().getItem()) {
                        incrStackSize(2, 1);
                        decrStackSize(0, 1);
                        removePower = true;
                    } else {

                    }
                } else {
                    if (getStackInSlot(0) != null) {
                        setInventorySlotContents(2, InfusionFurnaceRecipes.smelting().getSmeltingResult(getStackInSlot(0)).copy());
                        decrStackSize(0, 1);
                        removePower = true;
                    }
                }
            }
            sync();
        }
    }

    public void updateEntity() {
        super.updateEntity();
        determineIfSmelting();
        smelt();
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return items[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack s) {
        items[slot] = s;
    }

    @Override
    public String getInventoryName() {
        return "Electric Furnace";
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
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getStackInSlot(i);

            if (stack != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                stack.writeToNBT(item);
                items.appendTag(item);
            }
        }

        tag.setTag("Items", items);
        tag.setBoolean("isSmelting", isSmelting);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        NBTTagList items = tag.getTagList("Items", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
            int slot = item.getByte("Slot");

            if (slot >= 0 && slot < getSizeInventory()) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
            }
        }

        isSmelting = tag.getBoolean("isSmelting");
    }

    /**
     * Returns an integer between 0 and the passed value representing how close
     * the current item is to being completely cooked
     */
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int p_145953_1_) {
        return this.timeSmelted * p_145953_1_ / 200;
    }

    @Override
    public int guiID() {
        return GuiHandler.InfusedFurnace;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    public void sync() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);

    }
}
