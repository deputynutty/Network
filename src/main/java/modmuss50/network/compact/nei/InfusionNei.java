package modmuss50.network.compact.NEI;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import modmuss50.network.api.InfusionFurnaceRecipes;
import modmuss50.network.api.InfusionRecipe;
import modmuss50.network.blocks.tileentities.TileEntityInfusionFurnace;
import modmuss50.network.client.gui.GuiInfusionFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//This is the NEI recipe handler for the infusion furncace
public class InfusionNei extends TemplateRecipeHandler {
    @Override
    public void loadTransferRects() {
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiInfusionFurnace.class;
    }

    @Override
    public String getRecipeName() {
        return "Infusion Furnace";
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return super.newInstance();
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("smelting") && getClass() == InfusionNei.class) {//don't want subclasses getting a hold of this
            ArrayList<InfusionRecipe> recpies = InfusionFurnaceRecipes.smelting().recpies;
            for (int i = 0; i < recpies.size(); i++) {
                arecipes.add(new SmeltingPair(recpies.get(i).input, recpies.get(i).output));
            }
        } else
            super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        ArrayList<InfusionRecipe> recpies = InfusionFurnaceRecipes.smelting().recpies;
        for (int i = 0; i < recpies.size(); i++) {
            if (NEIServerUtils.areStacksSameType(recpies.get(i).output, result))
                arecipes.add(new SmeltingPair(recpies.get(i).input, recpies.get(i).output));
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        if (inputId.equals("fuel") && getClass() == InfusionNei.class)//don't want subclasses getting a hold of this
            loadCraftingRecipes("smelting");
        else
            super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        ArrayList<InfusionRecipe> recpies = InfusionFurnaceRecipes.smelting().recpies;
        for (int i = 0; i < recpies.size(); i++) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recpies.get(i).output, ingredient)) {
                SmeltingPair arecipe = new SmeltingPair(recpies.get(i).input, recpies.get(i).output);
                arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred), ingredient);
                arecipes.add(arecipe);
            }
        }
    }

    @Override
    public String getGuiTexture() {
        return "network:textures/gui/neiInfusion.png";
    }

    @Override
    public void drawExtras(int recipe) {
        Minecraft.getMinecraft().fontRenderer.drawString("100%", 75, 28, 4210752);
        Minecraft.getMinecraft().fontRenderer.drawString("Time to smelt: " + (TileEntityInfusionFurnace.maxSmeltTime / 20) + " Seconds", 1, 45, 4210752);
        Minecraft.getMinecraft().fontRenderer.drawString("Power needed: " + (TileEntityInfusionFurnace.getNeededPower() * TileEntityInfusionFurnace.maxSmeltTime) + " NetUnits", 1, 55, 4210752);
    }

    @Override
    public String getOverlayIdentifier() {
        return "Infusion Furnace";
    }

    public static class FuelPair {
        public PositionedStack stack;
        public int burnTime;
        public FuelPair(ItemStack ingred, int burnTime) {
            this.stack = new PositionedStack(ingred, 51, 42, false);
            this.burnTime = burnTime;
        }
    }

    public class SmeltingPair extends CachedRecipe {
        PositionedStack ingred;
        PositionedStack result;

        public SmeltingPair(ItemStack ingred, ItemStack result) {
            ingred.stackSize = 1;
            this.ingred = new PositionedStack(ingred, 51, 24);
            this.result = new PositionedStack(result, 111, 24);
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(ingred));
        }

        public PositionedStack getResult() {
            return result;
        }
    }
}