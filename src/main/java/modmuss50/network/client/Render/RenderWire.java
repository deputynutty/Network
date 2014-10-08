package modmuss50.network.client.render;

import modmuss50.network.client.models.WireModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 05/08/2014.
 */
public class RenderWire extends TileEntitySpecialRenderer {

    private WireModel model;

    public RenderWire() {
        this.model = new WireModel();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {

    }

    public void doRender(double x, double y, double z, Map<ForgeDirection, TileEntity> connectedSides, int type) {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x, (float) y, (float) z);

        if (connectedSides == null) {
            connectedSides = new HashMap<ForgeDirection, TileEntity>();
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                connectedSides.put(dir, null);
            }
        }

        //TODO fix colour

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/NFCWire" + type + ".png"));

        GL11.glColor3f(0.8F, 0.8F, 0.8F);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.49F, -0.49F, 0.55F);
        GL11.glTranslatef(-0.02F, 0.02F - 0.4F, -0.02f);
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
