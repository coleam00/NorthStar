package net.coleam.northstar.datagen;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.block.ModBlocks;
import net.coleam.northstar.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModStructureTagGenerator extends StructureTagsProvider {

    public ModStructureTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NorthStar.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Structures.NETHER_FORTRESS)
                .add(TagEntry.element(new ResourceLocation("minecraft", "fortress")));
        this.tag(ModTags.Structures.BASTION_REMNANT)
                .add(TagEntry.element(new ResourceLocation("minecraft", "bastion_remnant")));
        this.tag(ModTags.Structures.OCEAN_MONUMENT)
                .add(TagEntry.element(new ResourceLocation("minecraft", "monument")));
        this.tag(ModTags.Structures.ANCIENT_CITY)
                .add(TagEntry.element(new ResourceLocation("minecraft", "ancient_city")));
        this.tag(ModTags.Structures.END_CITY)
                .add(TagEntry.element(new ResourceLocation("minecraft", "end_city")));
        this.tag(ModTags.Structures.JADE_TRADING_POST)
                .add(TagEntry.element(new ResourceLocation(NorthStar.MOD_ID, "jadetradingpost")));
    }
}
