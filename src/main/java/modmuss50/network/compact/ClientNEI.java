package modmuss50.network.compact;


import codechicken.nei.api.API;
import modmuss50.network.compact.NEI.InfusionNei;

public class ClientNEI {

    public static void init(){
        API.registerRecipeHandler(new InfusionNei());
    }

}
