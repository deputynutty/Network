package modmuss50.network.blocks.tileentities;

import cpw.mods.fml.common.Loader;
import modmuss50.network.api.power.EnergySystem;
import modmuss50.network.api.power.IEnergyFace;
import modmuss50.network.compact.FMP.PartCable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourceteam.mods.lib.BlockHelper;

/**
 * Created by Mark on 21/02/14.
 */
public class TileEntityCable extends BaseTile implements IEnergyFace {

	// used mainly for rendering
	public boolean top, bottom, back, forward, left, right;
	public boolean canConnecttop, canConnectbottom, canConnectback, canConnectforward, canConnectleft, canConnectright;
	public boolean doupdate = true;
	public World TeWorld;
	public byte[] sideCache = {0, 0, 0, 0, 0, 0};
	public int BlockX, BlockY, BlockZ;
	public World World;

	public TileEntityCable() {
		// setConectionSides(top,bottom,back,forward,left,right);
		setConectionSides(true, true, true, true, true, true);
		TeWorld = worldObj;

	}

	public void updateEntity() {
		super.updateEntity();


		TileEntity curTile = null;

		this.CalculateCables();

		for (int i = 0; i < 6; i++) {
			curTile = BlockHelper.getAdjacentTileEntity(this, i);

			if (curTile == null) {
				this.sideCache[i] = 0;
			} else if (curTile instanceof TileEntityCable) {
				this.sideCache[i] = 1;
			} else {
				this.sideCache[i] = 0;
			}
		}
	}

	public void cacheTile(TileEntity theTile, int side) {
	}

	public void CalculateCables() {
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		World world = this.worldObj;
		TileEntityCable te;


		if (world.getTileEntity(x, y, z) instanceof TileEntityCable) {
			// forward
			if (this.canConnectforward) {
				if (isCable(worldObj.getTileEntity(xCoord + 1, yCoord, zCoord))) {
					forward = false;
				} else {
					forward = true;
				}
			} else {
				forward = true;
			}

			// back
			if (this.canConnectback) {
				if (isCable(worldObj.getTileEntity(xCoord - 1, yCoord, zCoord))) {
					back = false;
				} else {
					back = true;
				}
			} else {
				back = true;
			}

			// right
			if (this.canConnectright) {
				if (isCable(worldObj.getTileEntity(xCoord, yCoord, zCoord + 1))) {
					right = false;
				} else {
					right = true;
				}

			} else {
				right = true;
			}

			// left
			if (this.canConnectleft) {
				if (isCable(worldObj.getTileEntity(xCoord, yCoord, zCoord - 1))) {
					left = false;
				} else {
					left = true;
				}
			} else {
				left = true;
			}

			// top
			if (this.canConnecttop) {
				if (isCable(worldObj.getTileEntity(xCoord, yCoord + 1, zCoord))) {
					top = false;
				} else {
					top = true;
				}

			} else {
				top = true;
			}

			// bottom
			if (this.canConnectbottom) {
				if (worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof IEnergyFace) {
					bottom = false;
				} else {
					bottom = true;
				}
			}
		} else {
			bottom = true;
		}

	}


	public void setConectionSides(boolean topS, boolean bottomS, boolean backS, boolean forwardS, boolean leftS, boolean rightS) {
		canConnecttop = topS;
		canConnectbottom = bottomS;
		canConnectback = backS;
		canConnectforward = forwardS;
		canConnectleft = leftS;
		canConnectright = rightS;
	}


	public boolean isCable(TileEntity tile) {
		if (Loader.isModLoaded("ForgeMultipart")) {
			return PartCable.isCable(tile);
		}

		if (tile instanceof IEnergyFace)
			return true;

		return tile instanceof TileEntityCable;
	}

	@Override
	public EnergySystem ENERGY_SYSTEM() {
		return null;
	}
}
