package sourceteam.network.dataSystems.itemSystem;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import sourceteam.network.NetworkCore;
import sourceteam.network.dataSystems.itemSystem.blocks.BlockImport;
import sourceteam.network.dataSystems.itemSystem.blocks.BlockStorageChest;
import sourceteam.network.dataSystems.itemSystem.items.itemFilter;
import sourceteam.network.dataSystems.itemSystem.tileEntitys.TileEnityImport;
import sourceteam.network.dataSystems.itemSystem.tileEntitys.TileEntityBlockStorageContainer;
import sourceteam.network.init.initBlocks;
import sourceteam.network.init.initItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Created by Mark on 11/08/2014.
 */
public class ItemSystem {

    public static Block storageChest;
    public static Block importer;

    public static Item itemFilter;

    public static void preInit(FMLPreInitializationEvent event) {

    }

    public static void init(FMLInitializationEvent event) {
        //TODO fix texture
        storageChest = new BlockStorageChest().setBlockName(initBlocks.prefix + "BlockStorageChest").setCreativeTab(NetworkCore.Network).setBlockTextureName("network:base");
        GameRegistry.registerBlock(storageChest, initBlocks.prefix + "BlockStorageChest");
        GameRegistry.registerTileEntity(TileEntityBlockStorageContainer.class, initBlocks.prefix + "TileEntityBlockStorageContainer");

        //TODO fix texture
        importer = new BlockImport().setBlockName(initBlocks.prefix + "import").setCreativeTab(NetworkCore.Network).setBlockTextureName("network:base");
        GameRegistry.registerBlock(importer, initBlocks.prefix + "import");
        GameRegistry.registerTileEntity(TileEnityImport.class, initBlocks.prefix + "TileEnityImport");

        itemFilter = new itemFilter().setUnlocalizedName(initBlocks.prefix + "filter").setTextureName("network:itemFilter");
        registerItem(itemFilter);

    }

    public static void postInit(FMLPostInitializationEvent event) {

    }


    public static void registerItem(Item item) {
        GameRegistry.registerItem(item, initItems.prefix + item.getUnlocalizedName());
    }

}