package modmuss50.network.client.render;

import modmuss50.network.entity.robot.EntityDrone;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by mark on 18/09/2014.
 */
public class RenderRobot extends Render {
	private static final ResourceLocation minecartTextures = new ResourceLocation("TEXTURE");
	protected final RenderBlocks field_94145_f;
	/**
	 * instance of ModelMinecart for rendering
	 */
	protected ModelBase modelMinecart = new ModelMinecart();

	public RenderRobot() {
		this.shadowSize = 0.5F;
		this.field_94145_f = new RenderBlocks();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class render is generic
	 * (render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(EntityDrone p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		GL11.glPushMatrix();
		this.bindEntityTexture(p_76986_1_);
		long i = (long) p_76986_1_.getEntityId() * 493286711L;
		i = i * i * 4392167121L + i * 98761L;
		float f2 = (((float) (i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f3 = (((float) (i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f4 = (((float) (i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		GL11.glTranslatef(f2, f3, f4);

		float f5 = p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;

		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-f5, 0.0F, 0.0F, 1.0F);


		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		this.modelMinecart.render(p_76986_1_, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityDrone p_110775_1_) {
		return minecartTextures;
	}

	protected void func_147910_a(EntityDrone p_147910_1_, float p_147910_2_, Block p_147910_3_, int p_147910_4_) {
		float f1 = p_147910_1_.getBrightness(p_147910_2_);
		GL11.glPushMatrix();
		this.field_94145_f.renderBlockAsItem(p_147910_3_, p_147910_4_, f1);
		GL11.glPopMatrix();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.getEntityTexture((EntityDrone) p_110775_1_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class render is generic
	 * (render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		this.doRender((EntityDrone) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
