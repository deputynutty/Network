package modmuss50.network.world;

import modmuss50.mods.lib.retroGenerator.IRetroGenerator;
import modmuss50.network.blocks.NetworkBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGen implements IRetroGenerator {

	private void generateSurface(World world, Random random, int i, int j) {
	//	if(random.nextInt(64) == 1){
			int x =  i * 16;// + random.nextInt(16);
			int y = 100;
			int z = j * 16;// + random.nextInt(16);
			world.setBlock(x, y, z, NetworkBlocks.networkCable);
			System.out.println(x + ":" +  y + ":" +  z);
	//	}
	}

	@Override
	public String getUniqueGenerationID() {
		return "network:generator";
	}

	@Override
	public boolean canGenerateIn(World world, Chunk chunk, Random random) {
		//Tuned this of because this is a test of mmc
		return false;
	}

	@Override
	public void generate(Random rand, World world, int chunkX, int chunkZ) {
		generateSurface(world, rand, chunkX, chunkZ);
	}
}
