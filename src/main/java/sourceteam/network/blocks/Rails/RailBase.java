package sourceteam.network.blocks.Rails;

import net.minecraft.block.BlockRail;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class RailBase extends BlockRail {

    public RailBase() {
        super();
    }

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z) {
        return 5.65f;
    }

}
