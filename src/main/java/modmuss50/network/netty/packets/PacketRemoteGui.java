package modmuss50.network.netty.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import modmuss50.network.netty.AbstractPacket;

public class PacketRemoteGui extends AbstractPacket {

    int x;
    int y;
    int z;

    public PacketRemoteGui(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketRemoteGui() {
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
        //Block irt = player.worldObj.getBlock(x, y, z);
        //irt.onBlockActivated(player.worldObj, x, y, z, player, 0, 0.5F, 0.5F, 0.5F);

        System.out.println("The server say hi!");
        //    player.worldObj.getBlock(x, y, z).onBlockActivated(player.worldObj, x, y, z, player, 1, 0F, 0F, 0F);


//        TileEntityInfusionFurnace tileentityfurnace = (TileEntityInfusionFurnace) player.worldObj.getTileEntity(x, y, z);
//
//        if (tileentityfurnace != null) {
//            player.openGui(NetworkCore.instance, 12, player.worldObj, x, y, z);
//
//        }


    }
}
