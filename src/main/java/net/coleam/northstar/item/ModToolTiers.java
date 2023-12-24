package net.coleam.northstar.item;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier JADE = TierSortingRegistry.registerTier(
            new ForgeTier(4, 2500, 9f, 4f, 25,
                    ModTags.Blocks.NEEDS_JADE_TOOL, () -> Ingredient.of(ModItems.JADE.get())),
            new ResourceLocation(NorthStar.MOD_ID, "jade"), List.of(Tiers.DIAMOND), List.of());
}
