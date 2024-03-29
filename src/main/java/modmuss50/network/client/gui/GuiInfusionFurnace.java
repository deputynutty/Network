package modmuss50.network.client.gui;

import modmuss50.mods.lib.client.GuiRenderHelper;
import modmuss50.network.blocks.containers.ContainerInfusionFurnace;
import modmuss50.network.blocks.tileentities.TileEntityInfusionFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Mark on 28/07/2014.
 */
public class GuiInfusionFurnace extends GuiContainer {
	private static final ResourceLocation GuiTextures = new ResourceLocation("network", "textures/gui/BasePoweredGui.png");
	public EntityPlayer player;
	private TileEntityInfusionFurnace tile;


	public GuiInfusionFurnace(EntityPlayer par1InventoryPlayer, TileEntityInfusionFurnace par2TileEntityFurnace) {

		super(new ContainerInfusionFurnace(par2TileEntityFurnace, par1InventoryPlayer));
		this.tile = par2TileEntityFurnace;

		xSize = 176;
		ySize = 166;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		this.mc.renderEngine.bindTexture(GuiTextures);

		String s = "Infusion Furnace";
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, GuiContants.guiColour);
		s = "Power: " + this.tile.energySystem.power + "/" + this.tile.energySystem.PowerStorageSize;
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2 + 27, 58, GuiContants.guiColour);
		if (this.tile.energySystem.getPower() != 0) {
			int i1, i2;
			i1 = this.tile.energySystem.power;
			i2 = this.tile.energySystem.PowerStorageSize;
			int perint1 = (i1 * 100);
			int perint2 = (perint1 / i2);

			s = Integer.toString(perint2) + "%";
		} else {
			s = "0%";
		}

		this.fontRendererObj.drawString(s, 120, this.ySize - 96 + 2, 4210752);

		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, GuiContants.guiColour);

		if (this.tile.timeSmelted != 0 && this.tile.maxSmeltTime != 0) {
			int i1, i2;
			i1 = this.tile.timeSmelted;
			i2 = this.tile.maxSmeltTime;
			int perint1 = (i1 * 100);
			int perint2 = (perint1 / i2);

			s = Integer.toString(perint2) + "%";
		} else {
			s = "0%";
		}

		this.fontRendererObj.drawString(s, 90, this.ySize - 130, 4210752);

	}

	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		GuiRenderHelper.drawPowerBar(k + 65, l + 54, tile.energySystem.getPowerStorageSize(), tile.energySystem.getPower(), this);
		GuiRenderHelper.drawItemContainer(k + 56 - 1, l + 35 - 1, this);
		GuiRenderHelper.drawItemContainer(k + 116 - 1, l + 35 - 1, this);
	}
}
