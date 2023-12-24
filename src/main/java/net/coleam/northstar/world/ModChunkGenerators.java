package net.coleam.northstar.world;

import net.coleam.northstar.NorthStar;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import com.mojang.serialization.Codec;

@Mod("northstar")
public class ModChunkGenerators {
    private static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(Registries.CHUNK_GENERATOR, NorthStar.MOD_ID);

    public static final RegistryObject<Codec<? extends ChunkGenerator>> CUSTOM_CHUNK_GENERATOR = CHUNK_GENERATORS.register("custom_chunk_generator", () -> CustomChunkGenerator.CODEC);

    public static void register(IEventBus modEventBus) {
        CHUNK_GENERATORS.register(modEventBus);
    }
}