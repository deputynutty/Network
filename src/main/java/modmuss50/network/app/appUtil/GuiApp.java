package modmuss50.network.app.appUtil;

import modmuss50.network.app.App;
import net.minecraft.client.gui.GuiScreen;

/**
 * Created by Mark on 02/08/2014.
 */
public class GuiApp extends GuiScreen {

    public App application;
    public App parent;

       public void GuiApp(App app, App parentapp){
           application = app;
           parent = parentapp;
       }

    @Override
    public void drawScreen(int par1, int par2, float par3){
        super.drawScreen(par1, par2, par3);
        application.drawScreen(par1, par2, par3, this);
    }

    @Override
    public void initGui() {
        super.initGui();
        application.initGui(this);
    }

    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        application.mouseClicked(par1, par2, par3, this);
    }

    @Override
    public void keyTyped(char par1, int par2) {
        super.keyTyped(par1, par2);
        application.keyTyped(par1, par2, this);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        application.onGuiClosed(this);
    }

}
