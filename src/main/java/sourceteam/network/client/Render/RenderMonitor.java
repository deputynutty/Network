package sourceteam.network.client.Render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import sourceteam.network.blocks.tileentities.TileEntityMonitor;

import java.util.List;

public class RenderMonitor extends TileEntitySpecialRenderer {
    public static double rotateMatrixByMetadata(int metadata) {

        double metaRotation;

        metaRotation = 0.0D;
        GL11.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
        GL11.glTranslated(0.0D, -1.0D, 1.0D);

        if (metadata == 1)
            GL11.glRotated(1.0D, 1.0D, 10.0D, 1.0D);

        // GL11.glRotated(metaRotation, 0.0D, 1.0D, 0.0D);
        return metaRotation;
    }

    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        TileEntityMonitor tile = (TileEntityMonitor) tileentity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        rotateMatrixByMetadata(tile.getBlockMetadata());
        GL11.glTranslatef(0.0F, 1.0F, 0.4275F);
        List textLines = tile.getTextLines();
        int lineWidth = getMaxLineWidth(textLines);
        int lineHeight = 10 * textLines.size();
        float textScale = Math.min(0.875F / lineWidth, 0.875F / lineHeight);
        GL11.glScalef(textScale, textScale, textScale);
        // GL11.glRotatef(tile.textRotation * 90, 0.0F, 0.0F, 1.0F);
        int editedLine = -1;
        // if ((FMLClientHandler.instance().getClient().currentScreen instanceof
        // GuiAphorismTile)) {
        // GuiAphorismTile gui =
        // (GuiAphorismTile)FMLClientHandler.instance().getClient().currentScreen;
        // if ((gui.tile == tile) && (gui.updateCounter % 12 < 6)) {
        // editedLine = gui.cursorY;
        // }
        // }

        for (int i = 0; i < textLines.size(); i++) {
            String textLine = (String) textLines.get(i);
            if (editedLine == i)
                textLine = ">" + textLine + "<";
            RenderManager.instance.getFontRenderer().drawString(EnumChatFormatting.ITALIC + textLine, -RenderManager.instance.getFontRenderer().getStringWidth(textLine) / 2, -(textLines.size() * 10) / 2 + i * 10 + 1, -16777216);
        }

        GL11.glPopMatrix();
    }

    private int getMaxLineWidth(List<String> textList) {
        int maxLength = 0;
        for (String string : textList) {
            int stringWidth = RenderManager.instance.getFontRenderer().getStringWidth(string);
            if (stringWidth > maxLength) {
                maxLength = stringWidth;
            }
        }
        return maxLength;
    }
}
