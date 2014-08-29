package modmuss50.network.blocks;

import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityTeleporter;
import modmuss50.network.netty.packets.PacketSetTeleporterFQ;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import modmuss50.mods.lib.Location;

/**
 * Created by Mark on 19/04/14.
 */
public class BlockTeleporter extends BlockBase {


    public BlockTeleporter() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityTeleporter();
    }


    @Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        if (par1World.getTileEntity(x, y, z) instanceof TileEntityTeleporter)

            if (par1World.isRemote)
                NetworkCore.packetPipeline.sendToAll(new PacketSetTeleporterFQ(new Location(x, y, z), ((TileEntityTeleporter) par1World.getTileEntity(x, y, z)).fq));

        par5EntityPlayer.openGui(NetworkCore.instance, 11, par1World, x, y, z);
        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }


    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }


}
