package modmuss50.network.dataSystems.itemSystem;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import modmuss50.network.NetworkCore;
import modmuss50.network.dataSystems.itemSystem.blocks.BlockStorageChest;
import modmuss50.network.dataSystems.itemSystem.tileEntitys.TileEntityBlockStorageContainer;
import modmuss50.network.init.initBlocks;
import net.minecraft.block.Block;

/**
 * Created by Mark on 11/08/2014.
 */
public class ItemSystem {

    public static Block storageChest;


    public static void preInit(FMLPreInitializationEvent event) {

    }



    public static void init(FMLInitializationEvent event) {
            storageChest = new BlockStorageChest().setBlockName(initBlocks.prefix + "BlockStorageChest").setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockStorageChest");
        GameRegistry.registerBlock(storageChest, initBlocks.prefix + "BlockStorageChest");
        GameRegistry.registerTileEntity(TileEntityBlockStorageContainer.class, initBlocks.prefix + "TileEntityBlockStorageContainer");
    }

    public static void postInit(FMLPostInitializationEvent event) {

    }


}
