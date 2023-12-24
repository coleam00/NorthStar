package net.coleam.northstar.datagen;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.block.ModBlocks;
import net.coleam.northstar.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NorthStar.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Blocks.NORTHSTAR_ORES)
                .add(ModBlocks.JADE_ORE.get());

        this.tag(ModTags.Blocks.NORTHSTAR_PORTAL_BLOCKS)
                .add(ModBlocks.JADE_PORTAL_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.JADE_BLOCK.get(),
                        ModBlocks.JADE_ORE.get()
                );

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.JADE_BLOCK.get(),
                        ModBlocks.JADE_ORE.get()
                );

        /*
        this.tag(ModTags.Blocks.NEEDS_JADE_TOOL)
                .add(ModBlocks.JADE_BLOCK.get(),
                        ModBlocks.JADE_ORE.get()
                );
         */

        /*
        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(ModBlocks.JADE_BLOCK.get(),
                        ModBlocks.JADE_ORE.get()
                );
         */
    }
}
