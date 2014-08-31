package sourceteam.network.entity.minecart;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sourceteam.network.blocks.NetworkBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ServerCart extends EntityMinecart {

    public static Block currentblock = NetworkBlocks.powerSink;

    public ServerCart(World par1World) {
        super(par1World);
    }

    public ServerCart(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    public int getMinecartType() {
        return 15;
    }

    public Block func_145817_o() {
        return currentblock;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        super.onUpdate();
    }

    public void killMinecart(DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;

        if (!par1DamageSource.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.diamond_block, 1), 0.0F);
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1) {
        super.fall(par1);
    }

    /**
     * Called every tick the minecart is on an activator rail.
     */
    public void onActivatorRailPass(int par1, int par2, int par3, boolean par4) {

    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1) {
        super.handleHealthUpdate(par1);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }

    /**
     * First layer of player interaction
     */
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {

        System.out.println("yey");

        return true;
    }

}
