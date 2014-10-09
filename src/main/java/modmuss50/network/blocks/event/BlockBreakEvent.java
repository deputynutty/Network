package modmuss50.network.blocks.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import modmuss50.network.blocks.BlockBase;
import modmuss50.network.blocks.tileentities.BaseTile;
import net.minecraft.block.Block;
import net.minecraftforge.event.world.BlockEvent;

public class BlockBreakEvent {

	@SubscribeEvent
	public void blockBreakEvent(BlockEvent.BreakEvent blockEvent) {
		if (BlockBase.isBlockLocked(blockEvent.world, blockEvent.x, blockEvent.y, blockEvent.z, blockEvent.getPlayer())) {
			Block block = blockEvent.block;
			if (block instanceof BlockBase) {
				BaseTile baseTile = (BaseTile) blockEvent.world.getTileEntity(blockEvent.x, blockEvent.y, blockEvent.z);
				if (baseTile != null) {
					String playerName = blockEvent.getPlayer().getDisplayName();
					if (!baseTile.getOwner().equals("[Network]")) {
						if (baseTile.getOwner().equals(blockEvent.getPlayer().getDisplayName())) {

						} else {
							blockEvent.setCanceled(true);
						}
					}
				}
			}
		}
	}
}
