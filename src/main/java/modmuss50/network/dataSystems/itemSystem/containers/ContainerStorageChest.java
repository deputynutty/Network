package modmuss50.network.dataSystems.itemSystem.containers;

import modmuss50.network.dataSystems.itemSystem.containers.slot.SlotUpgrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


/**
 * Created by Mark on 11/08/2014.
 */
public class ContainerStorageChest extends Container {
    public int size = 108;
    private EntityPlayer player;
    private IInventory chest;

    public ContainerStorageChest(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
        chest = chestInventory;
        player = ((InventoryPlayer) playerInventory).player;
        chestInventory.openInventory();
        layoutContainer(playerInventory, chestInventory, xSize, ySize);

        int y = 174;
        this.addSlotToContainer(new SlotUpgrade(chest, 200, 7, y));
        this.addSlotToContainer(new SlotUpgrade(chest, 201, 7 + 208, y));
        y += 18;
        this.addSlotToContainer(new SlotUpgrade(chest, 202, 7, y));
        this.addSlotToContainer(new SlotUpgrade(chest, 203, 7 + 208, y));
        y += 18;
        this.addSlotToContainer(new SlotUpgrade(chest, 204, 7, y));
        this.addSlotToContainer(new SlotUpgrade(chest, 205, 7 + 208, y));
        y += 18;
        this.addSlotToContainer(new SlotUpgrade(chest, 206, 7, y));
        this.addSlotToContainer(new SlotUpgrade(chest, 207, 7 + 208, y));
    }

    public boolean canInteractWith(EntityPlayer player) {
        return chest.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < size) {
                if (!mergeItemStack(itemstack1, size, inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!mergeItemStack(itemstack1, 0, size, false)) {
                return null;
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        chest.closeInventory();
    }

    protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {

        for (int chestRow = 0; chestRow < getRowCount(); chestRow++) {
            for (int chestCol = 0; chestCol < getRowLength(); chestCol++) {
                this.addSlotToContainer(new Slot(chestInventory, chestCol + chestRow * getRowLength(), 12 + chestCol * 18, 8 + chestRow * 18));
            }
        }


        int leftCol = (xSize - 162) / 2 + 1;
        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                this.addSlotToContainer(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18
                        - 10));
            }

        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            this.addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
        }


    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public int getRowCount() {
        return 108 / getRowLength();
    }

    public int getRowLength() {
        return 12;
    }


}
