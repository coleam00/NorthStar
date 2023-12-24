package net.coleam.northstar.item;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NorthStar.MOD_ID);

    public static final RegistryObject<CreativeModeTab> NORTHSTAR_TAB = CREATIVE_MODE_TABS.register("northstar_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VILLAGE_EYE.get()))
                    .title(Component.translatable("creative.northstar_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.JADE.get());
                        pOutput.accept(ModItems.JADE_DUST.get());

                        pOutput.accept(ModItems.VILLAGE_EYE.get());
                        pOutput.accept(ModItems.MINESHAFT_EYE.get());
                        pOutput.accept(ModItems.NETHER_FORTRESS_EYE.get());
                        pOutput.accept(ModItems.BASTION_EYE.get());
                        pOutput.accept(ModItems.OCEAN_MONUMENT_EYE.get());
                        pOutput.accept(ModItems.ANCIENT_CITY_EYE.get());
                        pOutput.accept(ModItems.END_CITY_EYE.get());
                        pOutput.accept(ModItems.JADE_EYE.get());

                        pOutput.accept(ModItems.HOME_PEARL.get());

                        pOutput.accept(ModItems.COPPER_INFUSED_EMERALD.get());
                        pOutput.accept(ModItems.LAPIS_INFUSED_EMERALD.get());
                        pOutput.accept(ModItems.IRON_INFUSED_EMERALD.get());
                        pOutput.accept(ModItems.GOLD_INFUSED_EMERALD.get());
                        pOutput.accept(ModItems.REDSTONE_INFUSED_EMERALD.get());
                        pOutput.accept(ModItems.DIAMOND_INFUSED_EMERALD.get());
                        pOutput.accept(ModItems.NETHERITE_INFUSED_EMERALD.get());

                        pOutput.accept(ModItems.JADE_HOARDER_SPAWN_EGG.get());
                        pOutput.accept(ModItems.MURPHY_SPAWN_EGG.get());
                        pOutput.accept(ModItems.LOCKED_TRADER_SPAWN_EGG.get());

                        pOutput.accept(ModItems.JADE_SWORD.get());
                        pOutput.accept(ModItems.JADE_PICKAXE.get());
                        pOutput.accept(ModItems.JADE_AXE.get());
                        pOutput.accept(ModItems.JADE_SHOVEL.get());
                        pOutput.accept(ModItems.JADE_HOE.get());

                        pOutput.accept(ModItems.JADE_HELMET.get());
                        pOutput.accept(ModItems.JADE_CHESTPLATE.get());
                        pOutput.accept(ModItems.JADE_LEGGINGS.get());
                        pOutput.accept(ModItems.JADE_BOOTS.get());

                        pOutput.accept(ModItems.METAL_DETECTOR.get());

                        pOutput.accept(ModBlocks.JADE_BLOCK.get());
                        pOutput.accept(ModBlocks.JADE_ORE.get());
                        pOutput.accept(ModBlocks.LOCKED.get());
                        pOutput.accept(ModBlocks.JADE_PORTAL_BLOCK.get());
                        pOutput.accept(ModBlocks.INFUSED_EMERALD_BLOCK.get());
                        pOutput.accept(ModBlocks.ENDER_OBSIDIAN.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
