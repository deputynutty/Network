package modmuss50.network.init;

import cpw.mods.fml.common.registry.EntityRegistry;
import modmuss50.network.NetworkCore;
import modmuss50.network.entity.robot.EntityDrone;

/**
 * Created by mark on 18/09/2014.
 */
public class initEntitys {

    public static void initEntitys(){
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
      //  EntityRegistry.registerGlobalEntityID(EntityDrone.class, "EntityDrone", entityID);
      //  EntityRegistry.registerModEntity(EntityDrone.class, "EntityDrone", entityID, NetworkCore.instance, 64, 1, true);
    }


}
