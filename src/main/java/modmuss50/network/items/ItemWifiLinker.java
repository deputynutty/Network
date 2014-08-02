package modmuss50.network.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.NetworkCore;
import modmuss50.network.api.ILinkedTile;
import modmuss50.network.netty.packets.PacketSetRemoteTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourceteam.mods.lib.Location;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 25/02/14 Time: 13:17
 */
public class ItemWifiLinker extends Item {

    private int modemX, modemY, modemZ;

    private boolean hasModem = false;

    public ItemWifiLinker() {
        super();
        modemX = 0;
        modemY = 0;
        modemZ = 0;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        // if(!world.isRemote) {
//		Block block = world.getBlock(x, y, z);
//		if (block == NetworkBlocks.modem) {
//			modemX = x;
//			modemY = y;
//			modemZ = z;
//
//			hasModem = true;
//
//			return true;
//		}
//		else
//			if (block == NetworkBlocks.networkPeripheral) {
//				if (hasModem) {
//					TileEntity te = world.getTileEntity(x, y, z);
//					if (te != null && te instanceof TileEntityNetworkPeripheral) {
//						TileEntityNetworkPeripheral tenp = (TileEntityNetworkPeripheral) te;
//						tenp.modemX = modemX;
//						tenp.modemY = modemY;
//						tenp.modemZ = modemZ;
//
//						System.out.println("Set Modem X To: " + tenp.modemX);
//						System.out.println("Set Modem Y To: " + tenp.modemY);
//						System.out.println("Set Modem Z To: " + tenp.modemZ);
//
//						// NetworkCore.packetPipeline.sendTo(new PacketWifi(x,
//						// y, z,
//						// modemX, modemY, modemZ), (EntityPlayerMP) player);
//
//						return true;
//					}
//				}
//			}
//		// }

        if (world.getTileEntity(x, y, z) instanceof ILinkedTile) {
            if (player.isSneaking()) {
                modemX = x;
                modemY = y;
                modemZ = z;

                hasModem = true;
                return true;
            } else if (hasModem) {
                if (canlink(world.getTileEntity(x, y, z), new Location(modemX, modemY, modemZ), world)) {
                    ((ILinkedTile) world.getTileEntity(x, y, z)).setLocation(new Location(modemX, modemY, modemZ));
                }
                NetworkCore.packetPipeline.sendToServer(new PacketSetRemoteTile(new Location(x, y, z), new Location(modemX, modemY, modemZ)));
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
        list.add("Linked to: ");
        list.add("X: " + modemX);
        list.add("Y: " + modemY);
        list.add("Z: " + modemZ);
    }

    public boolean canlink(TileEntity tile, Location location, World world) {
        if (tile instanceof ILinkedTile) {
            for (int i = 0; i < ((ILinkedTile) tile).conectableTiles().length; i++) {
                if (((ILinkedTile) tile).conectableTiles()[i].getClass().getName().equals(world.getTileEntity(location.getX(), location.getY(), location.getZ()).getClass().getName())) {
                    return true;
                }
            }
        }
        return false;
    }

}
