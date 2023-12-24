package net.coleam.northstar.villager;

import com.google.common.collect.ImmutableSet;
import net.coleam.northstar.NorthStar;
import net.coleam.northstar.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, NorthStar.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, NorthStar.MOD_ID);

    public static final RegistryObject<PoiType> JADE_POI = POI_TYPES.register("jade_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.JADE_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<PoiType> ENDER_OBSIDIAN_POI = POI_TYPES.register("ender_obsidian_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.ENDER_OBSIDIAN.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<PoiType> INFUSED_EMERALD_POI = POI_TYPES.register("infused_emerald_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.INFUSED_EMERALD_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> JADE_MASTER =
            VILLAGER_PROFESSIONS.register("jademaster", () -> new VillagerProfession("jademaster",
                    holder -> holder.get() == JADE_POI.get(), holder -> holder.get() == JADE_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));

    public static final RegistryObject<VillagerProfession> ENDER_TRADER =
            VILLAGER_PROFESSIONS.register("ender_trader", () -> new VillagerProfession("ender_trader",
                    holder -> holder.get() == ENDER_OBSIDIAN_POI.get(), holder -> holder.get() == ENDER_OBSIDIAN_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));

    public static final RegistryObject<VillagerProfession> MYSTIC_MERCENARY =
            VILLAGER_PROFESSIONS.register("mystic_mercenary", () -> new VillagerProfession("mystic_mercenary",
                    holder -> holder.get() == INFUSED_EMERALD_POI.get(), holder -> holder.get() == INFUSED_EMERALD_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_TOOLSMITH));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
