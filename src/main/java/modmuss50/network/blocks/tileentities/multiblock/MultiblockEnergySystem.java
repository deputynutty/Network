package modmuss50.network.blocks.tileentities.multiblock;

import modmuss50.mods.lib.multiblock.IMultiblockPart;
import modmuss50.mods.lib.multiblock.MultiblockControllerBase;
import modmuss50.network.api.power.EnergySystem;
import modmuss50.network.api.power.IEnergyFace;
import modmuss50.network.netty.packets.PacketUpdateEnergySystem;
import net.minecraft.tileentity.TileEntity;

//This is a custom EnergySystem for my multiblock's if you need your own system base off this, just try not to break things. :)
//This is not an api, if you need to use it for your add-on copy and paste it.

public class MultiblockEnergySystem extends EnergySystem {
	MultiblockControllerBase multiblockControllerBase;
	boolean defultcanGivePower, defultcanTakePower;

	public MultiblockEnergySystem(int maxEnergyStored, int powerInputSpeed, boolean canGivePower, boolean canTakePower, MultiblockControllerBase multiblockControllerBase) {
		super(maxEnergyStored, powerInputSpeed, canGivePower, canTakePower);
		this.multiblockControllerBase = multiblockControllerBase;
		defultcanGivePower = canGivePower;
		defultcanTakePower = canTakePower;
	}


	public void tick() {
		if (multiblockControllerBase.isAssembled()) {
			setCanGivePower(defultcanGivePower);
			setCanTakePower(defultcanTakePower);
		} else {
			setCanTakePower(false);
			setCanGivePower(false);
		}

		for (IMultiblockPart s : multiblockControllerBase.connectedParts) {
			pullpower(s.getWorldObj().getTileEntity(s.xCoord, s.yCoord, s.zCoord));
		}
	}


	public void pullpower(TileEntity tileEntity) {
		if(!canTakePower){
			return;
		}

		if (ticks != 120) {
			ticks += 1;
		} else {
			if(lastFace != null)
			PacketUpdateEnergySystem.sendPowerToAllClients(this, lastFace.getLocation());
			//Do this to make sure it will recheck the closest power supply
			this.lastFace = null;
			ticks = 0;
		}
		//This is so the game does not need to recheck the nearest power supply preventing lag.
		if (this.lastFace != null) {
			EnergySystem energySystem = this.lastFace.ENERGY_SYSTEM();
			if (energySystem != this && energySystem.getPower() >= this.powerInputSpeed) {
				if (this.tryRequestPower((IEnergyFace) tileEntity) == true) {
					PacketUpdateEnergySystem.sendPowerToAllClients(this, lastFace.getLocation());
					return;
				} else {
					this.lastFace = null;
				}
			} else {
				this.lastFace = null;
			}
		}

		if (lastFace == null) {
			this.findAndRequestPower(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
		}

	}

	public boolean tryTakeEnergy(int ammout) {
		if(multiblockControllerBase.isAssembled()){
			if (canGivePower && this.power >= ammout) {
				setPower(getPower() - ammout);
				return true;
			}
		}
		return false;
	}

	@Override
	public void tick(TileEntity tileEntity) {

	}


	public void checkPower(){
		if(this.getPower() >= this.getPowerStorageSize()){
			this.setPower(this.getPowerStorageSize());
		}
	}


}
