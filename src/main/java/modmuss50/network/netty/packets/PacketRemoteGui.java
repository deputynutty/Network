package modmuss50.network.netty.packets;

import modmuss50.network.netty.AbstractPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketRemoteGui extends AbstractPacket {

	int	x;
	int	y;
	int	z;

	public PacketRemoteGui(int x, int y, int z, String name) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(x);

	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		Block irt = player.worldObj.getBlock(x, y, z);
		irt.onBlockActivated(player.worldObj, x, y, z, player, 0, 0.5F, 0.5F, 0.5F);

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		Block irt = player.worldObj.getBlock(x, y, z);
		irt.onBlockActivated(player.worldObj, x, y, z, player, 0, 0.5F, 0.5F, 0.5F);
	}
}
