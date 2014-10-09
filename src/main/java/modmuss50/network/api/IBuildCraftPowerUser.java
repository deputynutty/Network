package modmuss50.network.api;

import net.minecraft.tileentity.TileEntity;

public class IBuildCraftPowerUser extends TileEntity {// extends TileBuildCraft
	// implements IMachine,
	// IPowerReceptor {

	// public PowerHandler powerHandler;
	// public int requiredPowerToTick = 25;
	// public int maxStoredEnergy = 1000;
	//
	//
	// public IBuildCraftPowerUser() {
	// powerHandler = new PowerHandler(this, PowerHandler.Type.MACHINE);
	// powerHandler.configure(5, 10, requiredPowerToTick, maxStoredEnergy);
	// powerHandler.configurePowerPerdition(1, 1);
	// }
	//
	// public int BcCurrentPower()
	// {
	// return (int) powerHandler.getEnergyStored();
	// }
	//
	//
	// public int PowerStorageSize = 10000;
	// public int currentPower = 0;
	// public int powerimputspeed = 5;
	//
	// public int getCurrentPower() {
	// return currentPower;
	// }
	//
	// public int getPowerStorageSize() {
	// return PowerStorageSize;
	// }
	//
	// public boolean removePowerFromBase(int i) {
	// if (i >= this.currentPower) {
	// this.currentPower -= i;
	// return true;
	// } else {
	// return false;
	// }
	// }
	//
	// @Override
	// public void updateEntity() {
	// if (this instanceof IPowerReceptor) {
	// IPowerReceptor receptor = ((IPowerReceptor) this);
	// receptor.getPowerReceiver(null).update();
	// }
	// super.updateEntity();
	// }
	//
	// @Override
	// public boolean isActive() {
	// return true;
	// }
	//
	// @Override
	// public boolean manageFluids() {
	// return false;
	// }
	//
	// @Override
	// public boolean manageSolids() {
	// return false;
	// }
	//
	// @Override
	// public boolean allowAction(IAction action) {
	// return false;
	// }
	//
	// @Override
	// public PowerHandler.PowerReceiver getPowerReceiver(ForgeDirection side) {
	// return powerHandler.getPowerReceiver();
	// }
	//
	// @Override
	// public void doWork(PowerHandler workProvider) {
	// System.out.println("hi2");
	//
	// float mj = requiredPowerToTick;
	// if (powerHandler.useEnergy(mj, mj, true) != mj)
	// return;
	// doPoweredWorkTick(workProvider);
	// }
	//
	// public void doPoweredWorkTick(PowerHandler workProvider) {
	//
	// }
	//
	// @Override
	// public World getWorld() {
	// return this.worldObj;
	// }
}
