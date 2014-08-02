package modmuss50.network.client.gui;

import modmuss50.network.blocks.Containers.ContainerPowerUserBase;
import modmuss50.network.blocks.tileentities.BaseTile;
import modmuss50.network.blocks.tileentities.TileEntityPowerUserBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sourceteam.mods.lib.client.GuiRenderHelper;

public class GuiPowerUserBase extends GuiContainer {
    private static final ResourceLocation GuiTextures = new ResourceLocation("network", "textures/gui/BasePoweredGui.png");
    private TileEntityPowerUserBase tile;

    public GuiPowerUserBase(InventoryPlayer par1InventoryPlayer, TileEntityPowerUserBase par2TileEntityFurnace) {
        super(new ContainerPowerUserBase(par1InventoryPlayer, par2TileEntityFurnace));
        this.tile = par2TileEntityFurnace;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        String s = "Power Test WIP";
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, GuiContants.guiColour);
        s = "Power: " + this.tile.getCurrentPower() + "/" + this.tile.getPowerStorageSize();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2 + 27, 58, GuiContants.guiColour);

        if (this.tile.getCurrentPower() != 0) {
            int i1, i2;
            i1 = this.tile.getCurrentPower();
            i2 = this.tile.getPowerStorageSize();
            int perint1 = (i1 * 100);
            int perint2 = (perint1 / i2);

            s = Integer.toString(perint2) + "%";
        } else {
            s = "0%";
        }

        this.fontRendererObj.drawString(s, 120, this.ySize - 96 + 2, 4210752);

        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, GuiContants.guiColour);

        if (tile instanceof BaseTile) {
            BaseTile baseTile = (BaseTile) tile;
            this.fontRendererObj.drawString("Owner " + baseTile.getOwner(), 8, 20, GuiContants.guiColour);
        }
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        GuiRenderHelper.drawPowerBar(k + 65, l + 54, tile.getPowerStorageSize(), tile.getCurrentPower(), this);
    }

}
