package modmuss50.network.loaders;

import cpw.mods.fml.common.registry.GameRegistry;
import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.*;
import modmuss50.network.blocks.Rails.RailBase;
import modmuss50.network.blocks.tileentities.*;

public class LoadBlocks {
	public static void loadBlocks() {
		NetworkBlocks.networkRail = new RailBase().setBlockName("networkRail").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:networkRail");
		GameRegistry.registerBlock(NetworkBlocks.networkRail, "NetworkRail");

		NetworkBlocks.ItemConveyor = new ItemConveyor().setBlockName("ItemConveyor").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:ItemConveyor");
		GameRegistry.registerBlock(NetworkBlocks.ItemConveyor, "ItemConveyor");

		NetworkBlocks.BouncyItemConveyor = new BouncyItemConveyor().setBlockName("BouncyItemConveyor").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BouncyItemConveyor");
		GameRegistry.registerBlock(NetworkBlocks.BouncyItemConveyor, "BouncyItemConveyor");

		NetworkBlocks.server = new BlockServer().setBlockName("server").setHardness(1.5F).setCreativeTab(NetworkCore.Network);
		GameRegistry.registerBlock(NetworkBlocks.server, "server");
		GameRegistry.registerTileEntity(TileEntityServer.class, "TileEntityServer");

		NetworkBlocks.networkCable = new BlockNetworkCable().setBlockName("networkCable").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:networkCable");
		GameRegistry.registerBlock(NetworkBlocks.networkCable, "networkCable");
		GameRegistry.registerTileEntity(TileEntityCable.class, "TileEntityCable");
		GameRegistry.registerTileEntity(TileEntityPowerImputCable.class, "TileEntityPowerImputCable");

		NetworkBlocks.monitor = new BlockMonitor().setBlockName("monitor").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:monitor");
		GameRegistry.registerBlock(NetworkBlocks.monitor, "monitor");

		GameRegistry.registerTileEntity(TileEntityMonitor.class, "TileEntityMonitor");

		NetworkBlocks.computer = new BlockComputer().setBlockName("computer").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:computer");
		GameRegistry.registerBlock(NetworkBlocks.computer, "computer");
		GameRegistry.registerTileEntity(TileEntityComputer.class, "TileEntityComputer");

		NetworkBlocks.tabletCrafter = new BlockComputer().setBlockName("tabletCrafter").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:tabletCrafter");
		GameRegistry.registerBlock(NetworkBlocks.tabletCrafter, "tabletCrafter");

		NetworkBlocks.modem = new BlockModem().setBlockName("modem").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:modem");
		GameRegistry.registerBlock(NetworkBlocks.modem, "modem");
		GameRegistry.registerTileEntity(TileEntityModem.class, "TileEntityModem");

		NetworkBlocks.lightPeripheral = new BlockLightPeripheral().setBlockName("lightPeripheral").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:lightPeripheral");
		GameRegistry.registerBlock(NetworkBlocks.lightPeripheral, "lightPeripheral");
		GameRegistry.registerTileEntity(TileEntityLightPeripheral.class, "TileEntityLightPeripheral");

		NetworkBlocks.RTeleporter = new BlockTeleporter().setBlockName("BlockTeleporter").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockTeleporter");
		GameRegistry.registerBlock(NetworkBlocks.RTeleporter, ItemBlockRTeleporter.class, "ItemBlockRTeleporter");
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "TileEntityTeleporter");

		NetworkBlocks.BlockSolarPanel = new BlockSolarPanel().setBlockName("BlockSolarPanel").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockSolarPanel");
		GameRegistry.registerBlock(NetworkBlocks.BlockSolarPanel, "BlockSolarPanel");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "TileEntitySolarPanel");

		NetworkBlocks.Bcuser = new BlockBcUser().setBlockName("BlockBcUser").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockBcUser");
		GameRegistry.registerBlock(NetworkBlocks.Bcuser, "BlockBcUser");
		GameRegistry.registerTileEntity(TileEntityBCUser.class, "TileEntityBCUser");

		NetworkBlocks.BlockPowerUserBase = new BlockPowerUserBase().setBlockName("BlockPowerUserBase").setHardness(1.5F).setBlockTextureName("network:BlockPowerUserBase");
		GameRegistry.registerBlock(NetworkBlocks.BlockPowerUserBase, "BlockPowerUserBase");
		GameRegistry.registerTileEntity(TileEntityPowerUserBase.class, "TileEntityPowerUserBase");

		NetworkBlocks.FluidGen = new BlockFluidGen().setBlockName("BlockFluidGen").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:fluidgen/fluid");
		GameRegistry.registerBlock(NetworkBlocks.FluidGen, "BlockFluidGen");
		GameRegistry.registerTileEntity(TileEntityFluidGen.class, "TileEntityFluidGen");

		NetworkBlocks.radioPeripheral = new BlockRadioPeripheral().setBlockName("radioPeripheral").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:radioPeripheral");
		GameRegistry.registerBlock(NetworkBlocks.radioPeripheral, "radioPeripheral");

		NetworkBlocks.networkPeripheral = new BlockNetworkPeripheral().setBlockName("networkPeripheral").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:networkPeripheral");
		GameRegistry.registerBlock(NetworkBlocks.networkPeripheral, "networkPeripheral");
		GameRegistry.registerTileEntity(TileEntityNetworkPeripheral.class, "TileEntityNetworkPeripheral");

		NetworkBlocks.NetworkedFurnace = new BlockNetworkedFurnace().setBlockName("BlockNetworkedFurnace").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockNetworkedFurnace");
		GameRegistry.registerBlock(NetworkBlocks.NetworkedFurnace, "NetworkedFurnace");
		GameRegistry.registerTileEntity(TileEntityNetworkedFurnace.class, "TileEntityNetworkedFurnace");

		NetworkBlocks.BlockRemoteUser = new BlockRemoteUser().setBlockName("BlockRemoteUser").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockRemoteUser");
		GameRegistry.registerBlock(NetworkBlocks.BlockRemoteUser, "BlockRemoteUser");
		GameRegistry.registerTileEntity(TileEntityRemoteUser.class, "TileEntityRemoteUser");

		NetworkBlocks.CreativePower = new BlockCreativePower().setBlockName("BlockCreativePower").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockCreativePower");
		GameRegistry.registerBlock(NetworkBlocks.CreativePower, "BlockCreativePower");
		GameRegistry.registerTileEntity(TileEntityCreativePower.class, "TileEntityCreativePower");

		NetworkBlocks.BlockMover = new BlockMover().setBlockName("BlockMover").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:BlockMover");
		GameRegistry.registerBlock(NetworkBlocks.BlockMover, "BlockMover");
		GameRegistry.registerTileEntity(TileEntityMover.class, "TileEntityMover");

		NetworkBlocks.BlockMoving = new BlockMovingBlock().setBlockName("BlockMovingBlock").setHardness(10000F).setBlockUnbreakable().setResistance(100000F).setBlockTextureName("network:BlockMovingBlock");
		GameRegistry.registerBlock(NetworkBlocks.BlockMoving, "BlockMovingBlock");
		GameRegistry.registerTileEntity(TileEntityMovingBlock.class, "TileEntityMovingBlock");

		NetworkBlocks.robotMK1 = new BlockRobot(1).setBlockName("robotMK1").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:robotMK1");
		GameRegistry.registerBlock(NetworkBlocks.robotMK1, "robotMK1");
		GameRegistry.registerTileEntity(TileEntityRobot.class, "TileEntityRobotMK1");

		NetworkBlocks.robotMK2 = new BlockRobot(2).setBlockName("robotMK2").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:robotMK2");
		GameRegistry.registerBlock(NetworkBlocks.robotMK2, "robotMK2");
		GameRegistry.registerTileEntity(TileEntityRobot.class, "TileEntityRobotMK2");

		NetworkBlocks.robotMK3 = new BlockRobot(3).setBlockName("robotMK3").setHardness(1.5F).setCreativeTab(NetworkCore.Network).setBlockTextureName("network:robotMK3");
		GameRegistry.registerBlock(NetworkBlocks.robotMK3, "robotMK3");
		GameRegistry.registerTileEntity(TileEntityRobot.class, "TileEntityRobotMK3");

	}

}
