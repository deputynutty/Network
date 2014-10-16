package modmuss50.network.blocks.tileentities;

import modmuss50.mods.lib.Location;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class MovingBlock {

	public Block block;
	public int meta;
	public NBTTagCompound nbt;
	public Location origin;
	public Location destiantion;
	public ForgeDirection dir;

	public MovingBlock(Block block, int meta, NBTTagCompound nbt, Location origin, Location destiantion, ForgeDirection direction) {
		this.block = block;
		this.meta = meta;
		this.nbt = nbt;
		this.origin = origin;
		this.destiantion = destiantion;
		this.dir = direction;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public int getMeta() {
		return meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public NBTTagCompound getNbt() {
		return nbt;
	}

	public void setNbt(NBTTagCompound nbt) {
		this.nbt = nbt;
	}

	public Location getOrigin() {
		return origin;
	}

	public void setOrigin(Location origin) {
		this.origin = origin;
	}

	public Location getDestiantion() {
		return destiantion;
	}

	public void setDestiantion(Location destiantion) {
		this.destiantion = destiantion;
	}

	public ForgeDirection getDir() {
		return dir;
	}

	public void setDir(ForgeDirection dir) {
		this.dir = dir;
	}
}
