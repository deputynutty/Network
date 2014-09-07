package sourceteam.network;

import codechicken.nei.api.API;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.common.MinecraftForge;
import sourceteam.mods.lib.client.AnimatedBlock.RenderAnimatedTile;
import sourceteam.network.blocks.tileentities.TileEntityCable;
import sourceteam.network.blocks.tileentities.TileEntityFluidGen;
import sourceteam.network.blocks.tileentities.TileEntityMonitor;
import sourceteam.network.blocks.tileentities.TileEntityTeleporter;
import sourceteam.network.client.Render.*;
import sourceteam.network.entity.minecart.ServerCart;
import sourceteam.network.nei.InfusionNei;

public class ClientProxy extends CommonProxy {

    @Override
    public void initRenderers() {
        //load some of the renders
        RenderingRegistry.registerEntityRenderingHandler(ServerCart.class, new RenderCustomCart());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new RenderCable());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidGen.class, new RenderFluidGen());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMonitor.class, new RenderMonitor());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTeleporter.class, new RenderAnimatedTile());

        MinecraftForge.EVENT_BUS.register(new MobHealthBars());


        //TODO this need to be client only
        //Here because im lazy
        API.registerRecipeHandler(new InfusionNei());
    }
}
