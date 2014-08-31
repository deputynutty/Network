package sourceteam.network.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by Mark on 29/07/2014.
 */
public class InfusionFurnaceRecipes {

    public static InfusionFurnaceRecipes smeltingBase = new InfusionFurnaceRecipes();

    public ArrayList<InfusionRecipe> recpies = new ArrayList<InfusionRecipe>();

    public static InfusionFurnaceRecipes smelting() {
        return smeltingBase;
    }

    public void addRecipe(Block block, ItemStack itemStack) {
        this.addRecipe(Item.getItemFromBlock(block), itemStack);
    }

    public void addRecipe(Item item, ItemStack itemStack) {
        this.addRecipe(new ItemStack(item, 1, 32767), itemStack);
    }

    public void addRecipe(ItemStack input, ItemStack output) {
        this.recpies.add(new InfusionRecipe(input, output));
    }

    public ItemStack getSmeltingResult(ItemStack itemStack) {
        for (int i = 0; i < recpies.size(); i++) {
            if (recpies.get(i).input.getItem().equals(itemStack.getItem()) && recpies.get(i).input.getItemDamage() == itemStack.getItemDamage()) {
                return recpies.get(i).output;
            }
        }
        return null;
    }
}
