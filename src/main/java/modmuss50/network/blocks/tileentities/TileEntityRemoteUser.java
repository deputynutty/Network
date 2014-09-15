package modmuss50.network.blocks.tileentities;

import cpw.mods.fml.common.Loader;
import modmuss50.network.api.IRemoteTile;
import modmuss50.network.api.power.IEnergyFace;
import modmuss50.network.blocks.WorldCoordinate;
import modmuss50.network.compact.FMP.PartCable;
import modmuss50.network.netty.ChannelHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class TileEntityRemoteUser extends BaseTile implements IInventory {

    public ArrayList<TileEntity> remotetiles = new ArrayList<TileEntity>();

    public void updateEntity() {
        super.updateEntity();
        remotetiles.clear();
        findRemoteTile();

        // for (int i = 0; i < remotetiles.size(); i++) {
        // System.out.println(remotetiles.get(i).getBlockType().getUnlocalizedName());
        // }
    }

    public void findRemoteTile() {
        int BlockX = this.xCoord;
       int BlockY = this.yCoord;
       int BlockZ = this.zCoord;
       World World = this.getWorldObj();

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
                                    TileEntity tileEntity = World.getTileEntity(target.getX(), target.getY(), target.getZ());
                                    if (tileEntity != null && tileEntity instanceof IRemoteTile) {
                                        remotetiles.add(tileEntity);

                                    } else {

                                    }

                                    if (isCable(tile) && target.getDepth() < cableMaxLenghth) {
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

    // ####INV STUFFS

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {

    }

    @Override
    public String getInventoryName() {
        return "RemoteUser";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return false;
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

    public boolean isCable(TileEntity tile) {
        if (Loader.isModLoaded("ForgeMultipart")) {
            return PartCable.isCable(tile);
        }

        if (tile instanceof IEnergyFace)
            return true;

        return tile instanceof TileEntityCable;
    }
}
