package modmuss50.network.app;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.app.appUtil.GuiApp;

public interface App {


	/**
	 * @return The specified name of the app
	 */
	public String getAppName();

	/**
	 * @return The specified build number / version of this app
	 */
	public String getAppVersion();


	/**
	 * Called when the app is opened
	 */
	public void start();

	/**
	 * Called every time the game is ticked
	 */
	public void update();

	//This is stuff to draw the gui of the app


	@SideOnly(Side.CLIENT)
	public void drawScreen(int par1, int par2, float par3, GuiApp gui);

	@SideOnly(Side.CLIENT)
	public void initGui(GuiApp gui);

	@SideOnly(Side.CLIENT)
	public void mouseClicked(int par1, int par2, int par3, GuiApp gui);

	@SideOnly(Side.CLIENT)
	public void keyTyped(char par1, int par2, GuiApp gui);

	@SideOnly(Side.CLIENT)
	public void onGuiClosed(GuiApp gui);
}
