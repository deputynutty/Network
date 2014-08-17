package modmuss50.network.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import org.lwjgl.opengl.GL11;


public class GuiRenderHelper {

    public static void drawItemContainer(int x, int y, Gui gui) {
        int texlocx = 100;
        int texlocy = 166;
        gui.drawTexturedModalRect(x, y, texlocx, texlocy, 18, 18);
    }

    public static final ResourceLocation GuiTextures = new ResourceLocation("network", "textures/gui/BasePoweredGui.png");

    public static void drawTankGui(int x, int y, IFluidHandler tile, GuiContainer gui) {
        gui.drawTexturedModalRect(x, y, 176, 0, 16, 60);
        int _tankSizeMax = 60;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        FluidTankInfo[] tanks = tile.getTankInfo(ForgeDirection.UNKNOWN);
        int n = tanks.length > 3 ? 3 : tanks.length;
        if (n > 0) {
            for (int i = 0; i < n; ++i) {
                if (tanks[i].fluid == null)
                    continue;
                int tankSize = tanks[i].fluid.amount * _tankSizeMax / tanks[i].capacity;
                drawTank(x - (i * 20), y + _tankSizeMax, tanks[i].fluid, tankSize, gui);
            }
        }
        gui.mc.getTextureManager().bindTexture(GuiTextures);

        gui.drawTexturedModalRect(x, y, 194, 0, 16, 60);
    }

    public static void drawTank(int xOffset, int yOffset, FluidStack stack, int level, GuiContainer gui) {
        if (stack == null)
            return;
        Fluid fluid = stack.getFluid();
        if (fluid == null)
            return;

        IIcon icon = fluid.getIcon(stack);
        if (icon == null)
            icon = Blocks.flowing_lava.getIcon(0, 0);

        int vertOffset = 0;

        while (level > 0) {
            int texHeight = 0;

            if (level > 16) {
                texHeight = 16;
                level -= 16;
            } else {
                texHeight = level;
                level = 0;
            }

            bindTexture(fluid, gui);

            gui.drawTexturedModelRectFromIcon(xOffset, yOffset - texHeight - vertOffset, icon, 16, texHeight);
            vertOffset = vertOffset + 16;
        }

    }

    public static void bindTexture(Fluid fluid, GuiContainer gui) {
        if (fluid.getSpriteNumber() == 0)
            gui.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        else
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, fluid.getSpriteNumber());
    }

    public static void drawPowerBar(int x, int y, int Max, int current, GuiScreen gui) {

        gui.drawTexturedModalRect(x, y, 0, 182, 100, 16);

        if (Max == current) {
            gui.drawTexturedModalRect(x, y, 0, 166, 100, 16);
            return;
        }

        if (modmuss50.network.util.Math.percentage(Max, current) != 0) {
            gui.drawTexturedModalRect(x, y, 0, 166, modmuss50.network.util.Math.percentage(Max, current), 16);
        }
    }


}
