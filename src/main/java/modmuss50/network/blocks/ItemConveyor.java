package modmuss50.network.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import modmuss50.network.NetworkCore;

public class ItemConveyor extends Block {

    private static IIcon[] tops = new IIcon[4];
    private static IIcon[] outher = new IIcon[1];

    public ItemConveyor() {
        super(Material.iron);
        setBlockName("network:ItemConveyor");
        setBlockTextureName("network:ItemConveyor");
        setCreativeTab(NetworkCore.Network);
        setHardness(5.0F);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
    }

    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        int var6 = ((MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
    }

    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        if (par5Entity instanceof EntityItem || par5Entity instanceof EntityPlayer) {
        } else {
            return;
        }
        double m_speed = 0.05D;
        if (par5Entity instanceof EntityPlayer) {
            if (((EntityPlayer) par5Entity).getDisplayName().contains("MAPCraftGaming")) {
                m_speed = 5.05D;
            }
        }
        int a = par1World.getBlockMetadata(par2, par3, par4);
        int[] ax = {0, 1, 0, -1};
        int[] az = {-1, 0, 1, 0};

        if ((par5Entity != null) && (par5Entity.posY > par3 + 0.1D) && (!par5Entity.isSneaking())) {
            if ((par5Entity instanceof EntityItem)) {
                int x = par2;
                int y = par3;
                int z = par4;
                ItemStack my_item = null;

                if ((par5Entity instanceof EntityItem)) {
                    my_item = ((EntityItem) par5Entity).getEntityItem();
                    ((EntityItem) par5Entity).delayBeforeCanPickup = 30;
                    ((EntityItem) par5Entity).age = 500;
                }
            } else {
                if (par5Entity instanceof EntityPlayer) {
                    if (((EntityPlayer) par5Entity).getDisplayName().contains("MAPCraftGaming")) {

                    } else {
                        return;
                    }
                }
            }

            if ((ax[a] == 0) && (Math.abs(par2 + 0.5D - par5Entity.posX) < 0.5D) && (Math.abs(par2 + 0.5D - par5Entity.posX) > 0.1D)) {
                par5Entity.motionX += Math.signum(par2 + 0.5D - par5Entity.posX) * Math.min(m_speed, Math.abs(par2 + 0.5D - par5Entity.posX)) / 1.2D;
            }

            if ((az[a] == 0) && (Math.abs(par4 + 0.5D - par5Entity.posZ) < 0.5D) && (Math.abs(par4 + 0.5D - par5Entity.posZ) > 0.1D)) {
                par5Entity.motionZ += Math.signum(par4 + 0.5D - par5Entity.posZ) * Math.min(m_speed, Math.abs(par4 + 0.5D - par5Entity.posZ)) / 1.2D;
            }

            par5Entity.motionX += ax[a] * m_speed;

            par5Entity.motionZ += az[a] * m_speed;
        }
    }

    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) {
        return false;
    }

    public int getRenderType() {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
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

}
