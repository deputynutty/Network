package sourceteam.network.blocks;

import sourceteam.network.api.INetworkComponent;
import sourceteam.network.blocks.tileentities.TileEntityCable;
import sourceteam.network.blocks.tileentities.TileEntityPowerImputCable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 24/02/14 Time: 13:12
 */
public class BlockNetworkCable extends BlockBase implements INetworkComponent {

    public BlockNetworkCable() {
        super(Material.circuits);
        // setBlockBounds(0.3F, 0.3F, 0.3F, 0.6F, 0.6F, 0.6F);
    }

    // @Override
    // public TileEntity createNewTileEntity(World var1, int var2) {
    // return new TileEntityCable();
    // }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityPowerImputCable();
    }

    // @Override
    // public boolean removedByPlayer(World world, EntityPlayer player, int x,
    // int y, int z)
    // {
    // TileEntityCable tile = (TileEntityCable) world.getTileEntity(x, y, z);
    // if(tile != null)
    // {
    // TileEntityServer powerSink = (TileEntityServer)
    // world.getTileEntity(tile.getSerX(), tile.getSerY(), tile.getSerZ());
    // if(powerSink != null)
    // {
    // powerSink.needsUpdate = true;
    // powerSink.upTick = 0;
    // System.out.println("UPDATING");
    // }
    // }
    // world.removeTileEntity(x , y, z);
    // return super.removedByPlayer(world, player, x, y, z);
    //
    // }

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

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        TileEntityCable te;
        te = (TileEntityCable) par1World.getTileEntity(par2, par3, par4);

        if (!par1World.isRemote)
            par5EntityPlayer.addChatMessage(new ChatComponentText("The Cable is conected to:" + Integer.toString(te.getSerX()) + " " + Integer.toString(te.getSerY()) + " " + Integer.toString(te.getSerZ())));

        return true;
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity) {
        // setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);

        TileEntityCable theTile = (TileEntityCable) world.getTileEntity(x, y, z);

        // if (theTile != null) {
        // if (theTile.sideCache[0] != 0) {
        // setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.7F, 0.7F);
        // super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
        // }
        // if (theTile.sideCache[1] != 0) {
        // setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 1.0F, 0.7F);
        // super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
        // }
        // if (theTile.sideCache[2] != 0) {
        // setBlockBounds(0.3F, 0.3F, 0.0F, 0.7F, 0.7F, 0.7F);
        // super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
        // }
        // if (theTile.sideCache[3] != 0) {
        // setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 1.0F);
        // super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
        // }
        // if (theTile.sideCache[4] != 0) {
        // setBlockBounds(0.0F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
        // super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
        // }
        // if (theTile.sideCache[5] != 0) {
        // setBlockBounds(0.3F, 0.3F, 0.3F, 1.0F, 0.7F, 0.7F);
        // super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
        // }
        // }
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);

        TileEntityCable theTile = (TileEntityCable) world.getTileEntity(x, y, z);

        if (theTile != null) {
            if (theTile.sideCache[1] != 0 & theTile.sideCache[0] != 0) {
                setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
                super.setBlockBoundsBasedOnState(world, x, y, z);
                return;
            }
            if (theTile.sideCache[0] != 0) {
                setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.7F, 0.7F);
                super.setBlockBoundsBasedOnState(world, x, y, z);
                return;
            }
            if (theTile.sideCache[1] != 0) {
                setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 1.0F, 0.7F);
                super.setBlockBoundsBasedOnState(world, x, y, z);
                return;
            }
            if (theTile.sideCache[4] != 0 & theTile.sideCache[5] != 0) {
                setBlockBounds(0.0F, 0.3F, 0.3F, 1.0F, 0.7F, 0.7F);
                super.setBlockBoundsBasedOnState(world, x, y, z);
                return;
            }
            if (theTile.sideCache[2] != 0 & theTile.sideCache[3] != 0) {
                setBlockBounds(0.3F, 0.3F, 0.0F, 0.7F, 0.7F, 1.0F);
                super.setBlockBoundsBasedOnState(world, x, y, z);
                return;
            }
            if (theTile.sideCache[2] != 0) {
                setBlockBounds(0.3F, 0.3F, 0.0F, 0.7F, 0.7F, 0.7F);
                super.setBlockBoundsBasedOnState(world, x, y, z);
                return;
            }
            if (theTile.sideCache[3] != 0) {
                setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 1.0F);
                super.setBlockBoundsBasedOnState(world, x, y, z);
                return;
            }
            if (theTile.sideCache[4] != 0) {
                setBlockBounds(0.0F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
                super.setBlockBoundsBasedOnState(world, x, y, z);
                return;
            }
            if (theTile.sideCache[5] != 0) {
                setBlockBounds(0.3F, 0.3F, 0.3F, 1.0F, 0.7F, 0.7F);
                super.setBlockBoundsBasedOnState(world, x, y, z);
                return;
            }
        }
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        super.setBlockBoundsBasedOnState(world, x, y, z);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        TileEntityCable thiscable = (TileEntityCable) world.getTileEntity(x, y, z);
        if (thiscable != null) {
            thiscable.updateCables(world, x, y, z);
        } else {
            System.out.println("TILE = NULL");
        }
        super.onBlockAdded(world, x, y, z);

    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        TileEntityCable thiscable = (TileEntityCable) world.getTileEntity(x, y, z);
        if (thiscable != null) {
            thiscable.updateCables(world, x, y, z);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);
        TileEntityCable thiscable = (TileEntityCable) world.getTileEntity(x, y, z);
        if (thiscable != null) {
            thiscable.updateCables(world, x, y, z);
        }
    }
}
