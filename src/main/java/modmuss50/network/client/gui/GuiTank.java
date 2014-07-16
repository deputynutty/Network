package modmuss50.network.client.gui;

import modmuss50.network.blocks.Containers.ContainerFluidGen;
import modmuss50.network.blocks.tileentities.BaseTile;
import modmuss50.network.blocks.tileentities.TileEntityFluidGen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sourceteam.mods.lib.client.GuiRenderHelper;

public class GuiTank extends GuiContainer {
	private static final ResourceLocation	GuiTextures	= new ResourceLocation("network", "textures/gui/BasePoweredGui.png");
	private TileEntityFluidGen				tile;

	public GuiTank(InventoryPlayer par1InventoryPlayer, TileEntityFluidGen par2TileEntityFurnace) {
		super(new ContainerFluidGen(par1InventoryPlayer, par2TileEntityFurnace));
		this.tile = par2TileEntityFurnace;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		String s = "Lava Heater";
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, GuiContants.guiColour);

		s = Integer.toString(tile.tank.getFluidAmount()) + "/" + Integer.toString(tile.tank.getCapacity());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2 - 30, 20, GuiContants.guiColour);

		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, GuiContants.guiColour);

		if (tile instanceof BaseTile) {
			BaseTile baseTile = (BaseTile) tile;
			this.fontRendererObj.drawString("Owner: " + baseTile.getOwner(), 28, 28, GuiContants.guiColour);
		}
	}

	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		GuiRenderHelper.drawTankGui(k + 10, l + 10, tile, this);

	}

}
