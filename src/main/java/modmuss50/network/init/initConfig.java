package modmuss50.network.init;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class initConfig {
	private static Configuration	config;

	private static void initBlocks() {

	}

	private static void initItems() {

	}

	private static void initGuis() {

	}

	private static void initEnergy() {

	}

	public static void loadConfig(File file) {
		config = new Configuration(file);

		initBlocks();
		initItems();
		initGuis();
		initEnergy();
	}
}
