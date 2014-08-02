package modmuss50.network.client.gui;

import modmuss50.network.blocks.Containers.ContainerPowerSink;
import modmuss50.network.blocks.tileentities.TileEntityPowerSink;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sourceteam.mods.lib.client.GuiRenderHelper;

public class GuiPowerSink extends GuiContainer {
    private static final ResourceLocation GuiTextures = new ResourceLocation("network", "textures/gui/BasePoweredGui.png");
    private TileEntityPowerSink tile;

    public GuiPowerSink(InventoryPlayer par1InventoryPlayer, TileEntityPowerSink par2TileEntityFurnace) {
        super(new ContainerPowerSink(par1InventoryPlayer, par2TileEntityFurnace));
        this.tile = par2TileEntityFurnace;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        String s = "Power Sink";

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, GuiContants.guiColour);
        s = "   Power: " + this.tile.getNetworkNetUnits();

        this.fontRendererObj.drawString(s, 8, 25, GuiContants.guiColour);

        s = "Max Size: " + this.tile.getMaxStoredNetU();
        this.fontRendererObj.drawString(s, 8, 15, GuiContants.guiColour);

        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, GuiContants.guiColour);
    }

    public void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int y = 4;
        GuiRenderHelper.drawItemContainer(k + 150 - 1, l + y, this);
        y += 18;
        GuiRenderHelper.drawItemContainer(k + 150 - 1, l + y, this);
        y += 18;
        GuiRenderHelper.drawItemContainer(k + 150 - 1, l + y, this);
        y += 18;
        GuiRenderHelper.drawItemContainer(k + 150 - 1, l + y, this);

        GuiRenderHelper.drawPowerBar(k + 8, l + 35, tile.getMaxStoredNetU(), tile.getNetworkNetUnits(), this);
    }

}
