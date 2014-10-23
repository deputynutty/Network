package modmuss50.network.entity.robot;

import modmuss50.mods.lib.Location;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;


public class EntityDrone extends EntityLivingBase {

	protected static final int scanRadius = 10;
	public Location nextLocation = null;

    public EntityDrone(World p_i1594_1_) {
        super(p_i1594_1_);
        this.applyEntityAttributes();
        this.setHealth(100F);
        this.preventEntitySpawning = true;
        this.field_70770_ap = (float)(Math.random() + 1.0D) * 0.01F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.field_70769_ao = (float)Math.random() * 12398.0F;
        this.rotationYaw = (float)(Math.random() * Math.PI * 2.0D);
        this.rotationYawHead = this.rotationYaw;
        this.stepHeight = 0.5F;
    }

    @Override
	public void onUpdate() {
		if (this.nextLocation != null) {
			if (this.worldObj.getBlock(nextLocation.getX(), nextLocation.getY() - 2, nextLocation.getZ()) == Blocks.air) {
				this.nextLocation = null;
				return;
			}

			if (this.posX == nextLocation.getX() && this.posY == nextLocation.getY() + 2 && this.posZ == nextLocation.getZ()) {
				this.nextLocation = null;
				return;
			}

			if (nextLocation.getX() < this.posX) {
				this.moveEntity(-0.1, 0, 0);
			} else if (nextLocation.getX() > this.posX) {
				this.moveEntity(0.1, 0, 0);
			}

			if (nextLocation.getY() < this.posY) {
				this.moveEntity(0, -0.1, 0);
			} else if (nextLocation.getY() > this.posY) {
				this.moveEntity(0, 0.1, 0);
			}

			if (nextLocation.getZ() < this.posZ) {
				this.moveEntity(0, 0, -0.1);
			} else if (nextLocation.getY() > this.posZ) {
				this.moveEntity(0, 0, 0.1);
			}

		}

		if (this.nextLocation == null) {
			for (int x = -scanRadius; x < scanRadius; x++) {
				for (int y = -scanRadius; y < scanRadius; y++) {
					for (int z = -scanRadius; z < scanRadius; z++) {
						TileEntity tile = this.worldObj.getTileEntity((int) this.posX + x, (int) this.posY + y, (int) this.posZ + z);
						if (tile != null && tile instanceof IInventory) {
							if (this.posX != tile.xCoord && this.posY != tile.yCoord + 2 && this.posZ != tile.zCoord)
								nextLocation = new Location(tile.xCoord, tile.yCoord + 2, tile.zCoord);
							return;
						}
					}
				}
			}
		}
	}

	public boolean attackEntityFrom(DamageSource damageSource, float p_70097_2_) {
		this.setDead();
		return true;
	}

	@Override
	public ItemStack getHeldItem() {
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int p_71124_1_) {
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {

	}

	@Override
	public ItemStack[] getLastActiveItems() {
		return new ItemStack[0];
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) {

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {

	}

}
