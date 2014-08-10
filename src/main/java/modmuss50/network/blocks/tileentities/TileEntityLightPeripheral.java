package modmuss50.network.blocks.tileentities;

import cpw.mods.fml.common.FMLCommonHandler;
import modmuss50.network.NetworkCore;
import modmuss50.network.api.INetworkComponent;
import modmuss50.network.netty.networkWrapper;
import modmuss50.network.netty.packets.PacketLight;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import sourceteam.mods.lib.api.IRGB;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 25/02/14 Time: 13:11
 */
public class TileEntityLightPeripheral extends BaseTile implements INetworkComponent, IRGB {

    public int red = 255;
    public int green = 255;
    public int blue = 255;

    public int dim;

    public TileEntityLightPeripheral(int dim) {
        this.dim = dim;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("red", red);
        compound.setInteger("green", green);
        compound.setInteger("blue", blue);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        red = compound.getInteger("red");
        green = compound.getInteger("green");
        blue = compound.getInteger("blue");
        //   updateBlock();
    }

    public void updateBlock() {
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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

    public void syncWithClient() {
        // checks to see if the tile enity is not no the client then sneds the
        // data to the client when called.
        if (FMLCommonHandler.instance().getSide().isServer()) {
            networkWrapper.networkWrapper.sendToAll(new PacketLight(this.xCoord, this.yCoord, this.zCoord, red, green, blue, dim));
        }
        updateBlock();
    }


    @Override
    public boolean canUpdate() {
        return true;
    }


    @Override
    public float[] getRGB() {
        return new float[]{red / 255, green / 255, blue / 255};
    }
}
