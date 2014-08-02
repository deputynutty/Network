package modmuss50.network.blocks;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.api.INetworkComponent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.world.IBlockAccess;

import java.awt.*;
import java.util.Random;

public class BlockTabletCrafter extends Block implements INetworkComponent {

    public BlockTabletCrafter() {
        super(Material.circuits);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess iba, int x, int y, int z) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            String name = Minecraft.getMinecraft().thePlayer.getDisplayName();
            if (name.equals("roboyobo") || name.equals("tattyseal") || name.equals("mark123mark")) {
                Random rand = new Random();
                Color col = Color.getHSBColor(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                return col.getRGB();
            }
        }
        return super.colorMultiplier(iba, x, y, z);
    }

}
