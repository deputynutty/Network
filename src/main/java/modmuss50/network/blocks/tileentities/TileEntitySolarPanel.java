package modmuss50.network.blocks.tileentities;

import modmuss50.network.api.IPowedTileEntity;

public class TileEntitySolarPanel extends IPowedTileEntity {

    @Override
    public void doWorkTick() {
        super.doWorkTick();


        addPowerServer(1);
    }
}
