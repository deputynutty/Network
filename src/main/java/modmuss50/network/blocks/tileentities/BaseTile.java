package modmuss50.network.blocks.tileentities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BaseTile extends TileEntity {

	public String	owner	= "[Network]";

	public String getOwner() {
		return owner;
	}

	public void onBlockPlacedBy(EntityLivingBase entity, ItemStack stack) {
		if (entity instanceof EntityPlayer) {
			owner = ((EntityPlayer) entity).getDisplayName();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("owner", owner);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("owner")) {
			owner = nbt.getString("owner");
		}
	}
}
