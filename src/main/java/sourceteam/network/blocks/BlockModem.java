package sourceteam.network.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourceteam.network.api.IWirelessSignalProvider;
import sourceteam.network.blocks.tileentities.TileEntityModem;

public class BlockModem extends BlockBase implements IWirelessSignalProvider {

    public BlockModem() {
        super(Material.rock);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityModem();
    }
}
