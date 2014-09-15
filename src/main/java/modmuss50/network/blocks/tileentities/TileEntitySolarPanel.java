package modmuss50.network.blocks.tileentities;

import modmuss50.network.api.power.EnergySystem;
import modmuss50.network.api.power.IEnergyFace;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySolarPanel extends BaseTile implements IEnergyFace {

    EnergySystem energySystem;

    public TileEntitySolarPanel() {
        this.energySystem = new EnergySystem(10000, 0, true, false);
    }

    @Override
    public void updateEntity() {
        energySystem.tryInsertEnergy(1);
    }

    @Override
    public EnergySystem ENERGY_SYSTEM() {
        return energySystem;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        energySystem.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        energySystem.readFromNBT(tag);
    }
}
