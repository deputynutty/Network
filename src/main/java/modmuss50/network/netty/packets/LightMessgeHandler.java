package modmuss50.network.netty.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import modmuss50.network.blocks.tileentities.TileEntityLightPeripheral;
import modmuss50.network.netty.networkWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

/**
 * Created by Mark on 10/08/2014.
 */
public class LightMessgeHandler implements IMessageHandler<PacketLight, IMessage> {

    @Override
    public IMessage onMessage(PacketLight message, MessageContext ctx) {
            TileEntityLightPeripheral light = (TileEntityLightPeripheral) MinecraftServer.getServer().worldServerForDimension(message.dim).getTileEntity(message.x, message.y, message.z);
            if (light != null) light.red = message.r;
            if (light != null) light.green = message.g;
            if (light != null) light.blue = message.b;

        System.out.println("hi");

        if(ctx.side == Side.SERVER){
            networkWrapper.networkWrapper.sendToAll(new PacketLight(message.x, message.y, message.z, message.r, message.g, message.b, message.dim));
        }
        return null;
    }
}
