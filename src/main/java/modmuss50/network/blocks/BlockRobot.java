package modmuss50.network.blocks;

import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityRobot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRobot extends BlockBase {

    public int mk;

    public BlockRobot(int mk) {
        super(Material.rock);
        setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.6F, 0.8F);

        this.mk = mk;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        player.openGui(NetworkCore.instance, 2, world, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityRobot();
    }
}
