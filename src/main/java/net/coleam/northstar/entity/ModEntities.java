package net.coleam.northstar.entity;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.entity.custom.JadeHoarderEntity;
import net.coleam.northstar.entity.custom.LockedTraderEntity;
import net.coleam.northstar.entity.custom.MurphyEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NorthStar.MOD_ID);

    public static final RegistryObject<EntityType<JadeHoarderEntity>> JADEHOARDER =
            ENTITY_TYPES.register("jadehoarder",
                    () -> EntityType.Builder.of(JadeHoarderEntity::new, MobCategory.CREATURE)
                            .sized(1.75f, 2f)
                            .build(new ResourceLocation(NorthStar.MOD_ID, "jadehoarder").toString()));

    public static final RegistryObject<EntityType<MurphyEntity>> MURPHY =
            ENTITY_TYPES.register("murphy",
                    () -> EntityType.Builder.of(MurphyEntity::new, MobCategory.CREATURE)
                            .sized(1.75f, 1f)
                            .build(new ResourceLocation(NorthStar.MOD_ID, "murphy").toString()));

    public static final RegistryObject<EntityType<LockedTraderEntity>> LOCKEDTRADER =
            ENTITY_TYPES.register("lockedtrader",
                    () -> EntityType.Builder.of(LockedTraderEntity::new, MobCategory.CREATURE)
                            .sized(1f, 2f)
                            .build(new ResourceLocation(NorthStar.MOD_ID, "lockedtrader").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
