package net.coleam.northstar.util;

import net.coleam.northstar.NorthStar;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NORTHSTAR_ORES = tag("northstar_ores");
        public static final TagKey<Block> NORTHSTAR_PORTAL_BLOCKS = tag("northstar_portal_blocks");
        public static final TagKey<Block> NEEDS_JADE_TOOL = tag("needs_jade_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(NorthStar.MOD_ID, name));
        }
    }

    public static class items {
        public static final TagKey<Item> INFUSED_EMERALDS = tag("infused_emeralds");
        public static final TagKey<Item> NORTHSTAR_EYES = tag("northstar_eyes");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(NorthStar.MOD_ID, name));
        }
    }

    public static class Structures {
        public static final TagKey<Structure> NETHER_FORTRESS = tag("minecrcaft", "fortress");
        public static final TagKey<Structure> BASTION_REMNANT = tag("minecrcaft", "bastion_remnant");
        public static final TagKey<Structure> OCEAN_MONUMENT = tag("minecrcaft", "monument");
        public static final TagKey<Structure> ANCIENT_CITY = tag("minecrcaft", "ancient_city");
        public static final TagKey<Structure> END_CITY = tag("minecrcaft", "end_city");
        public static final TagKey<Structure> JADE_TRADING_POST = tag(NorthStar.MOD_ID, "jadetradingpost");
        private static TagKey<Structure> tag(String mod, String name) {
            return TagKey.create(Registries.STRUCTURE, new ResourceLocation(mod, name));
        }
    }
}
