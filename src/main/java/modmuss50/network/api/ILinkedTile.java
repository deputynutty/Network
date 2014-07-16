package modmuss50.network.api;

import net.minecraft.tileentity.TileEntity;
import sourceteam.mods.lib.Location;

public interface ILinkedTile {

    public TileEntity[] conectableTiles();

    public boolean setLocation(Location loc);

    public Location getLocation();
}
