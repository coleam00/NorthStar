package net.coleam.northstar.datagen;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.block.ModBlocks;
import net.coleam.northstar.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> JADE_SMELTABLES = List.of(ModItems.JADE.get(),
            ModBlocks.JADE_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreBlasting(pWriter, JADE_SMELTABLES, RecipeCategory.MISC, ModItems.JADE_DUST.get(), 0.25f, 100, "jade");

        blockFromGemRecipe(pWriter, ModItems.JADE.get(), ModBlocks.JADE_BLOCK.get());

        gemsFromBlockRecipe(pWriter, ModItems.JADE.get(), ModBlocks.JADE_BLOCK.get());

        swordRecipe(pWriter, ModItems.JADE.get(), ModItems.JADE_SWORD.get());
        pickaxeRecipe(pWriter, ModItems.JADE.get(), ModItems.JADE_PICKAXE.get());
        axeRecipe(pWriter, ModItems.JADE.get(), ModItems.JADE_AXE.get());
        shovelRecipe(pWriter, ModItems.JADE.get(), ModItems.JADE_SHOVEL.get());
        hoeRecipe(pWriter, ModItems.JADE.get(), ModItems.JADE_HOE.get());
    }

    protected static void blockFromGemRecipe(Consumer<FinishedRecipe> pWriter, ItemLike item, ItemLike block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', item)
                .unlockedBy(getHasName(item), has(item))
                .save(pWriter);
    }

    protected static void gemsFromBlockRecipe(Consumer<FinishedRecipe> pWriter, ItemLike item, ItemLike block) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item, 9)
                .requires(block)
                .unlockedBy(getHasName(block), has(block))
                .save(pWriter);
    }

    protected static void swordRecipe(Consumer<FinishedRecipe> pWriter, ItemLike item, ItemLike sword) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, sword)
                .pattern(" I ")
                .pattern(" I ")
                .pattern(" S ")
                .define('I', item)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(item), has(item))
                .save(pWriter);
    }

    protected static void pickaxeRecipe(Consumer<FinishedRecipe> pWriter, ItemLike item, ItemLike pickaxe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pickaxe)
                .pattern("III")
                .pattern(" S ")
                .pattern(" S ")
                .define('I', item)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(item), has(item))
                .save(pWriter);
    }

    protected static void axeRecipe(Consumer<FinishedRecipe> pWriter, ItemLike item, ItemLike axe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, axe)
                .pattern(" II")
                .pattern(" SI")
                .pattern(" S ")
                .define('I', item)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(item), has(item))
                .save(pWriter);
    }

    protected static void shovelRecipe(Consumer<FinishedRecipe> pWriter, ItemLike item, ItemLike shovel) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, shovel)
                .pattern(" I ")
                .pattern(" S ")
                .pattern(" S ")
                .define('I', item)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(item), has(item))
                .save(pWriter);
    }

    protected static void hoeRecipe(Consumer<FinishedRecipe> pWriter, ItemLike item, ItemLike hoe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, hoe)
                .pattern(" II")
                .pattern(" S ")
                .pattern(" S ")
                .define('I', item)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(item), has(item))
                .save(pWriter);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, NorthStar.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
