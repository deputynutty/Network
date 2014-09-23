package modmuss50.network.dataSystems.itemSystem.blocks;

import modmuss50.network.blocks.BlockBase;
import modmuss50.network.dataSystems.itemSystem.tileEntitys.TileEntityExport;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class BlockExport extends BlockBase {
    public BlockExport() {
        super(Material.anvil);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityExport();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if(player.inventory.getCurrentItem() != null){
            TileEntityExport tileEntityExport = (TileEntityExport) world.getTileEntity(x, y, z);
            tileEntityExport.stackToFind = player.inventory.getCurrentItem();
            return true;
        }
        return false;
    }
}
