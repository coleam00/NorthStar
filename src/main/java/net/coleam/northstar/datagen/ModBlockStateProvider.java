package net.coleam.northstar.datagen;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NorthStar.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.JADE_BLOCK);
        blockWithItem(ModBlocks.JADE_ORE);
        blockWithItem(ModBlocks.LOCKED);
        blockWithItem(ModBlocks.JADE_PORTAL_BLOCK);
        blockWithItem(ModBlocks.INFUSED_EMERALD_BLOCK);
        blockWithItem(ModBlocks.ENDER_OBSIDIAN);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
