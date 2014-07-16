package modmuss50.network.loaders;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class LoadConfig {
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
