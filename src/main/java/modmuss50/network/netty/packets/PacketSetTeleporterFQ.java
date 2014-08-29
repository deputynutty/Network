package modmuss50.network.netty.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import modmuss50.network.blocks.tileentities.TileEntityTeleporter;
import modmuss50.network.netty.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import modmuss50.mods.lib.Location;

public class PacketSetTeleporterFQ extends AbstractPacket {

    int fq, x, y, z;

    public PacketSetTeleporterFQ() {

    }

    public PacketSetTeleporterFQ(Location te, int id) {
        fq = id;
        x = te.getX();
        y = te.getY();
        z = te.getZ();
    }


    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(fq);
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.fq = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        if (player.worldObj.getTileEntity(x, y, z) instanceof TileEntityTeleporter) {
            ((TileEntityTeleporter) player.worldObj.getTileEntity(x, y, z)).fq = fq;
        }
        System.out.println("packet was recived on the client side");
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        if (player.worldObj.getTileEntity(x, y, z) instanceof TileEntityTeleporter) {
            ((TileEntityTeleporter) player.worldObj.getTileEntity(x, y, z)).fq = fq;
        }
        System.out.println("packet was recived on the server side");

    }
}
