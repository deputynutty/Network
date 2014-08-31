package sourceteam.network.Fmp;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.TMultiPart;
import sourceteam.network.NetworkCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPipeLine extends JItemMultiPart {
    public ItemPipeLine() {
        super();
        setCreativeTab(NetworkCore.Network);
        setUnlocalizedName("Network.parts.pipeLine");
        setTextureName("network:pipeline");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (super.onItemUse(stack, player, w, x, y, z, side, hitX, hitY, hitZ)) {
            return true;
        }
        return false;
    }

    @Override
    public TMultiPart newPart(ItemStack item, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 vhit) {
        PartPipeLine w = new PartPipeLine();
        return w;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return Multipart.Pipecodename;
    }

}
