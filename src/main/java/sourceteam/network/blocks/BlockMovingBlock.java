package sourceteam.network.blocks;

import sourceteam.network.blocks.tileentities.TileEntityMovingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMovingBlock extends BlockBase {

    public BlockMovingBlock() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityMovingBlock();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

}
