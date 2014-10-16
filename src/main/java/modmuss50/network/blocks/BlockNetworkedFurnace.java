package modmuss50.network.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.mods.lib.Location;
import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityNetworkedFurnace;
import modmuss50.network.netty.packets.PacketUpdateEnergySystem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNetworkedFurnace extends BlockBase {
	public IIcon[] icons = new IIcon[2];

	public BlockNetworkedFurnace() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityNetworkedFurnace();
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (!canBlockBeused(world, x, y, z, player))
			return false;
		TileEntityNetworkedFurnace tileentityfurnace = (TileEntityNetworkedFurnace) world.getTileEntity(x, y, z);

		if(!world.isRemote && tileentityfurnace != null){
			PacketUpdateEnergySystem.sendPowerToAllClients(tileentityfurnace.ENERGY_SYSTEM(), new Location(x, y, z));
		}

		if (tileentityfurnace != null) {
			player.openGui(NetworkCore.instance, 8, world, x, y, z);
		}

		return true;
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int m) {
		TileEntityNetworkedFurnace te = (TileEntityNetworkedFurnace) w.getTileEntity(x, y, z);

		if (te != null) {
			for (int i = 0; i < te.getSizeInventory(); i++) {
				if (te.getStackInSlot(i) != null) {
					new Random().nextInt(2);
					EntityItem e = new EntityItem(w, (double) x + new Random().nextInt(2), (double) y, (double) z + new Random().nextInt(2), te.getStackInSlot(i));
					w.spawnEntityInWorld(e);
				}
			}
		}
		super.breakBlock(w, x, y, z, b, m);
	}

	/**
	 * Called when the block is placed in the world.
	 */
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
		int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0) {
			w.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1) {
			w.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2) {
			w.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3) {
			w.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		super.onBlockPlacedBy(w, x, y, z, p, i);
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return p_149691_1_ == 1 ? this.icons[0] : (p_149691_1_ == 0 ? this.icons[0] : (p_149691_1_ != p_149691_2_ ? this.icons[0] : this.icons[1]));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.icons[1] = p_149651_1_.registerIcon("network:FurnaceFront");
		this.icons[0] = p_149651_1_.registerIcon("network:ItemConvayor_side");
	}

}
