package modmuss50.network.netty.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import modmuss50.network.blocks.NetworkBlocks;
import modmuss50.network.blocks.tileentities.TileEntityPowerSink;
import modmuss50.network.netty.AbstractPacket;

public class PacketServer extends AbstractPacket {
    int posX, posY, posZ;
    int NetworkNetUnits;

    public PacketServer() {

    }

    public PacketServer(int posX, int posY, int posZ, int networkNetUnits) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.NetworkNetUnits = networkNetUnits;

    }

    public PacketServer(TileEntityPowerSink te) {
        this.posX = te.xCoord;
        this.posY = te.yCoord;
        this.posZ = te.zCoord;

        this.NetworkNetUnits = te.NetworkNetUnits;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(posX);
        buffer.writeInt(posY);
        buffer.writeInt(posZ);

        buffer.writeInt(NetworkNetUnits);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.posX = buffer.readInt();
        this.posY = buffer.readInt();
        this.posZ = buffer.readInt();

        this.NetworkNetUnits = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        int dimensionID = player.dimension;
        World worldObj = player.worldObj;

        if (worldObj.getBlock(posX, posY, posZ) == NetworkBlocks.powerSink) {
            TileEntityPowerSink te = (TileEntityPowerSink) worldObj.getTileEntity(posX, posY, posZ);
            if (te != null) {
                te.NetworkNetUnits = this.NetworkNetUnits;
                // te.markDirty();
                te.getWorldObj().markBlockForUpdate(posX, posY, posZ);
                te.getWorldObj().markBlockRangeForRenderUpdate(posX, posY, posZ, posX, posY, posZ);
            }
        }

        // System.out.println("YEYEYEYYEYEYEYEYEYEYEYE");

    }

    @Override
    public void handleServerSide(EntityPlayer player) {

    }
}
