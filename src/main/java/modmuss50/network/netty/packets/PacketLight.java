package modmuss50.network.netty.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.NetworkBlocks;
import modmuss50.network.blocks.tileentities.TileEntityLightPeripheral;
import modmuss50.network.netty.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PacketLight implements IMessage {
    int x, y, z;
    int r, g, b;
    int dim;

    public PacketLight() {

    }

    public PacketLight(int posX, int posY, int posZ, int r2, int g2, int b2, int dim) {
        this.x = posX;
        this.y = posY;
        this.z = posZ;

        this.r = r2;
        this.g = g2;
        this.b = b2;

        this.dim = dim;
    }

    public PacketLight(TileEntityLightPeripheral te) {
        this.x = te.xCoord;
        this.y = te.yCoord;
        this.z = te.zCoord;

        this.r = te.red;
        this.g = te.green;
        this.b = te.blue;

        this.dim = te.dim;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);

        buffer.writeInt(r);
        buffer.writeInt(g);
        buffer.writeInt(b);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();

        this.r = buffer.readInt();
        this.g = buffer.readInt();
        this.b = buffer.readInt();
    }

//    @Override
//    public void handleClientSide(EntityPlayer player) {
//        World worldObj = player.worldObj;
//
//        if (worldObj.getBlock(posX, posY, posZ) == NetworkBlocks.lightPeripheral) {
//            TileEntityLightPeripheral te = (TileEntityLightPeripheral) worldObj.getTileEntity(posX, posY, posZ);
//            if (te != null) {
//                te.red = this.r;
//                te.green = this.g;
//                te.red = this.r;
//                te.getWorldObj().markBlockRangeForRenderUpdate(posX, posY, posZ, posX, posY, posZ);
//            }
//        }
//    }
//
//    @Override
//    public void handleServerSide(EntityPlayer player) {
//        int dimensionID = player.dimension;
//        World worldObj = player.worldObj;
//
//        if (worldObj.getBlock(posX, posY, posZ) == NetworkBlocks.lightPeripheral) {
//            TileEntityLightPeripheral te = (TileEntityLightPeripheral) worldObj.getTileEntity(posX, posY, posZ);
//            if (te != null) {
//                te.red = this.r;
//                te.green = this.g;
//                te.red = this.r;
//            }
//        }
//       NetworkCore.packetPipeline.sendToAll(new PacketLight(posX, posY, posZ,r, g, b));
//    }

}
