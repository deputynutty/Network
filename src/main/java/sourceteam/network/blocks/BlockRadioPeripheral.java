package sourceteam.network.blocks;

import sourceteam.network.api.INetworkComponent;
import sourceteam.network.api.IPeripheral;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockRadioPeripheral extends Block implements IPeripheral, INetworkComponent {

    public BlockRadioPeripheral() {
        super(Material.rock);
    }

    @Override
    public boolean canConnectViaWireless() {
        return true;
    }
}