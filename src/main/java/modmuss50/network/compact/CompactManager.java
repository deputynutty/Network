package modmuss50.network.compact;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;

public class CompactManager {

	public static void init() {

		if (Loader.isModLoaded("ForgeMultipart")) {
			CommonFMP.init();
		}

		if (Loader.isModLoaded("NotEnoughItems") && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ClientNEI.init();
		}

	}

}
