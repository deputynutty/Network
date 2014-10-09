package modmuss50.network.app.appUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.app.App;
import modmuss50.network.client.gui.GuiComputer;
import modmuss50.network.client.gui.GuiTablet;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Mark on 02/08/2014.
 */
public class GuiApp extends GuiScreen {

	private static final ResourceLocation tabletTexture = new ResourceLocation("network:textures/gui/tablet.png");
	private static final ResourceLocation computerTexture = new ResourceLocation("network:textures/gui/computer.png");
	public App application;
	public App parent;
	public int k = (this.width - 192) / 2 + 7;
	public int b0 = 2;

	//0 pc, 1, tablet
	public int divicetype = 0;


	@SideOnly(Side.CLIENT)
	public FontRenderer fontRenderer = null;

	public GuiApp(App app, App parentapp) {
		application = app;
		parent = parentapp;
		fontRenderer = this.fontRendererObj;
	}

	public GuiApp(App app, int type) {
		application = app;
		parent = null;
		fontRenderer = this.fontRendererObj;
		divicetype = type;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if (divicetype == 1) {
			k = (this.width - 192) / 2 + 7;
			b0 = 2;

			RenderManager.instance.renderEngine.bindTexture(tabletTexture);
			this.drawTexturedModalRect(k, b0, 0, 0, 192, 256);

		}

		if (divicetype == 0) {
			int k = (this.width - 256) / 2;
			int l = (this.height - 256) / 2;
			RenderManager.instance.renderEngine.bindTexture(computerTexture);
			this.drawTexturedModalRect(k, l, 0, 0, 256, 256);
		}

		fontRenderer = this.fontRendererObj;
		if (divicetype == 0) {
			b0 = (this.height - 256) / 2;
			k = (this.width - 256) / 2;
		}
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
		if (par2 == 1) {
			if (divicetype == 0) {
				this.mc.displayGuiScreen(new GuiComputer());
			} else {
				this.mc.displayGuiScreen(new GuiTablet());
			}

		}
		application.keyTyped(par1, par2, this);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		application.onGuiClosed(this);
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

}
