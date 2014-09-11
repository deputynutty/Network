package modmuss50.network.blocks;

import modmuss50.network.api.INetworkComponent;
import modmuss50.network.api.IPeripheral;
import modmuss50.network.blocks.tileentities.TileEntityNetworkPeripheral;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNetworkPeripheral extends BlockBase implements IPeripheral, INetworkComponent {

    public BlockNetworkPeripheral() {
        super(Material.rock);
    }

    @Override
    public boolean canConnectViaWireless() {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityNetworkPeripheral();
    }
}
