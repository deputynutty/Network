package modmuss50.network.blocks.tileentities;

import cpw.mods.fml.common.Loader;
import modmuss50.network.Fmp.PartCable;
import modmuss50.network.api.INetworkComponent;
import modmuss50.network.blocks.NetworkBlocks;
import modmuss50.network.blocks.WorldCoordinate;
import modmuss50.network.netty.ChannelHandler;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourceteam.mods.lib.BlockHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Mark on 21/02/14.
 */
public class TileEntityCable extends BaseTile implements INetworkComponent {

    // used mainly for rendering
    public boolean top, bottom, back, forward, left, right;
    public boolean canConnecttop, canConnectbottom, canConnectback, canConnectforward, canConnectleft, canConnectright;
    public boolean doupdate = true;
    public World TeWorld;
    public byte[] sideCache = {0, 0, 0, 0, 0, 0};
    public int BlockX, BlockY, BlockZ;
    public World World;
    boolean gotSerpos = false;
    int ServX;
    int ServY;
    int ServZ;
    int ticks;

    public TileEntityCable() {
        // setConectionSides(top,bottom,back,forward,left,right);
        setConectionSides(true, true, true, true, true, true);
        TeWorld = worldObj;

    }

    public void updateEntity() {
        super.updateEntity();

        if (ticks != 30) {
            ticks += 1;
        } else {
            updateCables(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            // syncTile();
            ticks = 0;
        }

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

        // check to see if the powerSink has been removed from the world
        if (world.getTileEntity(ServX, ServY, ServZ) instanceof INetworkComponent) {

        } else {
            gotSerpos = false;
            ServX = 0;
            ServY = 0;
            ServZ = 0;
        }

        if (this.ServX == 0 & this.ServY == 0 & this.ServZ == 0) {
            this.doupdate = false;
        } else {
            this.doupdate = true;
        }

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
                if (worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof INetworkComponent) {
                    bottom = false;
                } else {
                    bottom = true;
                }
            }
        } else {
            bottom = true;
        }

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

    public boolean hasServerPos() {
        return gotSerpos;
    }

    @Override
    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = new NBTTagList();
        par1NBTTagCompound.setBoolean("gotSerpos", gotSerpos);
        par1NBTTagCompound.setInteger("ServX", ServX);
        par1NBTTagCompound.setInteger("ServY", ServY);
        par1NBTTagCompound.setInteger("ServZ", ServZ);
    }

    @Override
    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);

    }

    public void setConectionSides(boolean topS, boolean bottomS, boolean backS, boolean forwardS, boolean leftS, boolean rightS) {
        canConnecttop = topS;
        canConnectbottom = bottomS;
        canConnectback = backS;
        canConnectforward = forwardS;
        canConnectleft = leftS;
        canConnectright = rightS;
    }

    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
        readFromNBT(packet.func_148857_g());
    }

    public void syncTile() {
        ChannelHandler.sendPacketToAllPlayers(getDescriptionPacket(), worldObj);
    }

    public void updateCables(World world, int blockX, int blockY, int blockZ) {

        BlockX = blockX;
        BlockY = blockY;
        BlockZ = blockZ;
        World = this.getWorldObj();

        List<WorldCoordinate> visited = new ArrayList<WorldCoordinate>();
        int cableMaxLenghth = 128;
        Queue<WorldCoordinate> queue = new PriorityQueue<WorldCoordinate>();
        WorldCoordinate start = new WorldCoordinate(BlockX, BlockY, BlockZ, 0);
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
                                    Block block = World.getBlock(target.getX(), target.getY(), target.getZ());
                                    TileEntity tile = World.getTileEntity(target.getX(), target.getY(), target.getZ());
                                    int meta = World.getBlockMetadata(target.getX(), target.getY(), target.getZ());
                                    if (block == NetworkBlocks.powerSink) {
                                        TileEntity tileEntity = World.getTileEntity(target.getX(), target.getY(), target.getZ());
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

    public boolean isCable(TileEntity tile) {
        if (Loader.isModLoaded("ForgeMultipart")) {
            return PartCable.isCable(tile);
        }

        if (tile instanceof INetworkComponent)
            return true;

        return tile instanceof TileEntityCable;
    }

}
