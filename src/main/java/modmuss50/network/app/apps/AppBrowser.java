package modmuss50.network.app.apps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.app.App;
import modmuss50.network.app.appUtil.GuiApp;
import sourceteam.mods.lib.api.Colors;

public class AppBrowser extends App {

    public AppBrowser() {
        super("Browser");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreen(int par1, int par2, float par3, GuiApp gui){
         super.drawScreen(par1, par2, par3, gui);
         gui.drawString(gui.fontRenderer, "Im a web browser", gui.k + 10, gui.b0 + 10, Colors.Magenta);
    }

}
