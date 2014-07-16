package modmuss50.network.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class GuiTablet extends GuiScreen {

	private static final ResourceLocation	tabletTexture	= new ResourceLocation("network:textures/gui/tablet.png");

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		RenderManager.instance.renderEngine.bindTexture(tabletTexture);
		int k = (this.width - 192) / 2 + 7;
		byte b0 = 2;
		this.drawTexturedModalRect(k, b0, 0, 0, 192, 256);
	}
}
