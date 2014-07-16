package modmuss50.network.Fmp;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourceteam.mods.lib.Location;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import cpw.mods.fml.common.registry.GameRegistry;

public class Multipart implements MultiPartRegistry.IPartFactory, MultiPartRegistry.IPartConverter {

	public static ItemPartCable	cablepartitem;
	public static ItemPipeLine	itemPipeLine;

	public static final String	codename		= "tile.network.cable";

	public static final String	Pipecodename	= "tile.network.pipeLine";

	public static void init() {

		MultiPartRegistry.registerParts(new Multipart(), new String[] { codename });
		MultiPartRegistry.registerParts(new Multipart(), new String[] { Pipecodename });

		cablepartitem = new ItemPartCable();
		itemPipeLine = new ItemPipeLine();

		GameRegistry.registerItem(itemPipeLine, Pipecodename);
		GameRegistry.registerItem(cablepartitem, codename);

	}

	@Override
	public TMultiPart createPart(String id, boolean client) {
		if (id.equals(codename)) {
			return new PartCable();
		}
		else
			if (id.equals(Pipecodename)) {
				return new PartPipeLine();
			}
		System.out.println("There was an error!");
		return null;
	}

	public static TileMultipart getMultipartTile(IBlockAccess access, Location pos) {
		TileEntity te = access.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
		return te instanceof TileMultipart ? (TileMultipart) te : null;
	}

	public static TMultiPart getMultiPart(IBlockAccess w, Location bc, int part) {
		TileMultipart t = getMultipartTile(w, bc);
		if (t != null)
			return t.partMap(part);

		return null;
	}

	public static boolean hasPartCable(TileMultipart mp) {
		boolean ret = false;
		List<TMultiPart> t = mp.jPartList();
		for (TMultiPart p : t) {
			if (ret == false) {
				if (p instanceof PartCable) {
					ret = true;
				}
			}
		}
		return ret;
	}

	public static PartCable getCable(TileMultipart mp) {
		boolean ret = false;
		List<TMultiPart> t = mp.jPartList();
		for (TMultiPart p : t) {
			if (ret == false) {
				if (p instanceof PartCable) {
					return (PartCable) p;
				}
			}
		}
		return null;
	}

	public static boolean hasPartPipe(TileMultipart mp) {
		boolean ret = false;
		List<TMultiPart> t = mp.jPartList();
		for (TMultiPart p : t) {
			if (ret == false) {
				if (p instanceof PartPipeLine) {
					ret = true;
				}
			}
		}
		return ret;
	}

	public static boolean hasPartPipe(TileEntity mp) {
		if (mp instanceof TileMultipart) {
			TileMultipart Tmp = (TileMultipart) mp;
			boolean ret = false;
			List<TMultiPart> t = Tmp.jPartList();
			for (TMultiPart p : t) {
				if (ret == false) {
					if (p instanceof PartPipeLine) {
						ret = true;
					}
				}
			}
			return ret;
		}

		return false;

	}

	public static PartPipeLine getPipe(TileMultipart mp) {
		boolean ret = false;
		List<TMultiPart> t = mp.jPartList();
		for (TMultiPart p : t) {
			if (ret == false) {
				if (p instanceof PartPipeLine) {
					return (PartPipeLine) p;
				}
			}
		}
		return null;
	}

	public static PartPipeLine getPipe(TileEntity mp) {
		if (mp instanceof TileMultipart) {
			TileMultipart Tmp = (TileMultipart) mp;
			boolean ret = false;
			List<TMultiPart> t = Tmp.jPartList();
			for (TMultiPart p : t) {
				if (ret == false) {
					if (p instanceof PartPipeLine) {
						return (PartPipeLine) p;
					}
				}
			}
			return null;
		}
		return null;
	}

	@Override
	public Iterable<Block> blockTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TMultiPart convert(World arg0, BlockCoord arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
