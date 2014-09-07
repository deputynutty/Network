package sourceteam.network.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourceteam.network.blocks.tileentities.TileEntityPump;

/**
 * Created by Mark on 28/07/2014.
 */
public class BlockPump extends BlockBase {

    public BlockPump() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityPump();
    }
}
