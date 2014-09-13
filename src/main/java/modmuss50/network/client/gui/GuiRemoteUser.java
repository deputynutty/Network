package modmuss50.network.client.gui;

import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.containers.ContainerPowerUserBase;
import modmuss50.network.blocks.tileentities.TileEntityRemoteUser;
import modmuss50.network.client.gui.componets.GuiButtonItem;
import modmuss50.network.netty.packets.PacketRemoteGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sourceteam.mods.lib.client.GuiRenderHelper;

public class GuiRemoteUser extends GuiContainer {
    private static final ResourceLocation GuiTextures = new ResourceLocation("network", "textures/gui/BasePoweredGui.png");
    private TileEntityRemoteUser tile;

    public GuiRemoteUser(InventoryPlayer par1InventoryPlayer, TileEntityRemoteUser par2TileEntityFurnace) {
        super(new ContainerPowerUserBase(par1InventoryPlayer, par2TileEntityFurnace));
        this.tile = par2TileEntityFurnace;
    }

    @Override
    public void initGui() {
        initButtons();
        super.initGui();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        String s = "Power: " + this.tile.getCurrentPower() + "/" + this.tile.getPowerStorageSize();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2 + 27, 58 + 10, GuiContants.guiColour);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        GuiRenderHelper.drawPowerBar(k + 65, l + 54 + 10, tile.getPowerStorageSize(), tile.getCurrentPower(), this);
    }

    public void initButtons() {
        buttonList.clear();
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        for (int i = 0; i < tile.remotetiles.size(); i++) {
            int j = 0;

            if (i >= 3)
                j += 22;
            if (i >= 6)
                j += 22;
            if (i >= 9)
                j += 22;
            if (i >= 12)
                j += 22;
            int r = (22 * i);

            if (j == 22)

                r -= 22 * 3;

            // for (int m = 2; m < tile.remotetiles.size() / 3; m++) {
            // if(j == 22 * m )
            // r -=( 22 * 3) * m;
            // }

            if (j == 22 * 3)
                r -= (22 * 3) * 3;
            if (j == 22 * 4)
                r -= (22 * 4) * 4;

            this.buttonList.add(new GuiButtonItem(i, k + 10 + j, l + r + 10, 20, 20, new ItemStack(tile.remotetiles.get(i).getBlockType(), 1)));
        }

    }

    public void actionPerformed(GuiButton par0Button) {

        for (int i = 0; i < tile.remotetiles.size(); i++) {
            if (par0Button.id == i) {
                System.out.println(tile.remotetiles.get(i).blockType.getLocalizedName());

//                if (Minecraft.getMinecraft().theWorld.getTileEntity(tile.remotetiles.get(i).xCoord,
//                        tile.remotetiles.get(i).yCoord,
//                        tile.remotetiles.get(i).zCoord) instanceof IRemoteTile) {
//                    Block irt =
//                            Minecraft.getMinecraft().theWorld.getBlock(tile.remotetiles.get(i).xCoord,
//                                    tile.remotetiles.get(i).yCoord,
//                                    tile.remotetiles.get(i).zCoord);
//                    irt.onBlockActivated(Minecraft.getMinecraft().theWorld,
//                            tile.remotetiles.get(i).xCoord,
//                            tile.remotetiles.get(i).yCoord,
//                            tile.remotetiles.get(i).zCoord
//                            , Minecraft.getMinecraft().thePlayer, 0, 0.5F, 0.5F, 0.5F);
//
//

//                TileEntityInfusionFurnace tileentityfurnace = (TileEntityInfusionFurnace) Minecraft.getMinecraft().theWorld.getTileEntity(tile.remotetiles.get(i).xCoord, tile.remotetiles.get(i).yCoord, tile.remotetiles.get(i).zCoord);
//
//                if (tileentityfurnace != null) {
//                    Minecraft.getMinecraft().thePlayer.openGui(NetworkCore.instance, 12, Minecraft.getMinecraft().theWorld, tile.remotetiles.get(i).xCoord, tile.remotetiles.get(i).yCoord, tile.remotetiles.get(i).zCoord);
//                }


                //TODO this is badly broken and NEEDS fixing
                NetworkCore.packetPipeline.sendToServer(new PacketRemoteGui(tile.remotetiles.get(i).xCoord, tile.remotetiles.get(i).yCoord, tile.remotetiles.get(i).zCoord));
                Minecraft.getMinecraft().thePlayer.worldObj.getBlock(tile.remotetiles.get(i).xCoord, tile.remotetiles.get(i).yCoord, tile.remotetiles.get(i).zCoord).onBlockActivated(Minecraft.getMinecraft().thePlayer.worldObj, tile.remotetiles.get(i).xCoord, tile.remotetiles.get(i).yCoord, tile.remotetiles.get(i).zCoord, Minecraft.getMinecraft().thePlayer, 1, 0F, 0F, 0F);

            }

            //    }
        }
    }

}
