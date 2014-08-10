package modmuss50.network.netty;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import modmuss50.network.NetworkCore;
import modmuss50.network.netty.packets.LightMessgeHandler;
import modmuss50.network.netty.packets.PacketLight;

/**
 * Created by Mark on 10/08/2014.
 */
public class networkWrapper {

    public static SimpleNetworkWrapper networkWrapper;


    public static void postInit(){
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("network");

        networkWrapper.registerMessage(LightMessgeHandler.class, PacketLight.class, 0, Side.CLIENT);
        networkWrapper.registerMessage(LightMessgeHandler.class, PacketLight.class, 1, Side.SERVER);
    }

}
