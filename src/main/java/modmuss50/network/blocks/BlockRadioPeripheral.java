package modmuss50.network.blocks;

import modmuss50.network.api.IPeripheral;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockRadioPeripheral extends Block implements IPeripheral {

    public BlockRadioPeripheral() {
        super(Material.rock);
    }

    @Override
    public boolean canConnectViaWireless() {
        return true;
    }
}
