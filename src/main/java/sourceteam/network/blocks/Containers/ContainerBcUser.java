package sourceteam.network.blocks.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerBcUser extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return false;
    }
    // private IInventory lowerChestInventory;
    // private int numRows = 3;
    //
    // private TileEntityBCUser tileEntity;
    //
    // public ContainerBcUser(InventoryPlayer player,
    // TileEntityBCUser tile) {
    // this.tileEntity = tile;
    // lowerChestInventory = this.tileEntity;
    //
    // // this.addSlotToContainer(new Slot(this.tileEntity, 0, 8, 24));
    //
    // this.bindPlayerInventory(player);
    // }
    //
    // protected void bindPlayerInventory(InventoryPlayer par1InventoryPlayer) {
    // int i;
    // for (i = 0; i < 3; ++i) {
    // for (int j = 0; j < 9; ++j) {
    // this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9
    // + 9, 8 + j * 18, 84 + i * 18));
    // }
    // }
    //
    // for (i = 0; i < 9; ++i) {
    // this.addSlotToContainer(new Slot(par1InventoryPlayer, i,
    // 8 + i * 18, 142));
    // }
    // }
    //
    // public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
    // return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
    // }
    //
    // /**
    // * Called when a player shift-clicks on a slot. You must override this or
    // * you will crash when someone does that.
    // */
    // public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int
    // par2) {
    // ItemStack itemstack = null;
    // Slot slot = (Slot) this.inventorySlots.get(par2);
    //
    // if (slot != null && slot.getHasStack()) {
    // ItemStack itemstack1 = slot.getStack();
    // itemstack = itemstack1.copy();
    //
    // if (par2 < this.numRows * 9) {
    // if (!this.mergeItemStack(itemstack1, this.numRows * 9,
    // this.inventorySlots.size(), true)) {
    // return null;
    // }
    // } else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9,
    // false)) {
    // return null;
    // }
    //
    // if (itemstack1.stackSize == 0) {
    // slot.putStack((ItemStack) null);
    // } else {
    // slot.onSlotChanged();
    // }
    // }
    //
    // return itemstack;
    // }
    //
    // /**
    // * Called when the container is closed.
    // */
    // public void onContainerClosed(EntityPlayer par1EntityPlayer) {
    // super.onContainerClosed(par1EntityPlayer);
    // this.lowerChestInventory.closeInventory();
    // }
    //
    // /**
    // * Return this chest container's lower chest inventory.
    // */
    // public IInventory getLowerChestInventory() {
    // return this.lowerChestInventory;
    // }
}
