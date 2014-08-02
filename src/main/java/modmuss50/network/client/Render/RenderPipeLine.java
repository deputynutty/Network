package modmuss50.network.client.Render;

import modmuss50.network.Fmp.Multipart;
import modmuss50.network.Fmp.PartPipeLine;
import modmuss50.network.client.models.PipeLineModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class RenderPipeLine extends TileEntitySpecialRenderer {

    private PipeLineModel model;

    public RenderPipeLine() {
        this.model = new PipeLineModel();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
        PartPipeLine pipeLine = Multipart.getPipe(te);

    }

    public void doRender(double x, double y, double z, float f, Map<ForgeDirection, TileEntity> connectedSides, int LAmount) {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x, (float) y, (float) z);

        if (connectedSides == null) {
            connectedSides = new HashMap<ForgeDirection, TileEntity>();
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                connectedSides.put(dir, null);
            }
        }

        if (LAmount == 0) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_0.png"));
        } else if (LAmount >= 1 && LAmount <= 100) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_1.png"));
        } else if (LAmount >= 101 && LAmount <= 200) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_2.png"));
        } else if (LAmount >= 201 && LAmount <= 300) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_3.png"));
        } else if (LAmount >= 301 && LAmount <= 400) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_4.png"));
        } else if (LAmount >= 401 && LAmount <= 500) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_5.png"));
        } else if (LAmount >= 501 && LAmount <= 600) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_6.png"));
        } else if (LAmount >= 601 && LAmount <= 700) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_7.png"));
        } else if (LAmount >= 701 && LAmount <= 800) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_8.png"));
        } else if (LAmount >= 801 && LAmount <= 900) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_9.png"));
        } else if (LAmount >= 901 && LAmount <= 999) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_10.png"));
        } else if (LAmount == 1000) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/PipeLine/PipeLine_11.png"));
        }
        GL11.glColor3f(0.8F, 0.8F, 0.8F);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.49F, -0.49F, 0.55F);
        GL11.glTranslatef(-0.02F, 0.02F, -0.02f);
        if (connectedSides.containsKey(ForgeDirection.UP)) {
            this.model.Down.render(0.0625F);
        }

        if (connectedSides.containsKey(ForgeDirection.DOWN)) {
            this.model.Up.render(0.0625F);
        }

        if (connectedSides.containsKey(ForgeDirection.NORTH)) {
            this.model.Forward.render(0.0625F);
        }

        if (connectedSides.containsKey(ForgeDirection.SOUTH)) {
            this.model.Back.render(0.0625F);
        }

        if (connectedSides.containsKey(ForgeDirection.EAST)) {
            this.model.Right.render(0.0625F);
        }

        if (connectedSides.containsKey(ForgeDirection.WEST)) {
            this.model.Left.render(0.0625F);
        }

        this.model.Main.render(0.0625F);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

}
