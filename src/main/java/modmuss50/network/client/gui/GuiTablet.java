package modmuss50.network.client.gui;

import modmuss50.network.app.AppManager;
import modmuss50.network.app.appUtil.GuiApp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import sourceteam.mods.lib.api.Colors;

public class GuiTablet extends GuiScreen {

	private static final ResourceLocation tabletTexture = new ResourceLocation("network:textures/gui/tablet.png");

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		int k = (this.width - 192) / 2;
		int l = (this.height - 256) / 2;
		RenderManager.instance.renderEngine.bindTexture(tabletTexture);
		this.drawTexturedModalRect(k, l, 0, 0, 192, 256);
		drawString(this.fontRendererObj, "Apps:", k + 10, l + 10, Colors.Magenta);
		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void initGui() {
		int k = (this.width - 192) / 2;
		int l = (this.height - 256) / 2;
		this.buttonList.clear();
		for (int i = 0; i < AppManager.apps.size(); i++) {
			this.buttonList.add(new GuiButton(i, k + 15, l + (20 * i) + 20, 70, 20, AppManager.apps.get(i).getAppName()));
		}
		super.initGui();
	}

	public boolean doesGuiPauseGame() {
		return false;
	}


	public void actionPerformed(GuiButton button) {
		for (int i = 0; i < AppManager.apps.size(); i++) {
			if (i == button.id) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiApp(AppManager.apps.get(i), 1));
			}
		}
	}


}
