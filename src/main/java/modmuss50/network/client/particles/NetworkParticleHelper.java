package modmuss50.network.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 24/02/14 Time: 21:06
 */
public class NetworkParticleHelper {

    public static void runWifiFX(World par1World, double x, double y, double z, double tx, double ty, double tz, float red, float green, float blue, int age) {
        WifiFX wifi = new WifiFX(par1World, x, y, z, tx, ty, tz, red, green, blue, age);
        Minecraft.getMinecraft().effectRenderer.addEffect(wifi);
    }

}
