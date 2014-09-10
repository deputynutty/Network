package modmuss50.network.blocks.tileentities;

public class TileEntityCreativePower extends TileEntityPowerUserBase {

    @Override
    public void updateEntity() {
        super.updateEntity();
        fillPowerServer();
    }
}
