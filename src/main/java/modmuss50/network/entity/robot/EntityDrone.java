package modmuss50.network.entity.robot;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sourceteam.mods.lib.Location;


public class EntityDrone extends EntityLivingBase {

    public EntityDrone(World world) {
        super(Minecraft.getMinecraft().theWorld);
    }

    public Location nextLocation = null;

    protected static final int scanRadius = 10;

    @Override
    public void onUpdate() {
        if(this.nextLocation != null){
            if(this.worldObj.getBlock(nextLocation.getX(), nextLocation.getY() - 2, nextLocation.getZ()) == Blocks.air){
                this.nextLocation = null;
                return;
            }

            if(this.posX == nextLocation.getX() && this.posY == nextLocation.getY()  + 2 && this.posZ == nextLocation.getZ()){
                this.nextLocation = null;
                return;
            }

            if(nextLocation.getX() < this.posX){
                this.moveEntity(-0.1, 0, 0);
            } else if(nextLocation.getX() > this.posX){
                this.moveEntity(0.1, 0, 0);
            }

            if(nextLocation.getY() < this.posY){
                this.moveEntity(0, -0.1, 0);
            } else if(nextLocation.getY() > this.posY){
                this.moveEntity(0, 0.1, 0);
            }

            if(nextLocation.getZ() < this.posZ){
                this.moveEntity(0, 0, -0.1);
            } else if(nextLocation.getY() > this.posZ){
                this.moveEntity(0, 0, 0.1);
            }

        }

        if(this.nextLocation == null){
            for (int x = -scanRadius; x < scanRadius; x++) {
                for (int y = -scanRadius; y < scanRadius; y++) {
                    for (int z = -scanRadius; z < scanRadius; z++) {
                        TileEntity tile = this.worldObj.getTileEntity((int) this.posX + x, (int)this.posY + y, (int)this.posZ + z);
                        if(tile != null && tile instanceof IInventory){
                            if(this.posX != tile.xCoord && this.posY != tile.yCoord + 2 && this.posZ != tile.zCoord)
                            nextLocation = new Location(tile.xCoord, tile.yCoord + 2, tile.zCoord);
                            return;
                        }
                    }
                }
            }
        }
    }

    public boolean attackEntityFrom(DamageSource damageSource, float p_70097_2_)
    {
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