package modmuss50.network.blocks.tileentities.multiblock;

import modmuss50.mods.lib.Location;
import modmuss50.mods.lib.multiblock.MultiblockControllerBase;
import modmuss50.mods.lib.multiblock.MultiblockValidationException;
import modmuss50.mods.lib.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import modmuss50.network.api.power.EnergySystem;
import modmuss50.network.api.power.IEnergyFace;


public class TileEntityMultiblockPower extends RectangularMultiblockTileEntityBase implements IEnergyFace {


	@Override
	public void onMachineAssembled(MultiblockControllerBase multiblockControllerBase) {
		super.onMachineAssembled(multiblockControllerBase);
	}

	@Override
	public void onMachineBroken() {
		super.onMachineBroken();
	}

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {

	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {

	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {

	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {

	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {

	}

	@Override
	public void onMachineActivated() {

	}

	@Override
	public void onMachineDeactivated() {

	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new MultiBlockPowerSystem(worldObj);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return MultiBlockPowerSystem.class;
	}


	@Override
	public EnergySystem ENERGY_SYSTEM() {
		if (!isConnected()) {
			return null;
		}
		return getMultiblockControllerPower().getEnergySystem();
	}

	@Override
	public Location getLocation() {
		return new Location(xCoord, yCoord, zCoord);
	}


	public MultiBlockPowerSystem getMultiblockControllerPower() {
		return (MultiBlockPowerSystem) super.getMultiblockController();
	}
}
