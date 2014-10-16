package modmuss50.network.client.gui;

import modmuss50.mods.lib.api.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiPinPad extends GuiScreen {

	public int x, y, z;
	public World world;
	public EntityPlayer player;


	public GuiPinPad(int x, int y, int z, World world, EntityPlayer player) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.player = player;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {

	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		drawString(Minecraft.getMinecraft().fontRenderer, player.getDisplayName(), 10, 10, Colors.White);
	}


	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();

	}
}
