package sourceteam.network.api;

import net.minecraft.item.ItemStack;

/**
 * Created by Mark on 30/07/2014.
 */
public class InfusionRecipe {

    public ItemStack input;
    public ItemStack output;

    public InfusionRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

}
