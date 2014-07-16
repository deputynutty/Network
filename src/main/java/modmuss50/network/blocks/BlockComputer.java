package modmuss50.network.blocks;

import java.awt.*;
import java.util.Random;

import modmuss50.network.blocks.tileentities.TileEntityComputer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {

		TileEntityComputer te;
		te = (TileEntityComputer) par1World.getTileEntity(par2, par3, par4);

		if (par1World.isRemote) {
			if (te.hasConectionServer()) {
				par5EntityPlayer.addChatMessage(new ChatComponentText("Power: " + te.Getpower()));
			}
			else {
				par5EntityPlayer.addChatMessage(new ChatComponentText("ERROR: No Connection to server!"));
			}
		}

		return true;
	}

}
