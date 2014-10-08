package modmuss50.network.dataSystems.itemSystem.gui;

import codechicken.nei.ItemList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import sourceteam.mods.lib.client.RenderItemUtils;

public class GuiItemList extends GuiScreen {

    public static int scroll = 0;

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        if(ItemList.items.size() == 0){
            ItemList.loadItems();
        }
        scroll = 0;
    }

    @Override
    public void actionPerformed(GuiButton button) {

    }

    @Override
    public void drawScreen(int x, int y, float f) {
        scroll -= 10;
        int rx = scroll, ry = mc.displayWidth/ 2 - 180;

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < ItemList.items.size(); i++) {
                ItemStack block = ItemList.items.get(i);
                if (block != null) {
                    ry += 20;
                    //if (i % 2 == 0) {
                        rx += 200;
                        ry = mc.displayWidth/ 2 - 180;
                   // }

                    renderBlockIntoGui(block, rx, ry, 10F, Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft());


                }
            }
        }





    }

    public static void renderBlockIntoGui(ItemStack block, int x, int y, float scale, FontRenderer fontRenderer, Minecraft mc) {
        RenderItemUtils.renderItemAndEffectIntoGUI(fontRenderer, Minecraft.getMinecraft().getTextureManager(), block, x, y, scale);
    }

}