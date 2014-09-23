package modmuss50.network.client.gui;


import modmuss50.network.entity.robot.EntityDrone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


public class GuiDrone extends GuiScreen {


        public EntityDrone entity3rdPerson;
        private EntityLivingBase player3rd;
        private int thirdPersonView = 0;

        private double posX, posY, posZ;
        private float yaw;

        private static final ResourceLocation trackDesignerGuiTextures = new ResourceLocation("rc", "textures/gui/track_designer.png");


        public GuiDrone(EntityPlayer player, World world, int x, int y, int z) {

            entity3rdPerson = new EntityDrone(world);
            if (entity3rdPerson != null) {
                Minecraft.getMinecraft().theWorld.spawnEntityInWorld(entity3rdPerson);
                player3rd = Minecraft.getMinecraft().renderViewEntity;

                entity3rdPerson.setLocationAndAngles(x, y, z, 0 , 50);

                Minecraft.getMinecraft().renderViewEntity = entity3rdPerson;
                thirdPersonView = Minecraft.getMinecraft().gameSettings.thirdPersonView;
                Minecraft.getMinecraft().gameSettings.thirdPersonView = 8;

                posX = entity3rdPerson.posX + 0.5;
                posY = entity3rdPerson.posY + 5;
                posZ = entity3rdPerson.posZ - 5;

                entity3rdPerson.setPositionAndUpdate(posX, posY, posZ);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public void initGui() {

        }

        @Override
        public void drawScreen(int par1, int par2, float par3) {
            super.drawScreen(par1, par2, par3);

        }


        @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Minecraft.getMinecraft().renderViewEntity = Minecraft.getMinecraft().thePlayer;
        Minecraft.getMinecraft().gameSettings.thirdPersonView = thirdPersonView;
        Minecraft.getMinecraft().theWorld.removeEntity(entity3rdPerson);
        entity3rdPerson = null;
    }
}
