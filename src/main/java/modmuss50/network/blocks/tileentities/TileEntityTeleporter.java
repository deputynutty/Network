package modmuss50.network.blocks.tileentities;

import modmuss50.network.api.ILinkedTile;
import modmuss50.network.api.INetworkComponent;
import modmuss50.network.api.IPeripheral;
import net.minecraft.tileentity.TileEntity;
import sourceteam.mods.lib.Location;
import sourceteam.mods.lib.client.IColour;
import sourceteam.mods.lib.client.IRGBColour;

/**
 * Created by Mark on 19/04/14.
 */
public class TileEntityTeleporter extends TileEntityCable implements IPeripheral, INetworkComponent, IColour, IRGBColour, ILinkedTile {
	int			ticktime	= 0;
	boolean		goingdown	= false;

    int colour = 0;

    Location remoteTile;

	public TileEntityTeleporter() {

	}

	@Override
	public boolean canConnectViaWireless() {
		return false;
	}

    //red = 0
    //red + green = 1
    //green = 2
    //green + blue = 3
    //blue = 4
    //blue + red = 5


	@Override
	public void updateEntity() {
		super.updateEntity();
		updateBlock();

		if (ticktime >= 244) {
			goingdown = true;
		}
		else{
            if (ticktime == 0) {
                goingdown = false;
                if(colour ==  5){
                    colour = 0;
                } else {
                    colour += 1;
                }
            }
        }

		if (goingdown) {
			ticktime -= 1;
		}
		else {
			ticktime += 1;
		}

	}

	public void updateBlock() {

	}



	@Override
	public int colour() {
		return 0;
	}

	@Override
	public boolean isAnimated() {
		return true;
	}

	@Override
	public int Cred() {
        if(colour == 0 || colour == 1 || colour == 5){
            return ticktime;
        }
        return 0;
	}

	@Override
	public int Cgreen() {
        if(colour == 1 || colour == 2|| colour == 3){
            return ticktime;
        }
        return 0;
	}

	@Override
	public int Cblue() {
        if(colour == 3 || colour == 4 || colour == 5){
            return ticktime;
        }
        return 0;
	}

    @Override
    public TileEntity[] conectableTiles() {
        return new TileEntity[]{this};
    }

    @Override
    public boolean setLocation(Location loc) {
        remoteTile = loc;
        return true;
    }

    @Override
    public Location getLocation() {
        return remoteTile;
    }

}
