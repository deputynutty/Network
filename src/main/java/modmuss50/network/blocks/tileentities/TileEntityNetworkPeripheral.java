package modmuss50.network.blocks.tileentities;


import modmuss50.network.blocks.NetworkBlocks;
import modmuss50.network.items.NetworkItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import modmuss50.network.client.particles.NetworkParticleHelper;

public class TileEntityNetworkPeripheral extends BaseTile {

    public int modemX, modemY, modemZ;

    public boolean hasModem = true;

    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.armorInventory[3];
            if (stack != null && stack.getItem() == NetworkItems.wifiGoggles && hasModem && worldObj.getBlock(modemX, modemY, modemZ) == NetworkBlocks.modem) {
                NetworkParticleHelper.runWifiFX(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, modemX + 0.5, modemY + 0.5, modemZ + 0.5, 1F, 1F, 1F, 10);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        System.out.println("Saving NBT For Network Peripheral");

        System.out.println("The X Pos Of The Modem: " + modemX);
        System.out.println("The Y Pos Of The Modem: " + modemY);
        System.out.println("The Z Pos Of The Modem: " + modemZ);

        System.out.println("Have we been connected to a modem? " + hasModem);

        compound.setInteger("modemX", modemX);
        compound.setInteger("modemY", modemY);
        compound.setInteger("modemZ", modemZ);

        compound.setBoolean("hasModem", hasModem);

        // NetworkCore.packetPipeline.sendToServer(new PacketWifi(xCoord,
        // yCoord, zCoord, modemX, modemY, modemZ));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        modemX = compound.getInteger("modemX");
        modemY = compound.getInteger("modemY");
        modemZ = compound.getInteger("modemZ");

        hasModem = compound.getBoolean("hasModem");

        System.out.println("Reading NBT For Network Peripheral");

        System.out.println(modemX);
        System.out.println(modemY);
        System.out.println(modemZ);

        System.out.println(hasModem);

        // NetworkCore.packetPipeline.sendToServer(new PacketWifi(xCoord,
        // yCoord, zCoord, modemX, modemY, modemZ));
    }

}
