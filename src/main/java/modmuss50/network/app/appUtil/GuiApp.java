package modmuss50.network.app.appUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.app.App;
import modmuss50.network.client.gui.GuiTablet;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Mark on 02/08/2014.
 */
public class GuiApp extends GuiScreen {

    public App application;
    public App parent;

    private static final ResourceLocation tabletTexture = new ResourceLocation("network:textures/gui/tablet.png");

    public int k = (this.width - 192) / 2 + 7;
    public int b0 = 2;

    @SideOnly(Side.CLIENT)
    public FontRenderer fontRenderer = null;

    public GuiApp(App app, App parentapp){
           application = app;
           parent = parentapp;
        fontRenderer = this.fontRendererObj;
       }

    public  GuiApp(App app){
        application = app;
        parent = null;
        fontRenderer = this.fontRendererObj;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3){
        k = (this.width - 192) / 2 + 7;
        b0 = 2;

        RenderManager.instance.renderEngine.bindTexture(tabletTexture);
        this.drawTexturedModalRect(k, b0, 0, 0, 192, 256);

        fontRenderer = this.fontRendererObj;
        application.drawScreen(par1, par2, par3, this);
        super.drawScreen(par1, par2, par3);
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
        if (par2 == 1)
        {
            this.mc.displayGuiScreen(new GuiTablet());
        }
        application.keyTyped(par1, par2, this);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        application.onGuiClosed(this);
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

}
