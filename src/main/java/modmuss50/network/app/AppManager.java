package modmuss50.network.app;

import cpw.mods.fml.common.FMLLog;
import modmuss50.network.app.apps.AppBrowser;
import modmuss50.network.app.apps.AppFileExplorer;
import modmuss50.network.app.apps.AppSettings;

import java.util.ArrayList;

public class AppManager {

	public static ArrayList<App> apps = new ArrayList<App>();

	public static void init() {
		FMLLog.info("[NetworkAppManager] Initialising app database");

		addApp(new AppSettings());
		addApp(new AppBrowser());
		addApp(new AppFileExplorer());
	}

	public static void addApp(App app) {
		apps.add(app);
		FMLLog.info("[NetworkAppManager] Adding " + app.getAppName() + " to app database");
	}
}
