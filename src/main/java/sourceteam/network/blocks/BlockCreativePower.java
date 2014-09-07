package sourceteam.network.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourceteam.network.blocks.tileentities.TileEntityCreativePower;

public class BlockCreativePower extends BlockBase {
    public BlockCreativePower() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityCreativePower();
    }
}
