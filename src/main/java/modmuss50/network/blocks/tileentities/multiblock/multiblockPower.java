package modmuss50.network.blocks.tileentities.multiblock;

import modmuss50.mods.lib.multiblock.IMultiblockPart;
import modmuss50.mods.lib.multiblock.MultiblockControllerBase;
import modmuss50.mods.lib.multiblock.rectangular.RectangularMultiblockControllerBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MultiBlockPower extends RectangularMultiblockControllerBase {
	public MultiblockEnergySystem energySystem = new MultiblockEnergySystem(100000, 10000, true, true, this);

	public MultiBlockPower(World world) {
		super(world);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {

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

	}

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

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {

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

	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {

	}

	public MultiblockEnergySystem getEnergySystem() {
		return energySystem;
	}
}
