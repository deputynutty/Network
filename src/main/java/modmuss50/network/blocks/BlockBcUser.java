package modmuss50.network.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.blocks.tileentities.TileEntityBCUser;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBcUser extends BlockBase {

	private static IIcon[] tops = new IIcon[1];
	private static IIcon[] bottoms = new IIcon[1];
	private static IIcon[] sides = new IIcon[1];

	public BlockBcUser() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityBCUser();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		tops[0] = icon.registerIcon("network:BlockSolarPanel_top");
		bottoms[0] = icon.registerIcon("network:BlockSolarPanel_bottom");
		sides[0] = icon.registerIcon("network:BlockSolarPanel_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side == 0) {
			return bottoms[0];
		} else if (side == 1) {
			return tops[0];
		} else {
			return sides[0];
		}
	}

	// /**
	// * Called upon block activation (right click on the block.)
	// */
	// public boolean onBlockActivated(World par1World, int par2, int par3,
	// int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
	// float par8, float par9) {
	// TileEntityBCUser tile = (TileEntityBCUser) par1World
	// .getTileEntity(par2, par3, par4);
	//
	// ItemStack stack = par5EntityPlayer.inventory.getCurrentItem();
	//
	// TileEntityBCUser tileentity = (TileEntityBCUser) par1World
	// .getTileEntity(par2, par3, par4);
	//
	// if (tileentity != null) {
	// par5EntityPlayer.openGui(NetworkCore.instance, 7, par1World, par2,
	// par3, par4);
	// }
	//
	// return true;
	//
	// }

}
