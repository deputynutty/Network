package modmuss50.network.api;

import modmuss50.network.blocks.tileentities.TileEntityCable;
import modmuss50.network.blocks.tileentities.TileEntityServer;
import net.minecraft.world.World;

public class IPowedTileEntity extends TileEntityCable {

	public void updateEntity() {
		super.updateEntity();

		doWorkTick();
	}

	public void doWorkTick() {

	}

	public int Getpower() {

		World world = this.worldObj;
		TileEntityServer server;

		if (world.getTileEntity(getSerX(), getSerY(), getSerZ()) instanceof TileEntityServer) {
			server = (TileEntityServer) world.getTileEntity(getSerX(), getSerY(), getSerZ());
			if (server.HasNetU()) {
				return server.getNetworkNetUnits();
			}
			else {
				return 0;
			}
		}
		else {
			return 0;
		}

	}

	public boolean hasConectionServer() {
		World world = this.worldObj;
		if (world.getTileEntity(getSerX(), getSerY(), getSerZ()) instanceof TileEntityServer) {
			return true;
		}
		return false;
	}

	public boolean addPowerServer(int i) {

		World world = this.worldObj;
		TileEntityServer server;

		if (world.getTileEntity(getSerX(), getSerY(), getSerZ()) instanceof TileEntityServer) {
			server = (TileEntityServer) world.getTileEntity(getSerX(), getSerY(), getSerZ());
			return server.AddNetU(i);
		}
		else {
			return false;
		}

	}

	public void fillPowerServer() {

		World world = this.worldObj;
		TileEntityServer server;

		if (world.getTileEntity(getSerX(), getSerY(), getSerZ()) instanceof TileEntityServer) {
			server = (TileEntityServer) world.getTileEntity(getSerX(), getSerY(), getSerZ());
			server.fillpower();
		}

	}

	public boolean removePowerServer(int i) {

		World world = this.worldObj;
		TileEntityServer server;

		if (world.getTileEntity(getSerX(), getSerY(), getSerZ()) instanceof TileEntityServer) {
			server = (TileEntityServer) world.getTileEntity(getSerX(), getSerY(), getSerZ());
			if (server.getNetworkNetUnits() >= i) {
				server.RemoveNetU(i);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}

	}

	public boolean hasServer() {
		World world = this.worldObj;
		if (world.getTileEntity(getSerX(), getSerY(), getSerZ()) instanceof TileEntityServer) {
			return true;
		}
		else {
			return false;
		}
	}

}
