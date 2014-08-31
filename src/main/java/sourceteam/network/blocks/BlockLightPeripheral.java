package sourceteam.network.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sourceteam.network.NetworkCore;
import sourceteam.network.api.IPeripheral;
import sourceteam.network.blocks.tileentities.TileEntityLightPeripheral;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourceteam.mods.core.client.ClientInit;
import sourceteam.mods.lib.api.ColoredNetworkBlock;

import java.awt.*;

public class BlockLightPeripheral extends ColoredNetworkBlock implements IPeripheral, ITileEntityProvider {

    public BlockLightPeripheral() {
        super(Material.rock, "network:lightPeripheral");
        // super(Material.rock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess iba, int x, int y, int z) {
        TileEntity te = iba.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityLightPeripheral) {
            TileEntityLightPeripheral telp = (TileEntityLightPeripheral) te;

            return new Color(telp.red, telp.green, telp.blue).getRGB();
        } else {
            return 0;
        }
    }

    @Override
    public boolean canConnectViaWireless() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityLightPeripheral();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem();
        Item item = stack != null ? stack.getItem() : null;
        if (item == null || (item != null && item != Items.dye)) {
            player.openGui(NetworkCore.instance, 1, world, x, y, z);
            TileEntity te = world.getTileEntity(x, y, z);
            TileEntityLightPeripheral telp = (TileEntityLightPeripheral) te;
            //   telp.syncWithClient();
        } else if (item != null && item == Items.dye) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null && te instanceof TileEntityLightPeripheral) {
                TileEntityLightPeripheral telp = (TileEntityLightPeripheral) te;
                telp.getDescriptionPacket();
                if (player.isSneaking()) {
                    if (stack.getItemDamage() == 1 && telp.red != 0) {
                        setColourRGB(telp, telp.red - 1, telp.green, telp.blue);
                    } else if (stack.getItemDamage() == 2 && telp.green != 0) {
                        setColourRGB(telp, telp.red, telp.green - 1, telp.blue);
                    } else if (stack.getItemDamage() == 4 && telp.blue >= 10) {
                        setColourRGB(telp, telp.red, telp.green, telp.blue - 1);
                    }

                } else {
                    if (stack.getItemDamage() == 1 && telp.red != 255) {
                        setColourRGB(telp, telp.red + 1, telp.green, telp.blue);
                    } else if (stack.getItemDamage() == 2 && telp.green != 255) {
                        setColourRGB(telp, telp.red, telp.green + 1, telp.blue);
                    } else if (stack.getItemDamage() == 4 && telp.blue != 255) {
                        setColourRGB(telp, telp.red, telp.green, telp.blue + 1);
                    }
                }
            }
        }
        return true;
    }


    private void setColourRGB(TileEntityLightPeripheral telp, int red, int green, int blue) {
        telp.red = red;
        telp.green = green;
        telp.blue = blue;

        telp.syncWithServer();
    }

    private void setColourHex(TileEntityLightPeripheral telp, int hex) {
        Color color = new Color(hex);
        telp.red = color.getRed() / 255;
        telp.green = color.getGreen() / 255;
        telp.blue = color.getBlue() / 255;

        telp.syncWithServer();
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
    public int getRenderType() {
        return ClientInit.render;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

}
