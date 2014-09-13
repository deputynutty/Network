package modmuss50.network.client.render;

import modmuss50.network.blocks.tileentities.TileEntityFluidGen;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class RenderFluidGen extends TileEntitySpecialRenderer {

    public static void setGLColorFromInt(int color) {
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, 1.0F);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {

        TileEntityFluidGen tank = ((TileEntityFluidGen) tileentity);

        FluidStack liquid = tank.tank.getFluid();
        int color = 0xFFFFFF;
        if (liquid == null || liquid.amount <= 0) {
            return;
        }

        int[] displayList = FluidRenderer.getFluidDisplayLists(liquid, tileentity.getWorldObj(), false);
        if (displayList == null) {
            return;
        }

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        bindTexture(FluidRenderer.getFluidSheet(liquid));
        setGLColorFromInt(color);

        GL11.glTranslatef((float) x + 0.001F, (float) y + 0.5F, (float) z + 0.001F);
        GL11.glScalef(0.999F, 0.999F, 0.999F);
        GL11.glTranslatef(0, -0.5F, 0);

        GL11.glCallList(displayList[(int) ((float) liquid.amount / (float) (tank.tank.getCapacity()) * (FluidRenderer.DISPLAY_STAGES - 1))]);

        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

}
