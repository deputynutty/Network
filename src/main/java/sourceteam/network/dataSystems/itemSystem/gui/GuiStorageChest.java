package sourceteam.network.dataSystems.itemSystem.gui;

import sourceteam.network.dataSystems.itemSystem.containers.ContainerStorageChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Mark on 11/08/2014.
 */
public class GuiStorageChest extends GuiContainer {
    public static final ResourceLocation texture = new ResourceLocation("network:textures/gui/StorageChest.png");


    public GuiStorageChest(IInventory player, IInventory chest) {
        super(new ContainerStorageChest(player, chest, 238, 256));
        this.allowUserInput = false;
        this.xSize = 238;
        this.ySize = 256;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // new "bind tex"
        this.mc.getTextureManager().bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }


}
