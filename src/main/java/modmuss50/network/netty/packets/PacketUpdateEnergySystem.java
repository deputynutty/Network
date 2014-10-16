package modmuss50.network.netty.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import modmuss50.mods.lib.Location;
import modmuss50.network.NetworkCore;
import modmuss50.network.api.power.EnergySystem;
import modmuss50.network.api.power.IEnergyFace;
import modmuss50.network.netty.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PacketUpdateEnergySystem extends AbstractPacket {

	int power, x, y, z;

	public PacketUpdateEnergySystem() {

	}

	public PacketUpdateEnergySystem(Location te, int powerAmmout) {
		power = powerAmmout;
		x = te.getX();
		y = te.getY();
		z = te.getZ();
	}

	public static void sendPowerToAllClients(EnergySystem energySystem, Location location) {
		NetworkCore.packetPipeline.sendToAll(new PacketUpdateEnergySystem(location, energySystem.getPower()));
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(power);
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.power = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		TileEntity tileEntity = player.worldObj.getTileEntity(x, y, z);
		if (tileEntity instanceof IEnergyFace) {
			EnergySystem energyFace = ((IEnergyFace) tileEntity).ENERGY_SYSTEM();
			energyFace.setPower(power);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		TileEntity tileEntity = player.worldObj.getTileEntity(x, y, z);
		if (tileEntity instanceof IEnergyFace) {
			EnergySystem energyFace = ((IEnergyFace) tileEntity).ENERGY_SYSTEM();
			energyFace.setPower(power);
		}
	}
}
