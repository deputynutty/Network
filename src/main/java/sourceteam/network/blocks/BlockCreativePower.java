package sourceteam.network.blocks;

import sourceteam.network.blocks.tileentities.TileEntityCreativePower;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCreativePower extends BlockBase {
    public BlockCreativePower() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityCreativePower();
    }
}
