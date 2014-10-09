package modmuss50.network.compact.FMP;


import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.EntityDigIconFX;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import codechicken.multipart.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.api.data.IDataPer;
import modmuss50.network.client.render.RenderWire;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;
import sourceteam.mods.lib.Functions;

import java.util.*;

public class PartWire extends TMultiPart implements TSlottedPart, JNormalOcclusion, ISidedHollowConnect {
	public static Cuboid6[] boundingBoxes = new Cuboid6[14];
	public static float center = 0.6F;
	public static float offset = 0.10F;
	private static int expandBounds = -1;
	@SideOnly(Side.CLIENT)
	private static RenderWire renderer;
	@SideOnly(Side.CLIENT)
	private static IIcon breakIcon;
	private final boolean[] connectedSideFlags = new boolean[6];
	public int colour = 9; //Defaults to pink might change this

	static {
		refreshBounding();
	}

	boolean gotSerpos = false;
	int ServX;
	int ServY;
	int ServZ;
	private Map<ForgeDirection, TileEntity> connectedSides;
	private boolean needToCheckNeighbors;
	private boolean connectedSidesHaveChanged = true;
	private boolean hasCheckedSinceStartup;


	public static void refreshBounding() {
		float centerFirst = center - offset;
		double w = 0.2D / 2;
		boundingBoxes[6] = new Cuboid6(centerFirst - w, centerFirst - w - 0.4, centerFirst - w, centerFirst + w, centerFirst + w - 0.5, centerFirst + w);

		int i = 0;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			double xMin1 = (dir.offsetX < 0 ? 0.0 : (dir.offsetX == 0 ? centerFirst - w : centerFirst + w));
			double xMax1 = (dir.offsetX > 0 ? 1.0 : (dir.offsetX == 0 ? centerFirst + w : centerFirst - w));

			double yMin1 = (dir.offsetY < 0 ? 0.0 : (dir.offsetY == 0 ? centerFirst - w : centerFirst + w)) - 0.4;
			double yMax1 = (dir.offsetY > 0 ? 1.0 : (dir.offsetY == 0 ? centerFirst + w : centerFirst - w)) - 0.5;

			double zMin1 = (dir.offsetZ < 0 ? 0.0 : (dir.offsetZ == 0 ? centerFirst - w : centerFirst + w));
			double zMax1 = (dir.offsetZ > 0 ? 1.0 : (dir.offsetZ == 0 ? centerFirst + w : centerFirst - w));

			boundingBoxes[i] = new Cuboid6(xMin1, yMin1, zMin1, xMax1, yMax1, zMax1);
			i++;
		}
	}

	@Override
	public String getType() {
		return Multipart.wireName;
	}

	@Override
	public void load(NBTTagCompound tagCompound) {
		super.load(tagCompound);
		connectedSides = new HashMap<ForgeDirection, TileEntity>();
		readConnectedSidesFromNBT(tagCompound);
		colour = tagCompound.getInteger("colour");
	}

	@Override
	public void save(NBTTagCompound tagCompound) {
		super.save(tagCompound);
		writeConnectedSidesToNBT(tagCompound);
		tagCompound.setInteger("colour", colour);
	}

	@Override
	public void writeDesc(MCDataOutput packet) {
		packet.writeInt(colour);
		NBTTagCompound mainCompound = new NBTTagCompound();
		NBTTagCompound handlerCompound = new NBTTagCompound();
		if (connectedSidesHaveChanged && world() != null && !world().isRemote) {
			connectedSidesHaveChanged = false;
			mainCompound.setBoolean("connectedSidesHaveChanged", true);
		}
		mainCompound.setTag("handler", handlerCompound);
		packet.writeNBTTagCompound(mainCompound);
	}

	private void readConnectedSidesFromNBT(NBTTagCompound tagCompound) {

		NBTTagCompound ourCompound = tagCompound.getCompoundTag("connectedSides");

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			connectedSideFlags[dir.ordinal()] = ourCompound.getBoolean(dir.name());
		}
		needToCheckNeighbors = true;
	}

	private void writeConnectedSidesToNBT(NBTTagCompound tagCompound) {

		if (connectedSides == null) {
			connectedSides = new HashMap<ForgeDirection, TileEntity>();
		}

		NBTTagCompound ourCompound = new NBTTagCompound();
		for (Map.Entry<ForgeDirection, TileEntity> entry : connectedSides.entrySet()) {
			ourCompound.setBoolean(entry.getKey().name(), true);
		}
		tagCompound.setTag("connectedSides", ourCompound);
	}

	@Override
	public void readDesc(MCDataInput packet) {
		colour = packet.readInt();
		NBTTagCompound mainCompound = packet.readNBTTagCompound();
		NBTTagCompound handlerCompound = mainCompound.getCompoundTag("handler");
		if (mainCompound.getBoolean("connectedSidesHaveChanged")) {
			hasCheckedSinceStartup = false;
		}

	}

	@Override
	public int getSlotMask() {
		return 0;
	}

	@Override
	public Iterable<IndexedCuboid6> getSubParts() {
		Iterable<Cuboid6> boxList = getCollisionBoxes();
		LinkedList<IndexedCuboid6> partList = new LinkedList<IndexedCuboid6>();
		for (Cuboid6 c : boxList)
			partList.add(new IndexedCuboid6(0, c));
		return partList;
	}

	@Override
	public boolean occlusionTest(TMultiPart npart) {
		try {
			return NormalOcclusionTest.apply(this, npart);
		} catch (NullPointerException npe) {
			return false;
		}
	}

	@Override
	public Iterable<Cuboid6> getOcclusionBoxes() {
		if (expandBounds >= 0)
			return Arrays.asList(boundingBoxes[expandBounds]);

		return Arrays.asList(boundingBoxes[6]);
	}

	@Override
	public Iterable<Cuboid6> getCollisionBoxes() {
		LinkedList<Cuboid6> list = new LinkedList<Cuboid6>();
		list.add(boundingBoxes[6]);
		if (connectedSides == null)
			return list;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (connectedSides.containsKey(dir)) {
				list.add(boundingBoxes[Functions.getIntDirFromDirection(dir)]);
			}
		}
		return list;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderDynamic(Vector3 pos, float frame, int pass) {
		if (pass == 0) {
			if (renderer == null) {
				renderer = new RenderWire();
			}
			renderer.doRender(pos.x, pos.y, pos.z, connectedSides, colour);
		}
	}

	private boolean shouldConnectTo(TileEntity entity, ForgeDirection dir, Object caller) {
		int opposite = Functions.getIntDirFromDirection(dir.getOpposite());
		if (entity instanceof TileMultipart) {
			List<TMultiPart> t = ((TileMultipart) entity).jPartList();

			if (Multipart.hasPartWire((TileMultipart) entity) || Multipart.hasPartWireNFC((TileMultipart) entity)) {
				if (!((TileMultipart) entity).canAddPart(new NormallyOccludedPart(boundingBoxes[opposite])))
					return false;
			}

			for (TMultiPart p : t) {

				if (p instanceof IDataPer) {
					return true;
				}
			}

			for (TMultiPart p : t) {
				if (p instanceof PartWire && caller.equals(this)) {
					if (((PartWire) p).colour == this.colour)
						((PartWire) p).checkConnectedSides(this);
				}

				if (p instanceof PartWire) {
					if (((PartWire) p).colour == this.colour)
						return ((PartWire) p).canConnectTo(dir.getOpposite());
				}

				if (p instanceof PartWireNFC && caller.equals(this)) {
					if (((PartWireNFC) p).colour == this.colour)
						((PartWireNFC) p).checkConnectedSides(this);
				}

				if (p instanceof PartWireNFC) {
					if (((PartWireNFC) p).colour == this.colour)
						return ((PartWireNFC) p).canConnectTo(dir.getOpposite());
				}
			}

			return false;
		} else {
			if (entity instanceof IDataPer) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isConnectedTo(ForgeDirection side) {
		int d = side.ordinal();

		if (world() != null && tile() != null) {
			TileEntity te = world().getTileEntity(x() + side.offsetX, y() + side.offsetY, z() + side.offsetZ);
			return tile().canAddPart(new NormallyOccludedPart(boundingBoxes[d])) && shouldConnectTo(te, side, this);
		} else {
			return false;
		}
	}

	public void checkConnectedSides() {
		checkConnectedSides(this);
	}

	public void checkConnectedSides(Object caller) {
		refreshBounding();
		connectedSides = new HashMap<ForgeDirection, TileEntity>();
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int d = Functions.getIntDirFromDirection(dir);

			TileEntity te = world().getTileEntity(x() + dir.offsetX, y() + dir.offsetY, z() + dir.offsetZ);
			if (shouldConnectTo(te, dir, caller)) {
				if (tile().canAddPart(new NormallyOccludedPart(boundingBoxes[d]))) {
					connectedSides.put(dir, te);
				}
			}
		}
		connectedSidesHaveChanged = true;
	}

	public boolean canConnectTo(ForgeDirection side) {
		int d = side.ordinal();
		if (side == ForgeDirection.DOWN || side == ForgeDirection.UP)
			return false;
		return tile().canAddPart(new NormallyOccludedPart(boundingBoxes[d]));
	}

	public void onNeighborChanged() {
		checkConnectedSides();
		refreshBounding();
	}

	public ItemStack getItem() {
		return new ItemStack(Multipart.itemPartWire, 1);
	}

	@Override
	public void onPartChanged(TMultiPart part) {
		checkConnectedSides();
		onRemoved();
	}

	@Override
	public Iterable<ItemStack> getDrops() {

		LinkedList<ItemStack> items = new LinkedList<ItemStack>();
		items.add(getItem());
		return items;
	}

	@Override
	public ItemStack pickItem(MovingObjectPosition hit) {
		return getItem();
	}

	@Override
	public void update() {

		if (world() != null) {
			if (world().getTotalWorldTime() % 10 == 0 && hasCheckedSinceStartup == false) {
				checkConnectedSides();
				hasCheckedSinceStartup = true;
			}

			checkConnectedSides();
		}

		if (needToCheckNeighbors) {
			needToCheckNeighbors = false;
			connectedSides = new HashMap<ForgeDirection, TileEntity>();
			for (int i = 0; i < 6; i++) {
				if (connectedSideFlags[i]) {
					ForgeDirection dir = ForgeDirection.getOrientation(i);
					connectedSides.put(dir, world().getTileEntity(x() + dir.offsetX, y() + dir.offsetY, z() + dir.offsetZ));
				}
			}
			if (!world().isRemote) {
				connectedSidesHaveChanged = true;
			}
		}
	}

	public Map<ForgeDirection, TileEntity> getConnectedSides() {
		if (connectedSides == null) {
			checkConnectedSides();
		}
		return connectedSides;
	}

	@Override
	public void onRemoved() {
		super.onRemoved();
		if (!world().isRemote) {
			for (Map.Entry<ForgeDirection, TileEntity> entry : connectedSides.entrySet()) {

			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addDestroyEffects(MovingObjectPosition hit, EffectRenderer effectRenderer) {
		addDestroyEffects(effectRenderer);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addDestroyEffects(EffectRenderer effectRenderer) {
		if (breakIcon == null) {
			breakIcon = null;
			return;
		}
		EntityDigIconFX.addBlockDestroyEffects(world(), Cuboid6.full.copy().add(Vector3.fromTileEntity(tile())), new IIcon[]{breakIcon, breakIcon, breakIcon, breakIcon, breakIcon, breakIcon}, effectRenderer);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addHitEffects(MovingObjectPosition hit, EffectRenderer effectRenderer) {

		EntityDigIconFX.addBlockHitEffects(world(), Cuboid6.full.copy().add(Vector3.fromTileEntity(tile())), hit.sideHit, breakIcon, effectRenderer);
	}

	@Override
	public float getStrength(MovingObjectPosition hit, EntityPlayer player) {
		return 8F;
	}

	@Override
	public int getHollowSize(int arg0) {
		return 6;
	}

	@Override
	public boolean activate(EntityPlayer player, MovingObjectPosition hit, ItemStack item) {
		if (item != null && item.getItem() instanceof ItemDye) {
			colour = item.getItemDamage();
			System.out.println("Set Colour to: " + item.getDisplayName());
		} else {
			System.out.println("Colour =" + colour);
		}
		return super.activate(player, hit, item);
	}

}
