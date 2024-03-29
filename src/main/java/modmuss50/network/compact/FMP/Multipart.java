package modmuss50.network.compact.FMP;

import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import cpw.mods.fml.common.registry.GameRegistry;
import modmuss50.mods.lib.Location;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class Multipart implements MultiPartRegistry.IPartFactory, MultiPartRegistry.IPartConverter {

	public static final String codename = "tile.network.cable";
	public static final String wireName = "tile.network.wire";
	public static final String wireNfcName = "tile.network.wireNFC";
	public static ItemPartCable cablepartitem;
	public static ItemPartWire itemPartWire;
	public static ItemPartWireNFC itemPartWireNFC;

	public static void init() {

		MultiPartRegistry.registerParts(new Multipart(), new String[]{codename});
		MultiPartRegistry.registerParts(new Multipart(), new String[]{wireName});
		MultiPartRegistry.registerParts(new Multipart(), new String[]{wireNfcName});

		cablepartitem = new ItemPartCable();
		itemPartWire = new ItemPartWire();
		itemPartWireNFC = new ItemPartWireNFC();

		GameRegistry.registerItem(cablepartitem, codename);
		GameRegistry.registerItem(itemPartWire, wireName);
		GameRegistry.registerItem(itemPartWireNFC, wireNfcName);
	}

	//TODO make this cleaner might move into the part class so this is messy

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


	public static PartCable getCable(TileEntity tile) {
		boolean ret = false;
		if (tile instanceof TileMultipart) {
			TileMultipart mp = (TileMultipart) tile;
			List<TMultiPart> t = mp.jPartList();
			for (TMultiPart p : t) {
				if (ret == false) {
					if (p instanceof PartCable) {
						return (PartCable) p;
					}
				}
			}
		}

		return null;
	}

	public static boolean hasPartWire(TileMultipart mp) {
		boolean ret = false;
		List<TMultiPart> t = mp.jPartList();
		for (TMultiPart p : t) {
			if (ret == false) {
				if (p instanceof PartWire) {
					ret = true;
				}
			}
		}
		return ret;
	}

	public static boolean hasPartWire(TileEntity mp) {
		if (mp instanceof TileMultipart) {
			TileMultipart Tmp = (TileMultipart) mp;
			boolean ret = false;
			List<TMultiPart> t = Tmp.jPartList();
			for (TMultiPart p : t) {
				if (ret == false) {
					if (p instanceof PartWire) {
						ret = true;
					}
				}
			}
			return ret;
		}

		return false;

	}

	public static PartWire getwire(TileMultipart mp) {
		boolean ret = false;
		List<TMultiPart> t = mp.jPartList();
		for (TMultiPart p : t) {
			if (ret == false) {
				if (p instanceof PartWire) {
					return (PartWire) p;
				}
			}
		}
		return null;
	}

	public static PartWire getWire(TileEntity mp) {
		if (mp instanceof TileMultipart) {
			TileMultipart Tmp = (TileMultipart) mp;
			boolean ret = false;
			List<TMultiPart> t = Tmp.jPartList();
			for (TMultiPart p : t) {
				if (ret == false) {
					if (p instanceof PartWire) {
						return (PartWire) p;
					}
				}
			}
			return null;
		}
		return null;
	}


	public static boolean hasPartWireNFC(TileMultipart mp) {
		boolean ret = false;
		List<TMultiPart> t = mp.jPartList();
		for (TMultiPart p : t) {
			if (ret == false) {
				if (p instanceof PartWireNFC) {
					ret = true;
				}
			}
		}
		return ret;
	}

	public static boolean hasPartWireNFC(TileEntity mp) {
		if (mp instanceof TileMultipart) {
			TileMultipart Tmp = (TileMultipart) mp;
			boolean ret = false;
			List<TMultiPart> t = Tmp.jPartList();
			for (TMultiPart p : t) {
				if (ret == false) {
					if (p instanceof PartWireNFC) {
						ret = true;
					}
				}
			}
			return ret;
		}

		return false;

	}

	public static PartWireNFC getWireNFC(TileMultipart mp) {
		boolean ret = false;
		List<TMultiPart> t = mp.jPartList();
		for (TMultiPart p : t) {
			if (ret == false) {
				if (p instanceof PartWireNFC) {
					return (PartWireNFC) p;
				}
			}
		}
		return null;
	}

	public static PartWireNFC getWireNFC(TileEntity mp) {
		if (mp instanceof TileMultipart) {
			TileMultipart Tmp = (TileMultipart) mp;
			boolean ret = false;
			List<TMultiPart> t = Tmp.jPartList();
			for (TMultiPart p : t) {
				if (ret == false) {
					if (p instanceof PartWireNFC) {
						return (PartWireNFC) p;
					}
				}
			}
			return null;
		}
		return null;
	}

	@Override
	public TMultiPart createPart(String id, boolean client) {
		if (id.equals(codename)) {
			return new PartCable();
		} else if (id.equals(wireName)) {
			return new PartWire();
		} else if (id.equals(wireNfcName)) {
			return new PartWireNFC();
		}
		System.out.println("There was an error!");
		return null;
	}


	public int getColour(TileEntity tile) {
		if (Multipart.hasPartWire(tile)) {
			return Multipart.getwire((TileMultipart) tile).colour;
		}
		if (Multipart.hasPartWireNFC(tile)) {
			return Multipart.getWireNFC((TileMultipart) tile).colour;
		}
		return 999;
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
