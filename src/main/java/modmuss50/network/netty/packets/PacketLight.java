package modmuss50.network.netty.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import modmuss50.network.blocks.NetworkBlocks;
import modmuss50.network.blocks.tileentities.TileEntityLightPeripheral;
import modmuss50.network.netty.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PacketLight extends AbstractPacket {
    int posX, posY, posZ;
    int r, g, b;

    public PacketLight() {

    }

    public PacketLight(int posX, int posY, int posZ, int r2, int g2, int b2) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.r = r2;
        this.g = g2;
        this.b = b2;
    }

    public PacketLight(TileEntityLightPeripheral te) {
        this.posX = te.xCoord;
        this.posY = te.yCoord;
        this.posZ = te.zCoord;

        this.r = te.red;
        this.g = te.green;
        this.b = te.blue;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(posX);
        buffer.writeInt(posY);
        buffer.writeInt(posZ);

        buffer.writeInt(r);
        buffer.writeInt(g);
        buffer.writeInt(b);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.posX = buffer.readInt();
        this.posY = buffer.readInt();
        this.posZ = buffer.readInt();

        this.r = buffer.readInt();
        this.g = buffer.readInt();
        this.b = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        World worldObj = player.worldObj;

        if (worldObj.getBlock(posX, posY, posZ) == NetworkBlocks.lightPeripheral) {
            TileEntityLightPeripheral te = (TileEntityLightPeripheral) worldObj.getTileEntity(posX, posY, posZ);
            if (te != null) {
                te.red = this.r;
                te.green = this.g;
                te.red = this.r;
                te.getWorldObj().markBlockRangeForRenderUpdate(posX, posY, posZ, posX, posY, posZ);
            }
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        int dimensionID = player.dimension;
        World worldObj = player.worldObj;

        if (worldObj.getBlock(posX, posY, posZ) == NetworkBlocks.lightPeripheral) {
            TileEntityLightPeripheral te = (TileEntityLightPeripheral) worldObj.getTileEntity(posX, posY, posZ);
            if (te != null) {
                te.red = this.r;
                te.green = this.g;
                te.red = this.r;
            }
        }
    }

}
