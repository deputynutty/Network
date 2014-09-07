package sourceteam.network.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import sourceteam.network.NetworkCore;
import sourceteam.network.blocks.tileentities.TileEntityLightPeripheral;
import sourceteam.network.netty.packets.PacketLight;

import java.awt.*;

public class GuiLightPeripheral extends GuiScreen {

    private static final ResourceLocation lightPeripheralTexture = new ResourceLocation("network:textures/gui/lightPeripheral.png");
    private GuiTextField redValue, greenValue, blueValue;

    private TileEntityLightPeripheral te;

    public GuiLightPeripheral(TileEntityLightPeripheral te) {
        this.te = te;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        RenderManager.instance.renderEngine.bindTexture(lightPeripheralTexture);
        int k = (this.width - 256) / 2;
        int b0 = (this.height - 128) / 2;
        this.drawTexturedModalRectRGB(k, b0, 0, 0, 256, 128, getRGBfromString(redValue.getText()), getRGBfromString(greenValue.getText()), getRGBfromString(blueValue.getText()));

        int x = (k + 128) - (this.fontRendererObj.getStringWidth(I18n.format("tile.lightPeripheral.name")) / 2);

        this.fontRendererObj.drawString(I18n.format("tile.lightPeripheral.name"), x, 70, Color.gray.getRGB());

        int x2 = (k + 128) - (this.fontRendererObj.getStringWidth(I18n.format("network.light.dye.0")) / 2);
        int x3 = (k + 128) - (this.fontRendererObj.getStringWidth(I18n.format("network.light.dye.1")) / 2);
        int x4 = (k + 128) - (this.fontRendererObj.getStringWidth(I18n.format("network.light.dye.2")) / 2);
        int x5 = (k + 128) - (this.fontRendererObj.getStringWidth(I18n.format("network.light.dye.3")) / 2);

        this.fontRendererObj.drawString(I18n.format("network.light.dye.0"), x2, 130, Color.gray.getRGB());
        this.fontRendererObj.drawString(I18n.format("network.light.dye.1"), x3, 145, Color.gray.getRGB());
        // this.fontRendererObj.drawString(I18n.format("network.light.dye.2"),
        // x4,
        // 160, Color.gray.getRGB());
        // this.fontRendererObj.drawString(I18n.format("network.light.dye.3"),
        // x5,
        // 175, Color.gray.getRGB());

        redValue.drawTextBox();
        greenValue.drawTextBox();
        blueValue.drawTextBox();
    }

    @Override
    public void initGui() {
        redValue = new GuiTextField(this.fontRendererObj, ((this.width - 256) / 2) + 128 - 100, 100, 50, 20);
        greenValue = new GuiTextField(this.fontRendererObj, ((this.width - 256) / 2) + 128 - 25, 100, 50, 20);
        blueValue = new GuiTextField(this.fontRendererObj, ((this.width - 256) / 2) + 128 + 50, 100, 50, 20);

        redValue.setMaxStringLength(32767);
        greenValue.setMaxStringLength(32767);
        blueValue.setMaxStringLength(32767);

        redValue.setText("" + te.red);
        greenValue.setText("" + te.green);
        blueValue.setText("" + te.blue);
    }

    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        this.redValue.mouseClicked(par1, par2, par3);
        this.greenValue.mouseClicked(par1, par2, par3);
        this.blueValue.mouseClicked(par1, par2, par3);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (redValue.textboxKeyTyped(par1, par2)) {

        } else if (greenValue.textboxKeyTyped(par1, par2)) {

        } else if (blueValue.textboxKeyTyped(par1, par2)) {

        } else {
            super.keyTyped(par1, par2);
        }

        NetworkCore.packetPipeline.sendToServer(new PacketLight(te.xCoord, te.yCoord, te.zCoord, getRGBfromString("0" + redValue.getText()), getRGBfromString("0" + greenValue.getText()), getRGBfromString("0" + blueValue.getText())));
        //  NetworkCore.packetPipeline.sendToAll(new PacketLight(te.xCoord, te.yCoord, te.zCoord, getRGBfromString("0" + redValue.getText()), getRGBfromString("0" + greenValue.getText()), getRGBfromString("0" + blueValue.getText())));
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        te.red = getRGBfromString("0" + redValue.getText());
        te.green = getRGBfromString("0" + greenValue.getText());
        te.blue = getRGBfromString("0" + blueValue.getText());
        te.updateBlock();

        NetworkCore.packetPipeline.sendToServer(new PacketLight(te.xCoord, te.yCoord, te.zCoord, getRGBfromString("0" + redValue.getText()), getRGBfromString("0" + greenValue.getText()), getRGBfromString("0" + blueValue.getText())));

    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v,
     * width, height
     */
    public void drawTexturedModalRectRGB(int x, int y, int u, int v, int width, int height, int r, int g, int b) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque(r, g, b);
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), (double) this.zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) this.zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + 0) * f1));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
        tessellator.draw();
    }

    public Integer getRGBfromString(String s) {

        try {
            if (s != "") {
                if (new Integer(s) > 255) {
                    return 255;
                }

                return new Integer(s);
            }
        } catch (NumberFormatException e) {
            return 0;
        }

        return 0;
    }

}
