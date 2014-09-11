package modmuss50.network.blocks;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityComputer;
import modmuss50.network.client.gui.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Random;

public class BlockComputer extends BlockBase {

    public BlockComputer() {
        super(Material.anvil);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess iba, int x, int y, int z) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            String name = Minecraft.getMinecraft().thePlayer.getDisplayName();
            if (name.equals("roboyobo") || name.equals("tattyseal") || name.equals("mark123mark")) {
                Random rand = new Random();
                Color col = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

                return col.getRGB();
            }
        }
        return super.colorMultiplier(iba, x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityComputer();
    }

    @Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {

        par5EntityPlayer.openGui(NetworkCore.instance, GuiHandler.computerGuiID, par1World, (int) x, (int) y, (int) z);
        return true;
    }

}
