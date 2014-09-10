package modmuss50.network.blocks.tileentities;

import cpw.mods.fml.common.FMLCommonHandler;
import modmuss50.network.NetworkCore;
import modmuss50.network.api.INetworkComponent;
import modmuss50.network.api.Item.IPowerSinkUpgrade;
import modmuss50.network.netty.packets.PacketServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.Set;

/**
 * Created by Mark on 21/02/14.
 */
public class TileEntityPowerSink extends BaseTile implements INetworkComponent, IInventory, ISidedInventory {

    public boolean needsUpdate = false;
    public int upTick = 0;
    public int MaxStoredNetU = 100000;
    public int NetworkNetUnits;
    public Boolean[] hasRed = new Boolean[100];
    protected short _radius = 1;
    protected boolean unableToRequestTicket;
    protected ForgeChunkManager.Ticket _ticket;
    boolean gotSerpos = false;
    int ServX;
    int ServY;
    int ServZ;
    private ItemStack[] Contents = new ItemStack[4];

    public int getMaxStoredNetU() {

        return MaxStoredNetU;
    }

    public void setMaxStoredNetU(int maxStoredNetU) {
        MaxStoredNetU = maxStoredNetU;
    }

    public int getNetworkNetUnits() {

        return NetworkNetUnits;
    }

    public void setNetworkNetUnits(int networkNetUnits) {
        NetworkNetUnits = networkNetUnits;
    }

    public void updateEntity() {
        super.updateEntity();
        this.CalculateCables();
        this.calMaxNet();
        this.chunkTick();
    }

    public void CalculateCables() {

        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;
        World world = this.worldObj;
        TileEntityCable te;

        if (world.getTileEntity(x, y, z) instanceof TileEntityPowerSink) {
            if (world.getTileEntity(x + 1, y, z) instanceof TileEntityCable) {
                te = (TileEntityCable) world.getTileEntity(x + 1, y, z);
                if (te != null) {
                    if (this.gotSerpos = true) {
                        // if (te.gotSerpos == false) {
                        te.setSerpos(x, y, z, true);
                        // }
                    }
                }
            }

            if (world.getTileEntity(x - 1, y, z) instanceof TileEntityCable) {
                te = (TileEntityCable) world.getTileEntity(x - 1, y, z);
                if (te != null) {
                    if (this.gotSerpos = true) {
                        // if (te.gotSerpos == false) {
                        te.setSerpos(x, y, z, true);
                        // }
                    }
                }

            }

            if (world.getTileEntity(x, y, z + 1) instanceof TileEntityCable) {
                te = (TileEntityCable) world.getTileEntity(x, y, z + 1);
                if (te != null) {
                    if (this.gotSerpos = true) {
                        // if (te.gotSerpos == false) {
                        te.setSerpos(x, y, z, true);
                        // }
                    }
                }
            }

            if (world.getTileEntity(x, y, z - 1) instanceof TileEntityCable) {
                te = (TileEntityCable) world.getTileEntity(x, y, z - 1);
                if (te != null) {
                    if (this.gotSerpos = true) {
                        // if (te.gotSerpos == false) {
                        te.setSerpos(x, y, z, true);
                        // }
                    }
                }
            }

        }

    }

    public boolean AddNetU(int i) {
        if ((NetworkNetUnits + i) <= MaxStoredNetU) {
            this.NetworkNetUnits += i;
            return true;
        } else {
            return false;
        }

    }

    public void fillpower() {
        this.NetworkNetUnits = MaxStoredNetU;
    }

    public void RemoveNetU(int i) {
        this.NetworkNetUnits -= i;
    }

    public boolean HasNetU() {
        if (this.NetworkNetUnits != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void calMaxNet() {
        int ammoutOfPUps = 0;
        for (int i = 0; i < Contents.length; i++) {
            if (getStackInSlot(i) != null) {
                if (getStackInSlot(i).getItem() instanceof IPowerSinkUpgrade) {
                    ammoutOfPUps += getStackInSlot(i).stackSize * ((IPowerSinkUpgrade) getStackInSlot(i).getItem()).capacity();
                }
            }
        }

        MaxStoredNetU = 100000 + ammoutOfPUps;

        if (getStackInSlot(0) == null & getStackInSlot(1) == null & getStackInSlot(2) == null & getStackInSlot(3) == null) {
            MaxStoredNetU = 100000;
        }

        if (getNetworkNetUnits() > MaxStoredNetU) {
            setNetworkNetUnits(MaxStoredNetU);
        }

    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
        syncWithClient();
    }

    public void syncWithClient() {
        // checks to see if the tileenity is not no the client then sends the
        // data to the client when called.
        if (FMLCommonHandler.instance().getSide().isServer()) {
            NetworkCore.packetPipeline.sendToAll(new PacketServer(this.xCoord, this.yCoord, this.zCoord, NetworkNetUnits));
        }

        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        NetworkNetUnits = tag.getInteger("NetworkNetUnits");

        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.Contents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.Contents.length) {
                this.Contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        // syncTile();
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("NetworkNetUnits", NetworkNetUnits);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.Contents.length; ++i) {
            if (this.Contents[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.Contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("Items", nbttaglist);
    }

    @Override
    public int getSizeInventory() {
        return Contents.length;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return var1 >= this.Contents.length ? null : this.Contents[var1];
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        if (this.Contents[var1] != null) {
            ItemStack itemstack;

            if (this.Contents[var1].stackSize <= var2) {
                itemstack = this.Contents[var1];
                this.Contents[var1] = null;
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.Contents[var1].splitStack(var2);

                if (this.Contents[var1].stackSize == 0) {
                    this.Contents[var1] = null;
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        if (this.Contents[var1] != null) {
            ItemStack itemstack = this.Contents[var1];
            this.Contents[var1] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        if (var1 < this.Contents.length) {
            this.Contents[var1] = var2;

            if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
                var2.stackSize = this.getInventoryStackLimit();
            }

            this.markDirty();
        }
    }

    @Override
    public String getInventoryName() {
        return "Server";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return true;
    }

    public void UpdateServerNetwork() {
        if (upTick == 0)
            this.needsUpdate = true;
    }

    // CHUNKLOADING STUFFS

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return new int[4];
    }

    @Override
    public boolean canInsertItem(int var1, ItemStack var2, int var3) {
        return true;
    }

    @Override
    public boolean canExtractItem(int var1, ItemStack var2, int var3) {
        return true;
    }

    protected void unforceChunks() {
        Set<ChunkCoordIntPair> chunks = _ticket.getChunkList();
        if (chunks.size() == 0)
            return;

        for (ChunkCoordIntPair c : chunks)
            ForgeChunkManager.unforceChunk(_ticket, c);
    }

    protected void forceChunks() {
        if (_ticket == null)
            return;
        Set<ChunkCoordIntPair> chunks = _ticket.getChunkList();
        int x = xCoord >> 4;
        int z = zCoord >> 4;
        int r = _radius * _radius;
        for (ChunkCoordIntPair c : chunks) {
            int xS = c.chunkXPos - x;
            int zS = c.chunkZPos - z;
            if ((xS * xS + zS * zS) > r)
                ForgeChunkManager.unforceChunk(_ticket, c);
        }
        for (int xO = -_radius; xO <= _radius; ++xO) {
            int xS = xO * xO;
            for (int zO = -_radius; zO <= _radius; ++zO)
                if (xS + zO * zO <= r) {
                    ChunkCoordIntPair p = new ChunkCoordIntPair(x + xO, z + zO);
                    if (!chunks.contains(p))
                        ForgeChunkManager.forceChunk(_ticket, p);
                }
        }
    }

    public void chunkTick() {
        if (_ticket == null) {
            _ticket = ForgeChunkManager.requestPlayerTicket(NetworkCore.instance, "network:PowerSink", worldObj, ForgeChunkManager.Type.NORMAL);
            if (_ticket == null) {
                unableToRequestTicket = true;
                return;
            }
            _ticket.getModData().setInteger("X", xCoord);
            _ticket.getModData().setInteger("Y", yCoord);
            _ticket.getModData().setInteger("Z", zCoord);

        }
        forceChunks();

        // System.out.println("loaded!");
    }

}
