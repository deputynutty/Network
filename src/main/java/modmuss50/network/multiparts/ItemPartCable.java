package modmuss50.network.multiparts;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import modmuss50.network.NetworkCore;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPartCable extends JItemMultiPart {
    public ItemPartCable() {
        super();
        setCreativeTab(NetworkCore.Network);
        setUnlocalizedName("Network.parts.cable");
        setTextureName("network:networkCable");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (super.onItemUse(stack, player, w, x, y, z, side, hitX, hitY, hitZ)) {
            w.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, Block.soundTypeGlass.getStepResourcePath(), Block.soundTypeGlass.getVolume() * 5.0F, Block.soundTypeGlass.getPitch() * .9F);
            return true;
        }
        return false;
    }

    @Override
    public TMultiPart newPart(ItemStack item, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 vhit) {
        PartCable w = (PartCable) MultiPartRegistry.createPart(Multipart.codename, false);
        return w;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return Multipart.codename;
    }

}
