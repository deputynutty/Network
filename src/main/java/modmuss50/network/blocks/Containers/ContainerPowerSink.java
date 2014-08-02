package modmuss50.network.blocks.Containers;

import modmuss50.network.blocks.tileentities.TileEntityPowerSink;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPowerSink extends Container {
    private IInventory lowerChestInventory;
    private int numRows = 3;

    private TileEntityPowerSink tileEntity;

    public ContainerPowerSink(InventoryPlayer player, TileEntityPowerSink tile) {
        this.tileEntity = tile;
        lowerChestInventory = this.tileEntity;

        int y = 5;
        this.addSlotToContainer(new Slot(this.tileEntity, 0, 150, y));
        y += 18;
        this.addSlotToContainer(new Slot(this.tileEntity, 1, 150, y));
        y += 18;
        this.addSlotToContainer(new Slot(this.tileEntity, 2, 150, y));
        y += 18;
        this.addSlotToContainer(new Slot(this.tileEntity, 3, 150, y));

        this.bindPlayerInventory(player);
    }

    protected void bindPlayerInventory(InventoryPlayer par1InventoryPlayer) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or
     * you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < this.numRows * 9) {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        this.lowerChestInventory.closeInventory();
    }

    /**
     * Return this chest container's lower chest inventory.
     */
    public IInventory getLowerChestInventory() {
        return this.lowerChestInventory;
    }
}
