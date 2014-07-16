package modmuss50.network.client.Render;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class RenderHUD {

	Minecraft	field_73839_d;

	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.type != RenderGameOverlayEvent.ElementType.CHAT && event.type != RenderGameOverlayEvent.ElementType.ALL && event.type != RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
			// event.setCanceled(true);
		}

	}

	@SubscribeEvent
	public void ClientTick(TickEvent.RenderTickEvent event) {

	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {

		this.field_73839_d = Minecraft.getMinecraft();

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// do render stuff here
		GL11.glPopMatrix();
	}
}
