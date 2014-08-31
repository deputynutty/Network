package sourceteam.network.blocks.tileentities;

import sourceteam.network.api.IPowedTileEntity;

public class TileEntitySolarPanel extends IPowedTileEntity {

    @Override
    public void doWorkTick() {
        super.doWorkTick();


        addPowerServer(1);
    }
}
