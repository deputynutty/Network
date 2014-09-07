package sourceteam.network.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sourceteam.mods.lib.client.GuiRenderHelper;
import sourceteam.network.blocks.Containers.ContinerBlockMover;
import sourceteam.network.blocks.tileentities.TileEntityMover;

public class GuiBlockMover extends GuiContainer {
    private static final ResourceLocation GuiTextures = new ResourceLocation("network", "textures/gui/BasePoweredGui.png");
    private TileEntityMover tile;

    public GuiBlockMover(InventoryPlayer par1InventoryPlayer, TileEntityMover par2TileEntityFurnace) {
        super(new ContinerBlockMover(par1InventoryPlayer, par2TileEntityFurnace));
        this.tile = par2TileEntityFurnace;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        String s = "Block Mover";
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2 + 50, 6, GuiContants.guiColour);
        s = "Power: " + this.tile.getCurrentPower() + "/" + this.tile.getPowerStorageSize();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2 - 35, 68, GuiContants.guiColour);

        s = "blocks: " + this.tile.blocksToMove();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2 - 60, 52, GuiContants.guiColour);

    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        GuiRenderHelper.drawPowerBar(k + 5, l + 64, tile.getPowerStorageSize(), tile.getCurrentPower(), this);

        GuiRenderHelper.drawItemContainer(k + 8 - 1, l + 14 - 1, this); // direction

        GuiRenderHelper.drawItemContainer(k + 50 - 1, l + 14 - 1, this); // up
        GuiRenderHelper.drawItemContainer(k + 50 - 1, l + 32 - 1, this); // down

        GuiRenderHelper.drawItemContainer(k + 90 - 1, l + 14 - 1, this); // north

        GuiRenderHelper.drawItemContainer(k + 72 - 1, l + 32 - 1, this); // west
        GuiRenderHelper.drawItemContainer(k + 108 - 1, l + 32 - 1, this);

        GuiRenderHelper.drawItemContainer(k + 90 - 1, l + 50 - 1, this);

    }
}
