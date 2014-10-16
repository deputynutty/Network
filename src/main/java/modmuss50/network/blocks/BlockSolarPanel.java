package modmuss50.network.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.mods.lib.multiblock.MultiblockValidationException;
import modmuss50.network.blocks.tileentities.TileEntitySolarPanel;
import modmuss50.network.blocks.tileentities.multiblock.TileEntityMultiblockPower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSolarPanel extends BlockBase {

	private static IIcon[] tops = new IIcon[1];
	private static IIcon[] bottoms = new IIcon[1];
	private static IIcon[] sides = new IIcon[1];

	public BlockSolarPanel() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntitySolarPanel();
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

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TileEntitySolarPanel) {
				if (!world.isRemote)
					player.addChatMessage(new ChatComponentText("POWER  " + Integer.toString(((TileEntitySolarPanel) tileEntity).ENERGY_SYSTEM().getPower()) + "/" + Integer.toString(((TileEntitySolarPanel) tileEntity).ENERGY_SYSTEM().getPowerStorageSize()) + " " + Integer.toString(modmuss50.mods.lib.util.Math.percentage(((TileEntitySolarPanel) tileEntity).ENERGY_SYSTEM().getPowerStorageSize(), ((TileEntitySolarPanel) tileEntity).ENERGY_SYSTEM().getPower())) + "%"));
		}
		return false;
	}


}
