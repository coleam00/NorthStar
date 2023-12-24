package net.coleam.northstar.datagen;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NorthStar.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.JADE);
        simpleItem(ModItems.JADE_DUST);

        simpleItem(ModItems.VILLAGE_EYE);
        simpleItem(ModItems.MINESHAFT_EYE);
        simpleItem(ModItems.NETHER_FORTRESS_EYE);
        simpleItem(ModItems.BASTION_EYE);
        simpleItem(ModItems.OCEAN_MONUMENT_EYE);
        simpleItem(ModItems.ANCIENT_CITY_EYE);
        simpleItem(ModItems.END_CITY_EYE);
        simpleItem(ModItems.JADE_EYE);

        simpleItem(ModItems.HOME_PEARL);

        simpleItem(ModItems.COPPER_INFUSED_EMERALD);
        simpleItem(ModItems.LAPIS_INFUSED_EMERALD);
        simpleItem(ModItems.IRON_INFUSED_EMERALD);
        simpleItem(ModItems.GOLD_INFUSED_EMERALD);
        simpleItem(ModItems.REDSTONE_INFUSED_EMERALD);
        simpleItem(ModItems.DIAMOND_INFUSED_EMERALD);
        simpleItem(ModItems.NETHERITE_INFUSED_EMERALD);

        simpleItem(ModItems.CODEX);

        simpleItem(ModItems.METAL_DETECTOR);

        withExistingParent(ModItems.JADE_HOARDER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MURPHY_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.LOCKED_TRADER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        handleHeldItem(ModItems.JADE_SWORD);
        handleHeldItem(ModItems.JADE_PICKAXE);
        handleHeldItem(ModItems.JADE_AXE);
        handleHeldItem(ModItems.JADE_SHOVEL);
        handleHeldItem(ModItems.JADE_HOE);

        trimmedArmorItem(ModItems.JADE_HELMET);
        trimmedArmorItem(ModItems.JADE_CHESTPLATE);
        trimmedArmorItem(ModItems.JADE_LEGGINGS);
        trimmedArmorItem(ModItems.JADE_BOOTS);
    }

    private ItemModelBuilder handleHeldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(NorthStar.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(NorthStar.MOD_ID, "item/" + item.getId().getPath()));
    }

    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = NorthStar.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }
}
