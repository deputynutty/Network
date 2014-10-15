package modmuss50.network.blocks.tileentities;

import modmuss50.network.api.power.EnergySystem;
import modmuss50.network.api.power.IEnergyFace;
import modmuss50.mods.lib.Location;

public class TileEntityCreativePower extends BaseTile implements IEnergyFace {

	EnergySystem energySystem = new EnergySystem(100000, 10000, true, false);

	@Override
	public void updateEntity() {
		super.updateEntity();
		energySystem.setPower(energySystem.getPowerStorageSize());
	}

	@Override
	public EnergySystem ENERGY_SYSTEM() {
		return energySystem;
	}

	@Override
	public Location getLocation() {
		return new Location(this.xCoord, this.yCoord, this.zCoord);
	}
}
