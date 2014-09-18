package modmuss50.network.init;

import cpw.mods.fml.common.registry.EntityRegistry;
import modmuss50.network.NetworkCore;
import modmuss50.network.entity.robot.EntityRobot;

/**
 * Created by mark on 18/09/2014.
 */
public class initEntitys {

    public static void initEntitys(){
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityRobot.class, "EntityRobot", entityID);
        EntityRegistry.registerModEntity(EntityRobot.class, "EntityRobot", entityID, NetworkCore.instance, 64, 1, true);
    }


}
