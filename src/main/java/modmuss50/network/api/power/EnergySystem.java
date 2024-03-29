package modmuss50.network.api.power;


import cpw.mods.fml.common.Loader;
import modmuss50.network.api.WorldCoordinate;
import modmuss50.network.blocks.tileentities.TileEntityCable;
import modmuss50.network.compact.FMP.PartCable;
import modmuss50.network.netty.packets.PacketUpdateEnergySystem;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class EnergySystem {

	public int power;
	public int PowerStorageSize;
	public int powerInputSpeed = 10;
	public boolean canGivePower;
	public boolean canTakePower;

	public IEnergyFace lastFace;

	protected int ticks;

	public EnergySystem() {
		setPowerInputSpeed(100);
		setPowerStorageSize(10000);
		setCanGivePower(false);
		setCanTakePower(true);
	}

	public EnergySystem(int maxEnergyStored) {
		setPowerStorageSize(maxEnergyStored);
		setCanGivePower(false);
		setCanTakePower(true);
	}

	public EnergySystem(int maxEnergyStored, int powerInputSpeed) {
		setPowerStorageSize(maxEnergyStored);
		setPowerInputSpeed(powerInputSpeed);
		setCanGivePower(false);
		setCanTakePower(true);
	}

	public EnergySystem(int maxEnergyStored, int powerInputSpeed, boolean canGivePower, boolean canTakePower) {
		setPowerStorageSize(maxEnergyStored);
		setPowerInputSpeed(powerInputSpeed);
		setCanGivePower(canGivePower);
		setCanTakePower(canTakePower);
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getPowerStorageSize() {
		return PowerStorageSize;
	}

	public void setPowerStorageSize(int powerStorageSize) {
		PowerStorageSize = powerStorageSize;
	}

	public int getPowerInputSpeed() {
		return powerInputSpeed;
	}

	public void setPowerInputSpeed(int powerInputSpeed) {
		this.powerInputSpeed = powerInputSpeed;
	}

	public boolean getCanGivePower() {
		return canGivePower;
	}

	public void setCanGivePower(boolean canGivePower) {
		this.canGivePower = canGivePower;
	}

	public boolean getCanTakePower() {
		return canTakePower;
	}

	public void setCanTakePower(boolean canTakePower) {
		this.canTakePower = canTakePower;
	}

	public boolean tryInsertEnergy(int energy) {
		if (this.canFitPower(energy)) {
			this.setPower(getPower() + energy);
			return true;
		}
		return false;
	}

	public boolean tryInsertEnergyToSelf(int energy) {
		if (this.canFitPower(energy, true)) {
			this.setPower(getPower() + energy);
			return true;
		}
		return false;
	}

	//This checks if there is room and can insertpower remotely
	public boolean canInsertEnergy(int energy) {
		if (canTakePower && this.getPowerStorageSize() - this.getPower() <= energy) {
			return true;
		}
		return false;
	}

	//Checks if can insert energy to self
	public boolean canInsertEnergyToSelf(int energy) {
		if (this.getPowerStorageSize() - this.getPower() <= energy) {
			return true;
		}
		return false;
	}

	//Call this from a remote tile
	public boolean tryTakeEnergy(int ammout) {
		if (canGivePower && this.power >= ammout) {
			setPower(getPower() - ammout);
			return true;
		}
		return false;
	}


	//Call this from its own tile not from a remote tile. For example a mechine that takes power out of the buffer
    public boolean tryTakeEnergyFromSelf(int ammout) {
        if ( this.power >= ammout) {
            setPower(getPower() - ammout);
            return true;
        }
        return false;
    }

	public boolean tryRequestPower(IEnergyFace iEnergyFace) {
		if (iEnergyFace.ENERGY_SYSTEM().tryTakeEnergy(this.getPowerInputSpeed()) == true) {
			if (this.tryInsertEnergy(this.getPowerInputSpeed())) {
				return true;
			}
		}
		return false;
	}

	public boolean canFitPower(int power) {
		if(canTakePower){
			if (this.getPowerStorageSize() - this.getPower() >= power) {
				return true;
			}
		}
		return false;
	}

	public boolean canFitPower(int power, boolean canAdd) {
		if(canAdd){
			if (this.getPowerStorageSize() - this.getPower() >= power) {
				return true;
			}
		}
		return false;
	}

	//Call this when you need the power(try not to do it every tick)
	public void findAndRequestPower(World world, int xS, int yS, int zS) {
		List<WorldCoordinate> visited = new ArrayList<WorldCoordinate>();
		int cableMaxLenghth = 128;
		Queue<WorldCoordinate> queue = new PriorityQueue<WorldCoordinate>();
		WorldCoordinate start = new WorldCoordinate(xS, yS, zS, 0);
		queue.add(start);
		visited.add(start);

		while (!queue.isEmpty()) {
			WorldCoordinate element = queue.poll();

			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						if (Math.abs(x) + Math.abs(y) + Math.abs(z) == 1) {
							WorldCoordinate target = new WorldCoordinate(element.getX() + x, element.getY() + y, element.getZ() + z, element.getDepth() + 1);

							if (!visited.contains(target)) {
								visited.add(target);
								if (element.getDepth() < cableMaxLenghth) {
									Block block = world.getBlock(target.getX(), target.getY(), target.getZ());
									TileEntity tile = world.getTileEntity(target.getX(), target.getY(), target.getZ());
									int meta = world.getBlockMetadata(target.getX(), target.getY(), target.getZ());
									TileEntity tileEntity = world.getTileEntity(target.getX(), target.getY(), target.getZ());
									if (tileEntity != null && tileEntity instanceof IEnergyFace) {
										EnergySystem energySystem = ((IEnergyFace) tileEntity).ENERGY_SYSTEM();
										if (energySystem != null && energySystem != this && energySystem.getPower() >= this.powerInputSpeed) {
											if (this.tryRequestPower((IEnergyFace) tileEntity) == true) {
												//     System.out.println(getPower());
												this.lastFace = (IEnergyFace) tileEntity;
												return;
											}
										}

									} else if (isCable(tile) && target.getDepth() < cableMaxLenghth) {
										queue.add(target);
									}
								}
							}
						}
					}
				}
			}

		}
	}

	//TODO make this so the api will complile without the main bit of code
	public boolean isCable(TileEntity tile) {
		if (Loader.isModLoaded("ForgeMultipart")) {
			return PartCable.isCable(tile);
		}

		if (tile instanceof IEnergyFace)
			return true;

		return tile instanceof TileEntityCable;
	}


	public void tick(TileEntity tileEntity) {
		if (ticks != 120) {
			ticks += 1;
		} else {
			//Do this to make sure it will recheck the closest power supply
			this.lastFace = null;
			ticks = 0;
		}
		//This is so the game does not need to recheck the nearest power supply preventing lag.
		if (this.lastFace != null) {
			EnergySystem energySystem = this.lastFace.ENERGY_SYSTEM();
			if (energySystem != this && energySystem.getPower() >= this.powerInputSpeed) {
				if (this.tryRequestPower((IEnergyFace) tileEntity) == true) {
					PacketUpdateEnergySystem.sendPowerToAllClients(this, lastFace.getLocation());
					return;
				} else {
					this.lastFace = null;
				}
			} else {
				this.lastFace = null;
			}
		}

		if (lastFace == null) {
			this.findAndRequestPower(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
		}

	}

	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("power", this.getPower());
	}

	public void readFromNBT(NBTTagCompound tag) {
		this.setPower(tag.getInteger("power"));
	}
}
