package modmuss50.network.Fmp;

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
import modmuss50.network.blocks.WorldCoordinate;
import modmuss50.network.client.Render.RenderPipeLine;
import modmuss50.network.netty.ChannelHandler;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import sourceteam.mods.lib.Functions;

import java.util.*;

public class PartPipeLine extends TMultiPart implements TSlottedPart, JNormalOcclusion, ISidedHollowConnect, IFluidHandler {
	public static Cuboid6[]					boundingBoxes				= new Cuboid6[14];
	private static int						expandBounds				= -1;

	private Map<ForgeDirection, TileEntity>	connectedSides;
	private final boolean[]					connectedSideFlags			= new boolean[6];
	private boolean							needToCheckNeighbors;
	private boolean							connectedSidesHaveChanged	= true;
	private boolean							hasCheckedSinceStartup;

	@SideOnly(Side.CLIENT)
	private static RenderPipeLine			renderer;

	@SideOnly(Side.CLIENT)
	private static IIcon					breakIcon;

	public static float						center						= 0.6F;
	public static float						offset						= 0.10F;

	static {
		refreshBounding();
	}

	@Override
	public String getType() {
		return Multipart.Pipecodename;
	}

	@Override
	public void load(NBTTagCompound tagCompound) {
		super.load(tagCompound);
		connectedSides = new HashMap<ForgeDirection, TileEntity>();
		readConnectedSidesFromNBT(tagCompound);
		if (tagCompound.getInteger("fluidName") != -1) {
			tank.setFluid(new FluidStack(tagCompound.getInteger("fluidName"), tagCompound.getInteger("fluidAmount")));
		}
	}

	@Override
	public void save(NBTTagCompound tagCompound) {
		super.save(tagCompound);
		writeConnectedSidesToNBT(tagCompound);
		tagCompound.setInteger("fluidAmount", tank.getFluidAmount());

		if (tank.getFluid() == null) {
			tagCompound.setInteger("fluidName", -1);
		}
		else {
			tagCompound.setInteger("fluidName", tank.getFluid().fluidID);
		}
	}

	@Override
	public void writeDesc(MCDataOutput packet) {
		NBTTagCompound mainCompound = new NBTTagCompound();
		NBTTagCompound handlerCompound = new NBTTagCompound();
		if (connectedSidesHaveChanged && world() != null && !world().isRemote) {
			connectedSidesHaveChanged = false;
			mainCompound.setBoolean("connectedSidesHaveChanged", true);
		}
		mainCompound.setTag("handler", handlerCompound);
		mainCompound.setInteger("fluidAmount", tank.getFluidAmount());
		if (tank.getFluid() == null) {
			mainCompound.setInteger("fluidID", -1);
		}
		else {
			mainCompound.setInteger("fluidID", tank.getFluid().fluidID);
		}

		packet.writeNBTTagCompound(mainCompound);
	}

	@Override
	public void readDesc(MCDataInput packet) {
		NBTTagCompound mainCompound = packet.readNBTTagCompound();
		NBTTagCompound handlerCompound = mainCompound.getCompoundTag("handler");
		if (mainCompound.getBoolean("connectedSidesHaveChanged")) {
			hasCheckedSinceStartup = false;
		}
		if (mainCompound.getInteger("fluidID") != -1) {
			tank.setFluid(new FluidStack(mainCompound.getInteger("fluidID"), mainCompound.getInteger("fluidAmount")));
		}
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
		return NormalOcclusionTest.apply(this, npart);
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
		if (renderer == null) {
			renderer = new RenderPipeLine();
		}
		if (pass == 0) {
			if (tank.getFluid() != null && tank.getFluid().amount != 0) {
				renderer.doRender(pos.x, pos.y, pos.z, 0, connectedSides, tank.getFluid().amount);
			}
			else {
				renderer.doRender(pos.x, pos.y, pos.z, 0, connectedSides, 0);
			}
		}
	}

	private boolean shouldConnectTo(TileEntity entity, ForgeDirection dir, Object caller) {
		int opposite = Functions.getIntDirFromDirection(dir.getOpposite());
		if (entity instanceof TileMultipart) {
			List<TMultiPart> t = ((TileMultipart) entity).jPartList();

			if (Multipart.hasPartPipe((TileMultipart) entity)) {
				if (!((TileMultipart) entity).canAddPart(new NormallyOccludedPart(boundingBoxes[opposite])))
					return false;
			}

			for (TMultiPart p : t) {
				if (p instanceof PartPipeLine && caller.equals(this)) {
					((PartPipeLine) p).checkConnectedSides(this);
				}

				if (p instanceof PartPipeLine) {
					return ((PartPipeLine) p).canConnectTo(dir.getOpposite());
				}
			}

			return false;
		}
		else {
			if (entity instanceof IFluidHandler) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public boolean isConnectedTo(ForgeDirection side) {
		int d = side.ordinal();

		if (world() != null && tile() != null) {
			TileEntity te = world().getTileEntity(x() + side.offsetX, y() + side.offsetY, z() + side.offsetZ);
			return tile().canAddPart(new NormallyOccludedPart(boundingBoxes[d])) && shouldConnectTo(te, side, this);
		}
		else {
			return false;
		}
	}

	public void checkConnectedSides() {
		checkConnectedSides(this);
	}

	public void checkConnectedSides(Object caller) {
		refreshBounding();

        try{
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
        }  catch (NullPointerException npe)
        {

        }

	}

	public boolean canConnectTo(ForgeDirection side) {
		int d = side.ordinal();
        try{
            return tile().canAddPart(new NormallyOccludedPart(boundingBoxes[d]));
        } catch (NullPointerException npe){
            System.out.println("[NETWORK] a NPE error was caught, please contact modmuss50");
          return false;
        }

	}

	public void onNeighborChanged() {
		checkConnectedSides();
		refreshBounding();
	}

	public ItemStack getItem() {
		return new ItemStack(Multipart.itemPipeLine, 1);
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
		onTick();
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
		EntityDigIconFX.addBlockDestroyEffects(world(), Cuboid6.full.copy().add(Vector3.fromTileEntity(tile())), new IIcon[] { breakIcon, breakIcon, breakIcon, breakIcon, breakIcon, breakIcon }, effectRenderer);
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

	public static void refreshBounding() {
		float centerFirst = center - offset;
		double w = 0.2D / 2;
		boundingBoxes[6] = new Cuboid6(centerFirst - w - 0.03, centerFirst - w - 0.08, centerFirst - w - 0.03, centerFirst + w + 0.08, centerFirst + w + 0.04, centerFirst + w + 0.08);
		boundingBoxes[6] = new Cuboid6(centerFirst - w, centerFirst - w, centerFirst - w, centerFirst + w, centerFirst + w, centerFirst + w);
		int i = 0;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			double xMin1 = (dir.offsetX < 0 ? 0.0 : (dir.offsetX == 0 ? centerFirst - w : centerFirst + w));
			double xMax1 = (dir.offsetX > 0 ? 1.0 : (dir.offsetX == 0 ? centerFirst + w : centerFirst - w));
			double yMin1 = (dir.offsetY < 0 ? 0.0 : (dir.offsetY == 0 ? centerFirst - w : centerFirst + w));
			double yMax1 = (dir.offsetY > 0 ? 1.0 : (dir.offsetY == 0 ? centerFirst + w : centerFirst - w));
			double zMin1 = (dir.offsetZ < 0 ? 0.0 : (dir.offsetZ == 0 ? centerFirst - w : centerFirst + w));
			double zMax1 = (dir.offsetZ > 0 ? 1.0 : (dir.offsetZ == 0 ? centerFirst + w : centerFirst - w));
			boundingBoxes[i] = new Cuboid6(xMin1, yMin1, zMin1, xMax1, yMax1, zMax1);
			i++;
		}
	}

	// START OF THE FLUID LOGIC

	private FluidStack	lastBeforeUpdate	= null;

	public FluidTank	tank				= new FluidTank(1000) {
												public FluidTank readFromNBT(NBTTagCompound nbt) {
													if (!nbt.hasKey("Empty")) {
														FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
														setFluid(fluid);
													}
													else {
														setFluid(null);
													}
													return this;
												}
											};

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource == null || (tank.getFluid() != null && resource.fluidID != tank.getFluid().fluidID))
			return 0;
		return fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (tank.getFluid() == null || resource == null || resource.fluidID != tank.getFluid().fluidID)
			return null;
		return drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (tank.getFluid() == null)
			return null;
		return drain(from, new FluidStack(tank.getFluid(), maxDrain), doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return tank.getFluid() == null || tank.getFluid().getFluid() == fluid;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return getTankInfo(true);
	}

	public FluidStack drain(FluidStack fluid, boolean doDrain) {
		FluidStack drained = tank.drain(fluid.amount, doDrain);
		compareAndUpdate();
		if (drained == null || drained.amount < fluid.amount) {
			TileEntity offTE = world().getTileEntity(x(), y() - 1, z());
			if (Multipart.hasPartPipe(offTE)) {
				PartPipeLine tank = (PartPipeLine) Multipart.getPipe(offTE);
				FluidStack externallyDrained = tank.drain(new FluidStack(fluid.fluidID, fluid.amount - (drained != null ? drained.amount : 0)), doDrain);
				if (externallyDrained != null)
					return new FluidStack(fluid.fluidID, (drained != null ? drained.amount : 0) + externallyDrained.amount);
				else
					return drained;
			}
		}
		return drained;
	}

	public int fill(FluidStack fluid, boolean doFill) {
		int filled = tank.fill(fluid, doFill);
		compareAndUpdate();

		if (filled < fluid.amount) {
			return 0;
		}

		return filled;
	}

	public FluidTankInfo[] getTankInfo(boolean goToMainTank) {
		if (!goToMainTank)
			return new FluidTankInfo[] { tank.getInfo() };

		int amount = 0, capacity = 0;
		Fluid fluid = null;

		int yOff = 0;
		TileEntity offTE = world().getTileEntity(x(), y() - yOff, z());
		PartPipeLine mainTank = this;
		while (true) {
			if (offTE != null && Multipart.hasPartPipe(offTE)) {
				if ((((PartPipeLine) Multipart.getPipe(offTE)).getFluid() == null || ((PartPipeLine) Multipart.getPipe(offTE)).getFluid() == getFluid())) {
					mainTank = (PartPipeLine) Multipart.getPipe(world().getTileEntity(x(), y() - yOff, y()));
					yOff++;
					offTE = world().getTileEntity(x(), y() - yOff, z());
					continue;
				}
			}
			break;
		}

		yOff = 0;
		offTE = world().getTileEntity(x(), y() + yOff, z());
		while (true) {
			if (offTE != null && Multipart.hasPartPipe(offTE)) {
				mainTank = (PartPipeLine) Multipart.getPipe(offTE);
				if ((mainTank.getFluid() == null || mainTank.getFluid() == getFluid())) {
					FluidTankInfo info = mainTank.getTankInfo(false)[0];
					if (info != null) {
						capacity += info.capacity;
						if (info.fluid != null) {
							amount += info.fluid.amount;
							if (info.fluid.getFluid() != null)
								fluid = info.fluid.getFluid();
						}
					}
					yOff++;
					offTE = world().getTileEntity(x(), y() + yOff, z());
					continue;
				}
			}
			break;
		}

		return new FluidTankInfo[] { new FluidTankInfo(fluid != null ? new FluidStack(fluid, amount) : null, capacity) };
	}

	public Fluid getFluid() {
		FluidStack tankFluid = tank.getFluid();
		return tankFluid != null && tankFluid.amount > 0 ? tankFluid.getFluid() : null;
	}

	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		save(nbtTag);
		return new S35PacketUpdateTileEntity(x(), y(), z(), 1, nbtTag);
	}

	public void compareAndUpdate() {
		if (!world().isRemote) {
			FluidStack current = tank.getFluid();
			if (current != null) {
				if (lastBeforeUpdate != null) {
					if (Math.abs(current.amount - lastBeforeUpdate.amount) >= 500) {
						ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), world());
						lastBeforeUpdate = current.copy();
					}
					else
						if (lastBeforeUpdate.amount < tank.getCapacity() && current.amount == tank.getCapacity() || lastBeforeUpdate.amount == tank.getCapacity() && current.amount < tank.getCapacity()) {
							ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), world());
							lastBeforeUpdate = current.copy();
						}
				}
				else {
					ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), world());
					lastBeforeUpdate = current.copy();
				}
			}
			else
				if (lastBeforeUpdate != null) {
					ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), world());
					lastBeforeUpdate = null;
				}
		}
	}

	public void onTick() {
//		if (canConnectTo(ForgeDirection.UP)) {
//			TileEntity tile = world().getTileEntity(x(), y() + 1, z());
//			if (tile instanceof IFluidHandler) {
//				if (!Multipart.hasPartPipe(tile)) {
//					IFluidHandler fluidHandler = (IFluidHandler) world().getTileEntity(x(), y() + 1, z());
//					FluidTankInfo[] fluidTankInfo = fluidHandler.getTankInfo(ForgeDirection.DOWN);
//					PartPipeLine partPipeLine = this;
//					if (partPipeLine.tank.getCapacity() == partPipeLine.tank.getFluidAmount()) {
//						partPipeLine = findnextPipeline();
//					}
//					if (partPipeLine != null && canFillwithLiquid(partPipeLine.tank.getFluid(), this.tank.getFluid())) {
//						partPipeLine.fill(ForgeDirection.UP, fluidHandler.drain(ForgeDirection.DOWN, 10, true), true);
//					}
//				}
//			}
//		}

		if (canConnectTo(ForgeDirection.DOWN)) {
			TileEntity tile = world().getTileEntity(x(), y() - 1, z());
			if (tile instanceof IFluidHandler) {
				if (!Multipart.hasPartPipe(tile)) {
					for (int i = 0; i < 2; i++) {
						if (this.tank.getFluidAmount() <= 990) {
							PartPipeLine last = findlastPipeline();
							if (last != null) {
								this.fill(ForgeDirection.UP, last.drain(ForgeDirection.DOWN, 10, true), true);
							}
						}
					}
					IFluidHandler fluidHandler = (IFluidHandler) world().getTileEntity(x(), y() - 1, z());
					PartPipeLine partPipeLine = this;
					if (partPipeLine != null) {
						if (this.getFluid() != null) {
							if (fluidHandler.canFill(ForgeDirection.DOWN, this.getFluid()) && canFit(fluidHandler, ForgeDirection.DOWN)) {
								fluidHandler.fill(ForgeDirection.UP, partPipeLine.drain(ForgeDirection.DOWN, 10, true), true);
							}
						}
					}
				}
			}
		}
	}

	public boolean canFit(IFluidHandler tankInfos, ForgeDirection dir) {
		if (tankInfos == null)
			return false;
		for (int i = 0; i < tankInfos.getTankInfo(dir).length; i++) {
			if (tankInfos.getTankInfo(dir)[i].fluid == null)
				return true;
			if (tankInfos.getTankInfo(dir)[i].capacity >= tankInfos.getTankInfo(dir)[i].fluid.amount) {
				return true;
			}
		}
		return false;
	}

	public boolean canFillwithLiquid(FluidStack fluid1, FluidStack fluid2) {
		if (fluid1 == null) {
			return true;
		}
		return fluid1.equals(fluid2);
	}

	public PartPipeLine findnextPipeline() {
		List<WorldCoordinate> visited = new ArrayList<WorldCoordinate>();
		int cableMaxLenghth = 128;
		Queue<WorldCoordinate> queue = new PriorityQueue<WorldCoordinate>();
		WorldCoordinate start = new WorldCoordinate(x(), y(), z(), 0);
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
									TileEntity tileEntity = world().getTileEntity(target.getX(), target.getY(), target.getZ());
									if (tileEntity != null && Multipart.hasPartPipe(tileEntity)) {
										PartPipeLine pipeLine = (PartPipeLine) Multipart.getPipe(tileEntity);
										if (pipeLine.tank.getFluidAmount() < pipeLine.tank.getCapacity()) {
											return pipeLine;
										}
										else {
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
		return null;
	}

	public PartPipeLine findlastPipeline() {
		List<WorldCoordinate> visited = new ArrayList<WorldCoordinate>();
		int cableMaxLenghth = 128;
		Queue<WorldCoordinate> queue = new PriorityQueue<WorldCoordinate>();
		WorldCoordinate start = new WorldCoordinate(x(), y(), z(), 0);
		queue.add(start);
		visited.add(start);
		PartPipeLine partPipeLine = null;
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
									TileEntity tileEntity = world().getTileEntity(target.getX(), target.getY(), target.getZ());
									if (tileEntity != null && Multipart.hasPartPipe(tileEntity)) {
										PartPipeLine pipeLine = (PartPipeLine) Multipart.getPipe(tileEntity);
										if (pipeLine.tank.getFluidAmount() >= 10) {
											partPipeLine = pipeLine;
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
		return partPipeLine;
	}
}
