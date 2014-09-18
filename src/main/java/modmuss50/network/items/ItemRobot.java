package modmuss50.network.items;

import modmuss50.network.entity.robot.EntityRobot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by mark on 18/09/2014.
 */
public class ItemRobot extends Item {

    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {

        EntityRobot robot= new EntityRobot(world);
        robot.setPosition(x, y + 2, z);
        if(!world.isRemote)
        world.spawnEntityInWorld(robot);
        return true;
    }
}
