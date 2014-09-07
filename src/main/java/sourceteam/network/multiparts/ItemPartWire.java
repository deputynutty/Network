package sourceteam.network.multiparts;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sourceteam.network.NetworkCore;

/**
 * Created by Mark on 05/08/2014.
 */
public class ItemPartWire extends JItemMultiPart {

    public ItemPartWire() {
        super();
        setCreativeTab(NetworkCore.Network);
        setUnlocalizedName("Network.parts.wire");
        setTextureName("network:wire");
    }

    @Override
    public TMultiPart newPart(ItemStack itemStack, EntityPlayer player, World world, BlockCoord blockCoord, int i, Vector3 vector3) {
        PartWire w = (PartWire) MultiPartRegistry.createPart(Multipart.wireName, false);
        return w;
    }
}
