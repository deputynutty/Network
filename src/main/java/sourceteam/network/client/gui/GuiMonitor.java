package sourceteam.network.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import sourceteam.network.blocks.tileentities.TileEntityMonitor;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 02/03/14 Time: 19:27
 */
public class GuiMonitor extends GuiScreen {

    public static final ResourceLocation texture = new ResourceLocation("network:textures/gui/monitor.png");
    public GuiTextField input;
    public TileEntityMonitor te;
    private GuiButton runBtn;

    public GuiMonitor(TileEntityMonitor te) {
        super();
        this.te = te;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        RenderManager.instance.renderEngine.bindTexture(texture);
        int k = (this.width - 256) / 2;
        int b0 = (this.height - 220) / 2;
        this.drawTexturedModalRect(k, b0, 0, 0, 256, 220);
        input.drawTextBox();

        this.fontRendererObj.drawString("1>> " + te.l0, 100, 20, 4210752);
        this.fontRendererObj.drawString("2>> " + te.l1, 100, 30, 4210752);
        this.fontRendererObj.drawString("3>> " + te.l2, 100, 40, 4210752);
        this.fontRendererObj.drawString("4>> " + te.l3, 100, 50, 4210752);
        this.fontRendererObj.drawString("5>> " + te.l4, 100, 60, 4210752);
        this.fontRendererObj.drawString("6>> " + te.l5, 100, 70, 4210752);
        this.fontRendererObj.drawString("7>> " + te.l6, 100, 80, 4210752);
        this.fontRendererObj.drawString("8>> " + te.l7, 100, 90, 4210752);
        this.fontRendererObj.drawString("9>> " + te.l8, 100, 100, 4210752);
        this.fontRendererObj.drawString("10>> " + te.l9, 94, 110, 4210752);

    }

    @Override
    public void initGui() {
        super.initGui();
        input = new GuiTextField(this.fontRendererObj, ((this.width - 256) / 2) + 128 - 110, 200, 140, 20);
        input.setMaxStringLength(32767);
        // this.buttonList.clear();
        // this.buttonList.add(new GuiButton(0, (this.width - 100), (this.height
        // - 100), "RUN"));
        this.buttonList.add(new GuiButton(0, (100), (100), "RUN"));
    }

    public void actionPerformed(GuiButton button) {
        System.out.println("hi");

        // if (button.id == 0)
        // {
        if (input.getText() != null) {
            te.processCommand(input.getText());
            input.setText("");
        }
        // }

    }

    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        this.input.mouseClicked(par1, par2, par3);

    }

    @Override
    public void keyTyped(char par1, int par2) {
        if (input.textboxKeyTyped(par1, par2)) {
            te.processCommand(input.getText());
        } else {

            super.keyTyped(par1, par2);
        }

    }

}
