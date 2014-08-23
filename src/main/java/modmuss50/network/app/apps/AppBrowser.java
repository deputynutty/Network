package modmuss50.network.app.apps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.app.App;
import modmuss50.network.app.appUtil.GuiApp;
import sourceteam.mods.lib.api.Colors;

public class AppBrowser implements App {


    @Override
    public String getAppName() {
        return null;
    }

    @Override
    public String getAppVersion() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreen(int par1, int par2, float par3, GuiApp gui) {
        gui.drawString(gui.fontRenderer, "Im a web browser", gui.k + 10, gui.b0 + 10, Colors.Magenta);
    }

    @Override
    public void initGui(GuiApp gui) {

    }

    @Override
    public void mouseClicked(int par1, int par2, int par3, GuiApp gui) {

    }

    @Override
    public void keyTyped(char par1, int par2, GuiApp gui) {

    }

    @Override
    public void onGuiClosed(GuiApp gui) {

    }

}
