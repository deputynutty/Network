package modmuss50.network.client.gui;

import modmuss50.network.app.AppManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import sourceteam.mods.lib.api.Colors;

public class GuiTablet extends GuiScreen {

    private static final ResourceLocation tabletTexture = new ResourceLocation("network:textures/gui/tablet.png");

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        int k = (this.width - 192) / 2 + 7;
        byte b0 = 2;

        RenderManager.instance.renderEngine.bindTexture(tabletTexture);
        this.drawTexturedModalRect(k, b0, 0, 0, 192, 256);


        drawString(this.fontRendererObj, "Apps:", k + 10, b0 + 10, Colors.Magenta);
    }

    @Override
    public void initGui(){
       super.initGui();
        int k = (this.width - 192) / 2;
        int l = (this.height - 192) / 2;
        //this.buttonList.clear();
        for (int i = 0; i < AppManager.apps.size(); i++) {
            this.buttonList.add(new GuiButton(i, k + 10, l + (20 * i), 20, 20, AppManager.apps.get(i).getAppName()));
            System.out.println(AppManager.apps.get(i).getAppName());
        }
    }

}
