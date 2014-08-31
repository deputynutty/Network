package sourceteam.network.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sourceteam.network.NetworkCore;
import sourceteam.network.blocks.tileentities.TileEntityMover;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMover extends BlockBase {

    private static IIcon[] tops = new IIcon[4];
    private static IIcon[] outher = new IIcon[1];
    private final Random random = new Random();

    public BlockMover() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityMover();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
        tops[0] = icon.registerIcon("network:ItemConvayor_0");
        tops[1] = icon.registerIcon("network:ItemConvayor_1");
        tops[2] = icon.registerIcon("network:ItemConvayor_2");
        tops[3] = icon.registerIcon("network:ItemConvayor_3");
        outher[0] = icon.registerIcon("network:ItemConvayor_side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0) {
            return outher[0];
        } else if (side == 1) {
            return tops[meta];
        } else {
            return outher[0];
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntityMover tile = (TileEntityMover) world.getTileEntity(x, y, z);

        if (player.isSneaking()) {
            tile.active();
            return true;
        }

        player.openGui(NetworkCore.instance, 9, world, x, y, z);

        return true;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an
     * update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
        TileEntityMover tileentitychest = (TileEntityMover) par1World.getTileEntity(par2, par3, par4);

        if (tileentitychest != null) {
            for (int j1 = 0; j1 < tileentitychest.getSizeInventory(); ++j1) {
                ItemStack itemstack = tileentitychest.getStackInSlot(j1);

                if (itemstack != null) {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem)) {
                        int k1 = this.random.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize) {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        entityitem = new EntityItem(par1World, (double) ((float) par2 + f), (double) ((float) par3 + f1), (double) ((float) par4 + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
                        entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Gets the inventory of the chest at the specified coords, accounting for
     * blocks or ocelots on top of the chest, and double chests.
     */
    public IInventory getInventory(World par1World, int par2, int par3, int par4) {
        Object object = (TileEntityMover) par1World.getTileEntity(par2, par3, par4);

        return (IInventory) object;

    }

}
