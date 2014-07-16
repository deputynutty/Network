package modmuss50.network.netty.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import modmuss50.network.api.ILinkedTile;
import modmuss50.network.netty.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import sourceteam.mods.lib.Location;

public class PacketSetRemoteTile extends AbstractPacket {

    int ox, oy, oz, tx, ty, tz;

    public PacketSetRemoteTile() {

    }

    public PacketSetRemoteTile(Location origin, Location target) {
        ox = origin.getX();
        oy = origin.getY();
        oz = origin.getZ();
        tx = target.getX();
        ty = target.getY();
        tz = target.getZ();
    }



    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(ox);
        buffer.writeInt(oy);
        buffer.writeInt(oz);
        buffer.writeInt(tx);
        buffer.writeInt(ty);
        buffer.writeInt(tz);

    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.ox = buffer.readInt();
        this.oy = buffer.readInt();
        this.oz = buffer.readInt();
        this.tx = buffer.readInt();
        this.ty = buffer.readInt();
        this.tz = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {


    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        if(player.worldObj.getTileEntity(ox, oy, oz) instanceof ILinkedTile){
            ((ILinkedTile) player.worldObj.getTileEntity(ox, oy,oz)).setLocation(new Location(tx, ty, tz));
            System.out.println("Packet send and recived on the server side!");

        }
    }
}
