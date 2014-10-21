package modmuss50.network.blocks.tileentities.multiblock;

import modmuss50.mods.lib.multiblock.IMultiblockPart;
import modmuss50.mods.lib.multiblock.MultiblockControllerBase;
import modmuss50.mods.lib.multiblock.rectangular.RectangularMultiblockControllerBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MultiBlockPowerSystem extends RectangularMultiblockControllerBase {
	public MultiblockEnergySystem energySystem = new MultiblockEnergySystem(100000, 10000, true, true, this);

	public MultiBlockPowerSystem(World world) {
		super(world);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		this.readFromNBT(data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		getEnergySystem().setPowerStorageSize(this.getNumConnectedBlocks() * 10000);
		getEnergySystem().checkPower();
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		getEnergySystem().checkPower();
	}

	@Override
	protected void onMachineAssembled() {
		getEnergySystem().setPowerStorageSize(this.getNumConnectedBlocks() * 10000);
		getEnergySystem().checkPower();
	}

	@Override
	protected void onMachineRestored() {
		getEnergySystem().setPowerStorageSize(this.getNumConnectedBlocks() * 10000);
		getEnergySystem().checkPower();
	}

	@Override
	protected void onMachinePaused() {

	}

	@Override
	protected void onMachineDisassembled() {
		getEnergySystem().checkPower();
	}

	//This lets us use only 1 block for the power storage
	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 1;
	}

	@Override
	protected int getMaximumXSize() {
		return 15;
	}

	@Override
	protected int getMaximumZSize() {
		return 15;
	}

	@Override
	protected int getMaximumYSize() {
		return 15;
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		getEnergySystem().power = 0;
	}

	//This lets us copy the power from the old master block.
	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		if(assimilator instanceof MultiBlockPowerSystem){
			MultiBlockPowerSystem power = (MultiBlockPowerSystem) assimilator;
			getEnergySystem().power = power.getEnergySystem().getPower();
		}

	}

	@Override
	protected boolean updateServer() {
		energySystem.tick();
		return true;
	}

	@Override
	protected void updateClient() {
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		getEnergySystem().writeToNBT(data);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		getEnergySystem().readFromNBT(data);
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound data) {
		writeToNBT(data);
	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {
		readFromNBT(data);
	}

	public MultiblockEnergySystem getEnergySystem() {
		return energySystem;
	}
}
