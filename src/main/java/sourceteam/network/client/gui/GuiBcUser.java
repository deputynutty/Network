package sourceteam.network.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GuiBcUser extends GuiContainer {
    public GuiBcUser(Container par1Container) {
        super(par1Container);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

    }
    // private static final ResourceLocation GuiTextures = new ResourceLocation(
    // "network", "textures/gui/BasePoweredGui.png");
    // private TileEntityBCUser tile;
    //
    // public GuiBcUser(InventoryPlayer par1InventoryPlayer,
    // TileEntityBCUser par2TileEntityFurnace) {
    // super(new ContainerBcUser(par1InventoryPlayer,
    // par2TileEntityFurnace));
    // this.tile = par2TileEntityFurnace;
    // }
    //
    // /**
    // * Draw the foreground layer for the GuiContainer (everything in front of
    // * the items)
    // */
    // protected void drawGuiContainerForegroundLayer(int p_146979_1_,
    // int p_146979_2_) {
    // String s = "Power Test WIP";
    // this.fontRendererObj.drawString(s, this.xSize / 2
    // - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
    // s = "Power: " + this.tile.powerHandler.getEnergyStored() + "/"
    // + this.tile.powerHandler.getMaxEnergyStored();
    // this.fontRendererObj.drawString(s, this.xSize / 2
    // - this.fontRendererObj.getStringWidth(s) / 2 + 27, 58, 4210752);
    //
    // if (this.tile.powerHandler.getEnergyStored() != 0) {
    // int i1, i2;
    // i1 = (int) this.tile.powerHandler.getEnergyStored();
    // i2 = (int) this.tile.powerHandler.getMaxEnergyStored();
    // int perint1 = (i1 * 100);
    // int perint2 = (perint1 / i2);
    //
    // s = Integer.toString(perint2) + "%";
    // } else {
    // s = "0%";
    // }
    //
    // this.fontRendererObj.drawString(s, 120, this.ySize - 96 + 2, 4210752);
    //
    // this.fontRendererObj.drawString(
    // I18n.format("container.inventory", new Object[0]), 8,
    // this.ySize - 96 + 2, 4210752);
    // }
    //
    // protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
    // int p_146976_2_, int p_146976_3_) {
    // GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    // this.mc.getTextureManager().bindTexture(GuiTextures);
    // int k = (this.width - this.xSize) / 2;
    // int l = (this.height - this.ySize) / 2;
    // this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    // GuiRenderHelper.drawPowerBar(k + 65, l + 54,
    // (int) tile.powerHandler.getMaxEnergyStored(),(int)
    // tile.powerHandler.getEnergyStored(), this);
    // }
}
