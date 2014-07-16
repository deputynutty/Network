package modmuss50.network.blocks.tileentities;

import modmuss50.network.api.ILinkedTile;
import modmuss50.network.api.INetworkComponent;
import net.minecraft.tileentity.TileEntity;
import sourceteam.mods.lib.Location;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 25/02/14 Time: 13:11
 */
public class TileEntityModem extends BaseTile implements INetworkComponent, ILinkedTile {

    @Override
    public TileEntity[] conectableTiles() {
        return new TileEntity[]{this, new TileEntityNetworkPeripheral()};
    }

    @Override
    public boolean setLocation(Location loc) {
        return false;
    }

    @Override
    public Location getLocation() {
        return null;
    }
}
