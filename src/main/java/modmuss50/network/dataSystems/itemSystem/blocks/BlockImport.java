package modmuss50.network.dataSystems.itemSystem.blocks;

import modmuss50.network.blocks.BlockBase;
import modmuss50.network.dataSystems.itemSystem.tileEntitys.TileEnityImport;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Mark on 11/08/2014.
 */
public class BlockImport extends BlockBase {
    public BlockImport() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEnityImport();
    }
}
