package modmuss50.network.init;

import cpw.mods.fml.common.registry.GameRegistry;
import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.*;
import modmuss50.network.blocks.rails.RailBase;
import modmuss50.network.blocks.tileentities.*;

public class initBlocks {

    //The prefix this is to stop cross mod conflict
    public static String prefix = "modmuss50.network.";

    public static void loadBlocks() {
        NetworkBlocks.networkRail = new RailBase().setBlockName(prefix + "networkRail").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:networkRail");
        GameRegistry.registerBlock(NetworkBlocks.networkRail, prefix + "NetworkRail");

        NetworkBlocks.ItemConveyor = new ItemConveyor().setBlockName(prefix + "ItemConveyor").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:ItemConveyor");
        GameRegistry.registerBlock(NetworkBlocks.ItemConveyor, prefix + "ItemConveyor");

        NetworkBlocks.BouncyItemConveyor = new BouncyItemConveyor().setBlockName(prefix + "BouncyItemConveyor").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BouncyItemConveyor");
        GameRegistry.registerBlock(NetworkBlocks.BouncyItemConveyor, prefix + "BouncyItemConveyor");


        NetworkBlocks.networkCable = new BlockNetworkCable().setBlockName(prefix + "networkCable").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:networkCable");
        GameRegistry.registerBlock(NetworkBlocks.networkCable, prefix + "networkCable");
        GameRegistry.registerTileEntity(TileEntityCable.class, prefix + "TileEntityCable");
        GameRegistry.registerTileEntity(TileEntityPowerImputCable.class, prefix + "TileEntityPowerImputCable");

        NetworkBlocks.monitor = new BlockMonitor().setBlockName(prefix + "monitor").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:monitor");
        GameRegistry.registerBlock(NetworkBlocks.monitor, prefix + "monitor");
        GameRegistry.registerTileEntity(TileEntityMonitor.class, prefix + "TileEntityMonitor");

        NetworkBlocks.computer = new BlockComputer().setBlockName(prefix + "computer").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:computer");
        GameRegistry.registerBlock(NetworkBlocks.computer, prefix + "computer");
        GameRegistry.registerTileEntity(TileEntityComputer.class, prefix + "TileEntityComputer");

//        NetworkBlocks.tabletCrafter = new BlockComputer().setBlockName(prefix + "tabletCrafter").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:tabletCrafter");
//        GameRegistry.registerBlock(NetworkBlocks.tabletCrafter, prefix + "tabletCrafter");

        NetworkBlocks.modem = new BlockModem().setBlockName(prefix + "modem").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:modem");
        GameRegistry.registerBlock(NetworkBlocks.modem, prefix + "modem");
        GameRegistry.registerTileEntity(TileEntityModem.class, prefix + "TileEntityModem");

        NetworkBlocks.lightPeripheral = new BlockLightPeripheral().setBlockName(prefix + "lightPeripheral").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:lightPeripheral");
        GameRegistry.registerBlock(NetworkBlocks.lightPeripheral, prefix + "lightPeripheral");
        GameRegistry.registerTileEntity(TileEntityLightPeripheral.class, prefix + "TileEntityLightPeripheral");

        NetworkBlocks.RTeleporter = new BlockTeleporter().setBlockName(prefix + "BlockTeleporter").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockTeleporter");
        GameRegistry.registerBlock(NetworkBlocks.RTeleporter, prefix + "ItemBlockRTeleporter");
        GameRegistry.registerTileEntity(TileEntityTeleporter.class, prefix + "TileEntityTeleporter");

        NetworkBlocks.BlockSolarPanel = new BlockSolarPanel().setBlockName(prefix + "BlockSolarPanel").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockSolarPanel");
        GameRegistry.registerBlock(NetworkBlocks.BlockSolarPanel, prefix + "BlockSolarPanel");
        GameRegistry.registerTileEntity(TileEntitySolarPanel.class, prefix + "TileEntitySolarPanel");


        NetworkBlocks.FluidGen = new BlockFluidGen().setBlockName(prefix + "BlockFluidGen").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:fluidgen/fluid");
        GameRegistry.registerBlock(NetworkBlocks.FluidGen, prefix + "BlockFluidGen");
        GameRegistry.registerTileEntity(TileEntityFluidGen.class, prefix + "TileEntityFluidGen");

//        NetworkBlocks.radioPeripheral = new BlockRadioPeripheral().setBlockName(prefix + "radioPeripheral").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:radioPeripheral");
//        GameRegistry.registerBlock(NetworkBlocks.radioPeripheral, prefix + "radioPeripheral");

        NetworkBlocks.networkPeripheral = new BlockNetworkPeripheral().setBlockName(prefix + "networkPeripheral").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:networkPeripheral");
        GameRegistry.registerBlock(NetworkBlocks.networkPeripheral, prefix + "networkPeripheral");
        GameRegistry.registerTileEntity(TileEntityNetworkPeripheral.class, prefix + "TileEntityNetworkPeripheral");

        NetworkBlocks.NetworkedFurnace = new BlockNetworkedFurnace().setBlockName(prefix + "BlockNetworkedFurnace").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockNetworkedFurnace");
        GameRegistry.registerBlock(NetworkBlocks.NetworkedFurnace, prefix + "NetworkedFurnace");
        GameRegistry.registerTileEntity(TileEntityNetworkedFurnace.class, prefix + "TileEntityNetworkedFurnace");

        NetworkBlocks.infusionFurnace = new BlockInfusionFurnace().setBlockName(prefix + "BlockInfusionFurnace").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockInfusionFurnace");
        GameRegistry.registerBlock(NetworkBlocks.infusionFurnace, prefix + "infusionFurnace");
        GameRegistry.registerTileEntity(TileEntityInfusionFurnace.class, prefix + "TileEntityInfusionFurnace");

        //TODO fix texture
        NetworkBlocks.BlockRemoteUser = new BlockRemoteUser().setBlockName(prefix + "BlockRemoteUser").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:base");
        GameRegistry.registerBlock(NetworkBlocks.BlockRemoteUser, prefix + "BlockRemoteUser");
        GameRegistry.registerTileEntity(TileEntityRemoteUser.class, prefix + "TileEntityRemoteUser");

        //TODO fix texture
        NetworkBlocks.CreativePower = new BlockCreativePower().setBlockName(prefix + "BlockCreativePower").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:base");
        GameRegistry.registerBlock(NetworkBlocks.CreativePower, prefix + "BlockCreativePower");
        GameRegistry.registerTileEntity(TileEntityCreativePower.class, prefix + "TileEntityCreativePower");

        //TODO fix texture
        NetworkBlocks.Pump = new BlockPump().setBlockName(prefix + "BlockPump").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:base");
        GameRegistry.registerBlock(NetworkBlocks.Pump, prefix + "Pump");
        GameRegistry.registerTileEntity(TileEntityPump.class, prefix + "TileEntityPump");

        NetworkBlocks.BlockMover = new BlockMover().setBlockName(prefix + "BlockMover").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockMover");
        GameRegistry.registerBlock(NetworkBlocks.BlockMover, prefix + "BlockMover");
        GameRegistry.registerTileEntity(TileEntityMover.class, prefix + "TileEntityMover");

    }

}
