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
import modmuss50.network.api.power.IEnergyFace;
import modmuss50.network.blocks.NetworkBlocks;
import modmuss50.network.blocks.WorldCoordinate;
import modmuss50.network.blocks.tileentities.TileEntityCable;
import modmuss50.network.blocks.tileentities.TileEntityPowerSink;
import modmuss50.network.client.render.RenderCable;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourceteam.mods.lib.Functions;

import java.util.*;

public class PartCable extends TMultiPart implements TSlottedPart, JNormalOcclusion, ISidedHollowConnect {
    public static Cuboid6[] boundingBoxes = new Cuboid6[14];
    public static float center = 0.6F;
    public static float offset = 0.10F;
    private static int expandBounds = -1;
    @SideOnly(Side.CLIENT)
    private static RenderCable renderer;
    @SideOnly(Side.CLIENT)
    private static IIcon breakIcon;
    public final boolean[] connectedSideFlags = new boolean[6];
    public Map<ForgeDirection, TileEntity> connectedSides;
    int ticks;

    static {
        refreshBounding();
    }

    boolean gotSerpos = false;
    int ServX;
    int ServY;
    int ServZ;
    private boolean needToCheckNeighbors;
    private boolean connectedSidesHaveChanged = true;
    private boolean hasCheckedSinceStartup;

    public static boolean isCable(TileEntity tile) {

        if (tile instanceof TileEntityCable) return true;

        return tile instanceof TileMultipart && Multipart.hasPartCable((TileMultipart) tile);
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

    public static boolean isConnectedTo(WorldCoordinate start, World world) {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            PartCable cable = (Multipart.getCable(world.getTileEntity(start.getX(), start.getY(), start.getZ())));
            if (cable != null) {
                for (int i = 0; i < cable.connectedSides.size(); i++) {
                    if (cable.connectedSides.containsKey(cable)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getType() {
        return "tile.network.cable";
    }

    @Override
    public void load(NBTTagCompound tagCompound) {
        super.load(tagCompound);
        connectedSides = new HashMap<ForgeDirection, TileEntity>();
        readConnectedSidesFromNBT(tagCompound);
    }

    @Override
    public void save(NBTTagCompound tagCompound) {
        super.save(tagCompound);
        writeConnectedSidesToNBT(tagCompound);
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
                renderer = new RenderCable();
            }
            renderer.doRender(pos.x, pos.y, pos.z, connectedSides, getSerX(), getSerY(), getSerZ());
        }
    }

    public boolean shouldConnectTo(TileEntity entity, ForgeDirection dir, Object caller) {
        int opposite = Functions.getIntDirFromDirection(dir.getOpposite());
        if (entity instanceof TileMultipart) {
            List<TMultiPart> t = ((TileMultipart) entity).jPartList();

            if (Multipart.hasPartCable((TileMultipart) entity)) {
                if (!((TileMultipart) entity).canAddPart(new NormallyOccludedPart(boundingBoxes[opposite])))
                    return false;
            }

            for (TMultiPart p : t) {

                if (p instanceof IEnergyFace) {
                    return true;
                }
            }

            for (TMultiPart p : t) {
                if (p instanceof PartCable && caller.equals(this)) {
                    ((PartCable) p).checkConnectedSides(this);
                }

                if (p instanceof PartCable) {
                    return ((PartCable) p).canConnectTo(dir.getOpposite());
                }
            }

            return false;
        } else {
            return entity instanceof IEnergyFace;
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
        return tile().canAddPart(new NormallyOccludedPart(boundingBoxes[d]));
    }

    public void onNeighborChanged() {
        checkConnectedSides();
        refreshBounding();
    }

    public ItemStack getItem() {
        return new ItemStack(Multipart.cablepartitem, 1);
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
            if (world().getTotalWorldTime() % 10 == 0 && !hasCheckedSinceStartup) {
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

        if (ticks != 30) {
            ticks += 1;
        } else {
            updateCables(world(), this.x(), this.y(), this.z());
            ticks = 0;
        }
    }

    public Map<ForgeDirection, TileEntity> getConnectedSides() {
        if (connectedSides == null) {
            checkConnectedSides();
        }
        return connectedSides;
    }

    // SOME OF THE SUFF FROM TileEntityCable

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

    public void updateCables(World world, int blockX, int blockY, int blockZ) {
        List<WorldCoordinate> visited = new ArrayList<WorldCoordinate>();
        int cableMaxLenghth = 128;
        Queue<WorldCoordinate> queue = new PriorityQueue<WorldCoordinate>();
        WorldCoordinate start = new WorldCoordinate(blockX, blockY, blockZ, 0);
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
                                if (element.getDepth() < cableMaxLenghth) {// && PartCable.isConnectedTo(target, this.world())) {
                                    Block block = world.getBlock(target.getX(), target.getY(), target.getZ());
                                    TileEntity tile = world.getTileEntity(target.getX(), target.getY(), target.getZ());
                                    int meta = world.getBlockMetadata(target.getX(), target.getY(), target.getZ());
                                    if (block == NetworkBlocks.powerSink) {
                                        TileEntity tileEntity = world.getTileEntity(target.getX(), target.getY(), target.getZ());
                                        if (tileEntity != null && tileEntity instanceof TileEntityPowerSink) {
                                            TileEntityPowerSink server = (TileEntityPowerSink) tileEntity;
                                            setSerpos(target.getX(), target.getY(), target.getZ(), true);
                                            return;
                                        } else {
                                            setSerpos(0, 0, 0, false);
                                            return;
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

        setSerpos(0, 0, 0, false);

    }

    public int getSerX() {
        if (gotSerpos != false) {
            return ServX;
        }
        return 0;
    }

    public int getSerY() {
        if (gotSerpos != false) {
            return ServY;
        }
        return 0;
    }

    public int getSerZ() {
        if (gotSerpos != false) {
            return ServZ;
        }
        return 0;
    }

    public void setSerpos(int x, int y, int z, boolean pos) {
        ServX = x;
        ServY = y;
        ServZ = z;
        gotSerpos = pos;
    }

    public void clearSerPos() {
        ServX = 0;
        ServY = 0;
        ServZ = 0;
        gotSerpos = false;

    }

}
