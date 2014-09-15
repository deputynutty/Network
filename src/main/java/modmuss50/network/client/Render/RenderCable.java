package modmuss50.network.client.render;

import modmuss50.network.blocks.tileentities.TileEntityCable;
import modmuss50.network.client.models.CableModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 24/02/14 Time: 13:15
 */
public class RenderCable extends TileEntitySpecialRenderer {

    private CableModel model;
    private RenderingHelper.ItemRender itemRenderer;
    private RenderingHelper.ItemRender resultRenderer;

    public RenderCable() {
        this.model = new CableModel();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
        TileEntityCable tile = (TileEntityCable) te;
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F + 0.05F, (float) y + 1.5f - 0.05F, (float) z + 0.5F + 0.05F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/advanedcable_on.png"));
        if (!tile.back) {
            this.model.Right.render(0.0625F);
        }

        if (!tile.forward) {
            this.model.Left.render(0.0625F);
        }

        if (!tile.bottom) {
            this.model.Down.render(0.0625F);
        }

        if (!tile.top) {
            this.model.Up.render(0.0625F);
        }

        if (!tile.left) {
            this.model.Forward.render(0.0625F);
        }

        if (!tile.right) {
            this.model.Back.render(0.0625F);
        }

        this.model.Main.render(0.0625F);

        GL11.glPopMatrix();
    }

    public void doRender(double x, double y, double z, Map<ForgeDirection, TileEntity> connectedSides) {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x, (float) y, (float) z);

        if (connectedSides == null) {
            connectedSides = new HashMap<ForgeDirection, TileEntity>();
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                connectedSides.put(dir, null);
            }
        }


        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("network", "models/advanedcable_off.png"));


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

        GL11.glScalef(0.65F, 0.65F, 0.65F);
        GL11.glTranslatef(0.01F, 0.52F, -0.01f);
        this.model.Main.render(0.0625F);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

}
