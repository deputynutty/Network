package modmuss50.network.blocks.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.blocks.tileentities.TileEntityInfusionFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

/**
 * Created by Mark on 28/07/2014.
 */
public class ContainerInfusionFurnace extends Container {
	public TileEntityInfusionFurnace te;
	public EntityPlayer p;

	public ContainerInfusionFurnace(TileEntityInfusionFurnace te, EntityPlayer p) {
		super();
		this.te = te;
		this.p = p;

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(p.inventory, i, i * 18 + 8, 142));
		}

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 3; y++) {
				this.addSlotToContainer(new Slot(p.inventory, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
			}
		}

		addSlotToContainer(new Slot(te, 0, 56, 35));
		// addSlotToContainer(new Slot(te, 1, 56, 53));
		addSlotToContainer(new SlotFurnace(p, te, 2, 116, 35));
	}

	@Override
	public boolean canInteractWith(EntityPlayer p) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return null;
	}

	@Override
	public void addCraftingToCrafters(ICrafting player) {
		super.addCraftingToCrafters(player);
		player.sendProgressBarUpdate(this, 0, te.timeSmelted);
		player.sendProgressBarUpdate(this, 1, te.energySystem.power);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		if (id == 0) {
			te.timeSmelted = data;
		} else if (id == 1) {
			te.energySystem.power = data;
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (Object player : crafters) {
			((ICrafting) player).sendProgressBarUpdate(this, 0, te.timeSmelted);
			((ICrafting) player).sendProgressBarUpdate(this, 1, te.energySystem.power);
		}
	}
}
