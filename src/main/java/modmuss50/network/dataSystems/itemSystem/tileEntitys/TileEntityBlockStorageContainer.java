package modmuss50.network.dataSystems.itemSystem.tileEntitys;

import modmuss50.network.api.data.IDataPer;
import modmuss50.network.blocks.tileentities.BaseTile;
import modmuss50.network.dataSystems.itemSystem.ItemSystem;
import modmuss50.network.dataSystems.itemSystem.containers.ContainerStorageChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import modmuss50.mods.lib.api.IinvUpgrade;

import java.util.*;

/**
 * Created by Mark on 11/08/2014.
 */
public class TileEntityBlockStorageContainer extends BaseTile implements IDataPer, IInventory, IinvUpgrade {
    private int ticksSinceSync = -1;
    private int numUsingPlayers;
    public ItemStack[] chestContents;
    private ItemStack[] topStacks;
    private int facing;
    private boolean inventoryTouched;
    private boolean hadStuff;


    public TileEntityBlockStorageContainer() {
        super();
        this.chestContents = new ItemStack[getSizeInventory()];
        this.topStacks = new ItemStack[8];
    }

    public ItemStack[] getContents() {
        return chestContents;
    }

    @Override
    public int getSizeInventory() {
        return 108 + 500;
    }

    public int getFacing() {
        return this.facing;
    }

    @Override
    public String getInventoryName() {
        return "network.StorageConatainer";
    }


    @Override
    public ItemStack getStackInSlot(int i) {
        inventoryTouched = true;
        return chestContents[i];
    }

    @Override
    public void markDirty() {
        super.markDirty();
        sortTopStacks();
    }

    protected void sortTopStacks() {
        if ((worldObj != null && worldObj.isRemote)) {
            return;
        }
        ItemStack[] tempCopy = new ItemStack[getSizeInventory()];
        boolean hasStuff = false;
        int compressedIdx = 0;
        mainLoop:
        for (int i = 0; i < getSizeInventory(); i++) {
            if (chestContents[i] != null) {
                for (int j = 0; j < compressedIdx; j++) {
                    if (tempCopy[j].isItemEqual(chestContents[i])) {
                        tempCopy[j].stackSize += chestContents[i].stackSize;
                        continue mainLoop;
                    }
                }
                tempCopy[compressedIdx++] = chestContents[i].copy();
                hasStuff = true;
            }
        }
        if (!hasStuff && hadStuff) {
            hadStuff = false;
            for (int i = 0; i < topStacks.length; i++) {
                topStacks[i] = null;
            }
            if (worldObj != null) {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
            return;
        }
        hadStuff = true;
        Arrays.sort(tempCopy, new Comparator<ItemStack>() {
            @Override
            public int compare(ItemStack o1, ItemStack o2) {
                if (o1 == null) {
                    return 1;
                } else if (o2 == null) {
                    return -1;
                } else {
                    return o2.stackSize - o1.stackSize;
                }
            }
        });
        int p = 0;
        for (int i = 0; i < tempCopy.length; i++) {
            if (tempCopy[i] != null && tempCopy[i].stackSize > 0) {
                topStacks[p++] = tempCopy[i];
                if (p == topStacks.length) {
                    break;
                }
            }
        }
        for (int i = p; i < topStacks.length; i++) {
            topStacks[i] = null;
        }
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (chestContents[i] != null) {
            if (chestContents[i].stackSize <= j) {
                ItemStack itemstack = chestContents[i];
                chestContents[i] = null;
                markDirty();
                return itemstack;
            }
            ItemStack itemstack1 = chestContents[i].splitStack(j);
            if (chestContents[i].stackSize == 0) {
                chestContents[i] = null;
            }
            markDirty();
            return itemstack1;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        chestContents[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        chestContents = new ItemStack[getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if (j >= 0 && j < chestContents.length) {
                chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        facing = nbttagcompound.getByte("facing");
        sortTopStacks();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < chestContents.length; i++) {
            if (chestContents[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
        nbttagcompound.setByte("facing", (byte) facing);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        if (worldObj == null) {
            return true;
        }
        if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
            return false;
        }
        return entityplayer.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        // Resynchronize clients with the server state
        if (worldObj != null && !this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numUsingPlayers = 0;
            float var1 = 5.0F;
            @SuppressWarnings("unchecked")
            List<EntityPlayer> var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double) ((float) this.xCoord - var1), (double) ((float) this.yCoord - var1), (double) ((float) this.zCoord - var1), (double) ((float) (this.xCoord + 1) + var1), (double) ((float) (this.yCoord + 1) + var1), (double) ((float) (this.zCoord + 1) + var1)));
            Iterator<EntityPlayer> var3 = var2.iterator();

            while (var3.hasNext()) {
                EntityPlayer var4 = var3.next();

                if (var4.openContainer instanceof ContainerStorageChest) {
                    ++this.numUsingPlayers;
                }
            }
        }

        if (worldObj != null && !worldObj.isRemote && ticksSinceSync < 0) {
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, ItemSystem.storageChest, 3, ((numUsingPlayers << 3) & 0xF8) | (facing & 0x7));
        }
        if (!worldObj.isRemote && inventoryTouched) {
            inventoryTouched = false;
            sortTopStacks();
        }

        this.ticksSinceSync++;
    }

    @Override
    public boolean receiveClientEvent(int i, int j) {
        if (i == 1) {
            numUsingPlayers = j;
        } else if (i == 2) {
            facing = (byte) j;
        } else if (i == 3) {
            facing = (byte) (j & 0x7);
            numUsingPlayers = (j & 0xF8) >> 3;
        }
        return true;
    }

    @Override
    public void openInventory() {
        if (worldObj == null) return;
        numUsingPlayers++;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, ItemSystem.storageChest, 1, numUsingPlayers);
    }

    @Override
    public void closeInventory() {
        if (worldObj == null) return;
        numUsingPlayers--;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, ItemSystem.storageChest, 1, numUsingPlayers);
    }

    public void setFacing(int facing2) {
        this.facing = facing2;
    }


    public ItemStack[] getTopItemStacks() {
        return topStacks;
    }


    public int[] buildIntDataList() {
        int[] sortList = new int[topStacks.length * 3];
        int pos = 0;
        for (ItemStack is : topStacks) {
            if (is != null) {
                sortList[pos++] = Item.getIdFromItem(is.getItem());
                sortList[pos++] = is.getItemDamage();
                sortList[pos++] = is.stackSize;
            } else {
                sortList[pos++] = 0;
                sortList[pos++] = 0;
                sortList[pos++] = 0;
            }
        }
        return sortList;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.chestContents[par1] != null) {
            ItemStack var2 = this.chestContents[par1];
            this.chestContents[par1] = null;
            return var2;
        } else {
            return null;
        }
    }

    public void setMaxStackSize(int size) {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    void rotateAround(ForgeDirection axis) {
        setFacing((byte) ForgeDirection.getOrientation(facing).getRotation(axis).ordinal());
        worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, ItemSystem.storageChest, 2, getFacing());
    }

    public void wasPlaced(EntityLivingBase entityliving, ItemStack itemStack) {
    }

    public void removeAdornments() {

    }

    @Override
    public ArrayList<Integer> upgradeSlots() {
        ArrayList<Integer> slots = new ArrayList<Integer>();
        for (int i = 200; i < 207; i++) {
            slots.add(i);
        }
        return slots;
    }
}
