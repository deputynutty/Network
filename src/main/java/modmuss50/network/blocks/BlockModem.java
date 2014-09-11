package modmuss50.network.blocks;

import modmuss50.network.api.IWirelessSignalProvider;
import modmuss50.network.blocks.tileentities.TileEntityModem;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockModem extends BlockBase implements IWirelessSignalProvider {

    public BlockModem() {
        super(Material.rock);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityModem();
    }
}
