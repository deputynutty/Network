package modmuss50.network.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import modmuss50.network.blocks.containers.*;
import modmuss50.network.blocks.tileentities.*;
import modmuss50.network.dataSystems.itemSystem.containers.ContainerStorageChest;
import modmuss50.network.dataSystems.itemSystem.gui.GuiStorageChest;
import modmuss50.network.dataSystems.itemSystem.tileEntitys.TileEntityBlockStorageContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	public static int tabletGuiID = 0;
	public static int lightPeripheralGuiID = 1;
	public static int robotGuiID = 2;
	public static int MonitorGuiID = 3;
	public static int PowerUserBaseID = 4;
	public static int FluidGenID = 5;
	public static int PowerSinkID = 6;
	public static int BcUserID = 7;
	public static int NetworkedFurnace = 8;
	public static int BlockMoverID = 9;
	public static int BlockRemoteUserID = 10;
	public static int TeleporterID = 11;
	public static int InfusedFurnace = 12;
	public static int computerGuiID = 13;
	public static int StorageChest = 14;
	public static int DroneID = 15;
	public static int pinPadID = 16;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);

		if (ID == MonitorGuiID) {
			return null;
		}
		if (ID == PowerUserBaseID) {
			return new modmuss50.network.blocks.containers.ContainerFluidGen(player.inventory, (TileEntityFluidGen) te);
		}

		// if (ID == BcUserID) {
		// return new ContainerBcUser(player.inventory,
		// (TileEntityBCUser) te);
		// }
		if (ID == NetworkedFurnace) {
			return new modmuss50.network.blocks.containers.ContainerNetworkedFurnace((TileEntityNetworkedFurnace) te, player);
		}

		if (ID == BlockMoverID) {
			return new ContinerBlockMover(player.inventory, (TileEntityMover) te);
		}

		if (ID == BlockRemoteUserID) {
			return new ContainerRemoteUser(player.inventory, (TileEntityRemoteUser) te);
		}
		if (ID == InfusedFurnace) {
			return new ContainerInfusionFurnace((TileEntityInfusionFurnace) te, player);
		}
		if (ID == computerGuiID) {
			return null;
		}
		if (ID == StorageChest) {
			if (te != null && te instanceof TileEntityBlockStorageContainer) {
				TileEntityBlockStorageContainer icte = (TileEntityBlockStorageContainer) te;
				return new ContainerStorageChest(player.inventory, icte, 0, 0);
			}
		}


		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (ID == tabletGuiID) {
			return new GuiTablet();
		} else if (ID == lightPeripheralGuiID) {
			if (te instanceof TileEntityLightPeripheral) {
				return new GuiLightPeripheral((TileEntityLightPeripheral) te);
			}
		} else
//				if (ID == robotGuiID) {
//					if (te instanceof TileEntityRobot) {
//						ArrayList<String> strings = new ArrayList<String>();
//						strings.add("Test instruction");
//						return new GuiRobot(new GuiModList(new GuiMainMenu()), strings, 30);
//					}
//				}

			//else
			if (ID == MonitorGuiID) {
				if (te instanceof TileEntityMonitor) {
					return new GuiMonitor((TileEntityMonitor) te);
				}
			} else if (ID == FluidGenID) {
				if (te instanceof TileEntityFluidGen) {
					return new GuiTank(player.inventory, (TileEntityFluidGen) te);
				}
			}

			// else if (ID == BcUserID) {
			// if (te instanceof TileEntityBCUser) {
			// return new GuiBcUser(player.inventory,
			// (TileEntityBCUser) te);
			// }
			// }

			else if (ID == NetworkedFurnace) {
				if (te instanceof TileEntityNetworkedFurnace) {
					return new GuiNetworkedFurnace(player, (TileEntityNetworkedFurnace) te);
				}
			} else if (ID == BlockMoverID) {
				if (te instanceof TileEntityMover) {
					return new GuiBlockMover(player.inventory, (TileEntityMover) te);
				}
			} else if (ID == BlockRemoteUserID) {
				if (te instanceof TileEntityRemoteUser) {
					return new GuiRemoteUser(player.inventory, (TileEntityRemoteUser) te);
				}
			} else if (ID == TeleporterID) {
				if (te instanceof TileEntityTeleporter) {
					return new GuiTeleporter((TileEntityTeleporter) te);
				}
			} else if (ID == InfusedFurnace) {
				if (te instanceof TileEntityInfusionFurnace) {
					return new GuiInfusionFurnace(player, (TileEntityInfusionFurnace) te);
				}
			} else if (ID == computerGuiID) {
				return new GuiComputer();
			} else if (ID == StorageChest) {
				return new GuiStorageChest(player.inventory, (TileEntityBlockStorageContainer) te);
			} else if (ID == DroneID) {
				return new GuiDrone(player, world, x, y, z);
			} else if (ID == pinPadID) {
				return new GuiPinPad(x, y, z, world, player);
			}

		return null;
	}

}
