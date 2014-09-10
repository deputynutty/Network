package modmuss50.network;

import codechicken.nei.api.API;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import modmuss50.network.blocks.tileentities.TileEntityCable;
import modmuss50.network.blocks.tileentities.TileEntityMonitor;
import modmuss50.network.blocks.tileentities.TileEntityTeleporter;
import modmuss50.network.client.Render.*;
import net.minecraftforge.common.MinecraftForge;
import sourceteam.mods.lib.client.AnimatedBlock.RenderAnimatedTile;
import modmuss50.network.blocks.tileentities.TileEntityFluidGen;
import modmuss50.network.entity.minecart.ServerCart;
import modmuss50.network.nei.InfusionNei;

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
