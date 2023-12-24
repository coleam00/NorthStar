package net.coleam.northstar.datagen;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.item.ModItems;
import net.coleam.northstar.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, NorthStar.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.JADE_HELMET.get(),
                ModItems.JADE_CHESTPLATE.get(),
                ModItems.JADE_LEGGINGS.get(),
                ModItems.JADE_BOOTS.get());

        this.tag(ModTags.items.INFUSED_EMERALDS)
                .add(ModItems.COPPER_INFUSED_EMERALD.get(),
                ModItems.LAPIS_INFUSED_EMERALD.get(),
                ModItems.IRON_INFUSED_EMERALD.get(),
                ModItems.GOLD_INFUSED_EMERALD.get(),
                ModItems.REDSTONE_INFUSED_EMERALD.get(),
                ModItems.DIAMOND_INFUSED_EMERALD.get(),
                ModItems.NETHERITE_INFUSED_EMERALD.get());

        this.tag(ModTags.items.NORTHSTAR_EYES)
                .add(ModItems.VILLAGE_EYE.get(),
                ModItems.MINESHAFT_EYE.get(),
                ModItems.NETHER_FORTRESS_EYE.get(),
                ModItems.BASTION_EYE.get(),
                ModItems.OCEAN_MONUMENT_EYE.get(),
                ModItems.ANCIENT_CITY_EYE.get(),
                ModItems.END_CITY_EYE.get(),
                ModItems.JADE_EYE.get());
    }
}
