package net.coleam.northstar.world;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

public class CustomChunkGenerator extends NoiseBasedChunkGenerator {
    public static final Codec<CustomChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
            Codec.LONG.fieldOf("seed").stable().forGetter(CustomChunkGenerator::getSeed),
            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(CustomChunkGenerator::getSettings)
            // Add other configuration parameters here
    ).apply(instance, instance.stable(CustomChunkGenerator::new)));

    private final long seed;
    private final Holder<NoiseGeneratorSettings> settings;

    public Biome getBiomeFromResourceKey(ResourceKey<Biome> biomeKey) {
        // Access the biome registry from the server world
        Registry<Biome> biomeRegistry = ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(ForgeRegistries.BIOMES.getRegistryKey());

        // Retrieve the biome using the resource key
        Holder<Biome> biomeHolder = biomeRegistry.getHolderOrThrow(biomeKey);

        // Return the biome instance
        return biomeHolder.value();
    }

    public CustomChunkGenerator(BiomeSource biomeSource, long seed, Holder<NoiseGeneratorSettings> noiseGeneratorSettings) {
        super(new FixedBiomeSource(new Holder.Direct<Biome>(ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(ForgeRegistries.BIOMES.getRegistryKey()).getHolderOrThrow(Biomes.CHERRY_GROVE).value())), noiseGeneratorSettings);
        this.seed = seed;
        this.settings = noiseGeneratorSettings;
    }

    public long getSeed() {
        return this.seed;
    }

    public Holder<NoiseGeneratorSettings> getSettings() {
        return this.settings;
    }

    /*
    @Override
    public void generateStructures(WorldGenRegion region, IChunk chunk) {
        ChunkPos chunkpos = chunk.getPos();

        // Check if the chunk is within the desired Z-coordinate range
        if (chunkpos.getZ() >= -2000 && chunkpos.getZ() <= -1000) {
            // Apply your custom biome here
            Biome biome = ... // your custom biome
            setBiome(region, chunkpos, biome);
        }
    }

    private void setBiome(WorldGenRegion region, ChunkPos chunkPos, Biome biome) {
        // Logic to set the biome of the entire chunk or part of it
    }

    // ... Other necessary overrides
     */
}

