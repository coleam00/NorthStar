package net.coleam.northstar.item;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.entity.ModEntities;
import net.coleam.northstar.item.custom.HomePearlItem;
import net.coleam.northstar.item.custom.MetalDetectorItem;
import net.coleam.northstar.item.custom.ModArmorItem;
import net.coleam.northstar.item.custom.NorthstarEyeItem;
import net.coleam.northstar.util.ModTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NorthStar.MOD_ID);

    public static final RegistryObject<Item> JADE = ITEMS.register("jade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> JADE_DUST = ITEMS.register("jade_dust",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> VILLAGE_EYE = ITEMS.register("village_eye",
            () -> new NorthstarEyeItem(new Item.Properties(), StructureTags.VILLAGE, 0));
    public static final RegistryObject<Item> MINESHAFT_EYE = ITEMS.register("mineshaft_eye",
            () -> new NorthstarEyeItem(new Item.Properties(), StructureTags.MINESHAFT, 0));
    public static final RegistryObject<Item> NETHER_FORTRESS_EYE = ITEMS.register("nether_fortress_eye",
            () -> new NorthstarEyeItem(new Item.Properties(), ModTags.Structures.NETHER_FORTRESS, 0));
    public static final RegistryObject<Item> BASTION_EYE = ITEMS.register("bastion_eye",
            () -> new NorthstarEyeItem(new Item.Properties(), ModTags.Structures.BASTION_REMNANT, 0));
    public static final RegistryObject<Item> OCEAN_MONUMENT_EYE = ITEMS.register("ocean_monument_eye",
            () -> new NorthstarEyeItem(new Item.Properties(), ModTags.Structures.OCEAN_MONUMENT, 0));
    public static final RegistryObject<Item> ANCIENT_CITY_EYE = ITEMS.register("ancient_city_eye",
            () -> new NorthstarEyeItem(new Item.Properties(), ModTags.Structures.ANCIENT_CITY, 0));
    public static final RegistryObject<Item> END_CITY_EYE = ITEMS.register("end_city_eye",
            () -> new NorthstarEyeItem(new Item.Properties(), ModTags.Structures.END_CITY, 0));
    public static final RegistryObject<Item> JADE_EYE = ITEMS.register("jade_eye",
            () -> new NorthstarEyeItem(new Item.Properties(), ModTags.Structures.JADE_TRADING_POST, -2000));
    public static final RegistryObject<Item> HOME_PEARL = ITEMS.register("home_pearl",
            () -> new HomePearlItem(new Item.Properties()));

    public static final RegistryObject<Item> COPPER_INFUSED_EMERALD = ITEMS.register("copper_infused_emerald",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LAPIS_INFUSED_EMERALD = ITEMS.register("lapis_infused_emerald",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_INFUSED_EMERALD = ITEMS.register("iron_infused_emerald",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_INFUSED_EMERALD = ITEMS.register("gold_infused_emerald",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_INFUSED_EMERALD = ITEMS.register("redstone_infused_emerald",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_INFUSED_EMERALD = ITEMS.register("diamond_infused_emerald",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_INFUSED_EMERALD = ITEMS.register("netherite_infused_emerald",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> JADE_HOARDER_SPAWN_EGG = ITEMS.register("jadehoarder_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JADEHOARDER, 0x5a915d, 0x7aab7c, new Item.Properties()));

    public static final RegistryObject<Item> MURPHY_SPAWN_EGG = ITEMS.register("murphy_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MURPHY, 0x000000, 0x0760f0, new Item.Properties()));

    public static final RegistryObject<Item> LOCKED_TRADER_SPAWN_EGG = ITEMS.register("lockedtrader_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.LOCKEDTRADER, 0x000000, 0x222222, new Item.Properties()));

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));

    public static final RegistryObject<Item> JADE_SWORD = ITEMS.register("jade_sword",
            () -> new SwordItem(ModToolTiers.JADE, 4, -2, new Item.Properties()));

    public static final RegistryObject<Item> JADE_PICKAXE = ITEMS.register("jade_pickaxe",
            () -> new PickaxeItem(ModToolTiers.JADE, 1, -2.4F, new Item.Properties()));

    public static final RegistryObject<Item> JADE_AXE = ITEMS.register("jade_axe",
            () -> new AxeItem(ModToolTiers.JADE, 7, -2.7F, new Item.Properties()));

    public static final RegistryObject<Item> JADE_SHOVEL = ITEMS.register("jade_shovel",
            () -> new ShovelItem(ModToolTiers.JADE, 0, 0, new Item.Properties()));

    public static final RegistryObject<Item> JADE_HOE = ITEMS.register("jade_hoe",
            () -> new HoeItem(ModToolTiers.JADE, 0, 0, new Item.Properties()));

    public static final RegistryObject<Item> JADE_HELMET = ITEMS.register("jade_helmet",
            () -> new ModArmorItem(ModArmorMaterials.JADE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> JADE_CHESTPLATE = ITEMS.register("jade_chestplate",
            () -> new ArmorItem(ModArmorMaterials.JADE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> JADE_LEGGINGS = ITEMS.register("jade_leggings",
            () -> new ArmorItem(ModArmorMaterials.JADE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> JADE_BOOTS = ITEMS.register("jade_boots",
            () -> new ArmorItem(ModArmorMaterials.JADE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> CODEX = ITEMS.register("codex",
            () -> new BookItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
