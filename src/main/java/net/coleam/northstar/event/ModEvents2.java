package net.coleam.northstar.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.coleam.northstar.NorthStar;
import net.coleam.northstar.item.ModItems;
import net.coleam.northstar.villager.ModVillagers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = NorthStar.MOD_ID)
public class ModEvents2 {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player && !Objects.isNull(event.getSource().getEntity()) && !(event.getSource().getEntity() instanceof Player)) {
            Player player = (Player) event.getEntity();
            double distanceNorth = calculateDistanceNorth(player);

            float newDamage = event.getAmount() * (1.0F + (float)distanceNorth / 5000.0F);
            event.setAmount(newDamage);
        }

        // Villagers can't be hurt above Y 200
        double yPos = event.getEntity().getY();
        if (event.getEntity() instanceof Villager) {
            // Check if the Y-coordinate is above 200
            if (yPos > 200) {
                // Cancel the interaction event to prevent the villager from taking damage
                event.setCanceled(true);
            }
        }
    }

    private static double calculateDistanceNorth(Player player) {
        // Assuming 0,0 is our reference point
        double zCoord = player.getZ();
        return Math.abs(Math.min(zCoord, 0)); // Only increases if north of 0,0
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        // Get the position of the block
        BlockPos pos = event.getPos();

        // Check if the block is within the range and the player is not in creative mode
        if (pos.getY() > 200 && !event.getPlayer().isCreative()) {
            // Cancel the event
            event.setCanceled(true);
        }
    }

    public static boolean isPlayerWithinDistance(Player player, BlockPos targetPos, int numBlocks) {
        // Get the player's position as a Vec3
        Vec3 playerPos = player.position();

        // Calculate the distance between the player and the target position
        double distance = Math.sqrt(playerPos.distanceToSqr(Vec3.atCenterOf(targetPos)));

        // Check if the distance is within the specified range
        return distance <= numBlocks;
    }

    public static boolean hasAdvancement(ServerPlayer player, MinecraftServer server, String advancementId) {
        // Get the advancement from the advancement manager
        Advancement advancement = server.getAdvancements().getAdvancement(new ResourceLocation(advancementId));

        if (advancement != null) {
            // Get the player's advancement progress
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
            // Check if the advancement is completed
            return progress.isDone();
        }

        return false;
    }

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();

        // These events can only occur in the overworld and on the server side
        if (event.getLevel() instanceof ServerLevel && event.getLevel().dimension().equals(Level.OVERWORLD)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;

            // Check if sneak right clicking the Northstar guide book
            if (player.isCrouching()) {
                boolean hasNorthStarStartAdvancement = hasAdvancement(serverPlayer, Objects.requireNonNull(serverPlayer.getServer()), "minecraft:story/mine_diamond");
                CompoundTag itemShareTag = player.getMainHandItem().getShareTag();
                if (hasNorthStarStartAdvancement && !Objects.isNull(itemShareTag) && itemShareTag.toString().contains("northstar:codex")) {
                    int[] coordinates = CoordinateGenerator.generateCoordinates(event.getEntity().getUUID());
                    BlockPos hallOfProgressCoords = new BlockPos(coordinates[0], 250, coordinates[1]);

                    // If the player is in the hall of progress, teleport them out
                    // Otherwise, teleport them in
                    if (isPlayerWithinDistance(player, hallOfProgressCoords, 75)) {
                        CompoundTag playerData = serverPlayer.getPersistentData();
                        if (playerData.contains("NorthstarOrigCords")) {
                            CompoundTag teleportData = playerData.getCompound("NorthstarOrigCords");
                            double origX = teleportData.getDouble("OrigX");
                            double origY = teleportData.getDouble("OrigY");
                            double origZ = teleportData.getDouble("OrigZ");

                            // Teleport the player back
                            player.teleportTo(origX, origY, origZ);

                            // Clear the data after teleporting back
                            playerData.remove("NorthstarOrigCords");
                        }
                        else {
                            // If their coordinates before teleporting into the hall of progress aren't
                            // saved for whatever reason, teleport them to their respawn point
                            BlockPos respawnPosition = serverPlayer.getRespawnPosition();

                            if (!Objects.isNull(respawnPosition)) {
                                player.teleportTo(respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ());
                            }
                            else {
                                // Default teleport if respawn point is not set
                                player.teleportTo(0, 75, 0);
                            }
                        }
                    }
                    else {
                        // Teleports the player to the hall of progress and saves their coordinates
                        // before teleporting for their return
                        // Save the player's current position to NBT
                        CompoundTag playerData = serverPlayer.getPersistentData();
                        CompoundTag teleportData = new CompoundTag();
                        teleportData.putDouble("OrigX", serverPlayer.getX());
                        teleportData.putDouble("OrigY", serverPlayer.getY());
                        teleportData.putDouble("OrigZ", serverPlayer.getZ());
                        playerData.put("NorthstarOrigCords", teleportData);

                        player.teleportTo(hallOfProgressCoords.getX() + 5, hallOfProgressCoords.getY() + 3, hallOfProgressCoords.getZ() + 13);
                    }

                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        BlockState state = event.getLevel().getBlockState(pos);

        // Check if the block is a trapdoor and the player is not in creative mode
        if (state.getBlock() instanceof TrapDoorBlock && !player.isCreative()) {
            // Check if the Y-coordinate is above 200
            if (pos.getY() > 200) {
                // Cancel the interaction event to prevent the trapdoor from opening
                event.setCanceled(true);
            }
        }
    }

    public class StructureLoader {
        public static CompoundTag loadStructure(String structurePath) {
            try {
                InputStream inputStream = ServerLifecycleHooks.getCurrentServer().getResourceManager().open(new ResourceLocation(structurePath));
                return NbtIo.readCompressed(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static void placeNorthStarStructure(String structureName, StructureTemplateManager manager, BlockPos pos, ServerLevel serverLevel, RandomSource random) {
        // ResourceLocation location = new ResourceLocation(NorthStar.MOD_ID, structureName);
        // StructureTemplate template = manager.getOrCreate(location);
        // manager.save(location);

        CompoundTag structureTag = StructureLoader.loadStructure(NorthStar.MOD_ID + ":structures/" + structureName + ".nbt");
        if (structureTag == null) {
            System.out.println("Failed to load structure " + structureName);
            return;
        }

        StructureTemplate template = manager.readStructure(structureTag);
        template.placeInWorld(serverLevel, pos, pos, new StructurePlaceSettings(), random, 3);
    }

    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
        Advancement advancement = event.getAdvancement();
        if (advancement.getId().toString().equals("minecraft:story/mine_diamond")) {
            StructureTemplateManager manager = Objects.requireNonNull(event.getEntity().getServer()).getStructureManager();
            ServerLevel serverLevel = event.getEntity().getServer().getLevel(Level.OVERWORLD);
            RandomSource random = event.getEntity().getRandom();

            // Gets the coordinates for the player based on their UUID.
            int[] coordinates = CoordinateGenerator.generateCoordinates(event.getEntity().getUUID());
            System.out.println("X for hall is: " + coordinates[0] + ", Z for hall is: " + coordinates[1]);

            int startX = coordinates[0];
            int startY = 250;
            int startZ = coordinates[1];

            if (serverLevel != null) {
                BlockPos pos = new BlockPos(startX, startY, startZ);
                placeNorthStarStructure("hallstart", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 14, startY - 25, startZ);
                placeNorthStarStructure("hallmain1", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 14, startY - 24, startZ + 14);
                placeNorthStarStructure("hallmain2", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 25, startY - 23, startZ);
                placeNorthStarStructure("hallmain3", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 25, startY - 22, startZ + 14);
                placeNorthStarStructure("hallmain4", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 31, startY - 21, startZ);
                placeNorthStarStructure("hallmain5", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 31, startY - 20, startZ + 14);
                placeNorthStarStructure("hallmain6", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 37, startY - 19, startZ);
                placeNorthStarStructure("hallmain7", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 37, startY - 18, startZ + 14);
                placeNorthStarStructure("hallmain8", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 43, startY - 17, startZ);
                placeNorthStarStructure("hallmain9", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 43, startY - 16, startZ + 14);
                placeNorthStarStructure("hallmain10", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 49, startY - 13, startZ);
                placeNorthStarStructure("hallmainend", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 21, startY - 9, startZ - 26);
                placeNorthStarStructure("hallchallengeroom1", manager, pos, serverLevel, random);
                pos = new BlockPos(startX + 25, startY - 6, startZ + 27);
                placeNorthStarStructure("hallchallengeroom2", manager, pos, serverLevel, random);
            }

            /*
            Stream<ResourceLocation> templates = manager.listTemplates();
            // Using the stream to loop through each ResourceLocation
            templates.forEach(location -> {
                // Getting the namespace and path
                String namespace = location.getNamespace();
                String path = location.getPath();

                // Printing out the full name
                System.out.println("Resource: " + namespace + ":" + path);
            });
            */
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.isEndConquered()) { // Check to ensure it's a regular respawn, not end dimension respawn
            // Create the ItemStack for the item you want to give
            ItemStack itemStack = new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("patchouli:guide_book"))));

            // Set the NBT data for the item
            CompoundTag nbtData = new CompoundTag();
            nbtData.putString("patchouli:book", "northstar:codex");
            itemStack.setTag(nbtData);

            // Give the item to the player
            if (!event.getEntity().getInventory().add(itemStack)) {
                // If the player's inventory is full, drop the item in the world
                event.getEntity().drop(itemStack, false);
            }
        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.ARMORER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.JADE.get(), 1),
                    10, 8, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.JADE_HELMET.get(), 1),
                    5, 10, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModItems.JADE_CHESTPLATE.get(), 1),
                    5, 16, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 14),
                    new ItemStack(ModItems.JADE_LEGGINGS.get(), 1),
                    5, 14, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 8),
                    new ItemStack(ModItems.JADE_BOOTS.get(), 1),
                    5, 8, 0.02f
            ));
        }

        if (event.getType() == VillagerProfession.LIBRARIAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            ItemStack enchantedBook = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.ALL_DAMAGE_PROTECTION, 1));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    enchantedBook,
                    4, 10, 0.02f
            ));
        }

        if (event.getType() == ModVillagers.JADE_MASTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.JADE_DUST.get(), 1),
                    10, 8, 0.02f
            ));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.JADE.get(), 1),
                    10, 8, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.JADE_HELMET.get(), 1),
                    5, 10, 0.02f
            ));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModItems.JADE_CHESTPLATE.get(), 1),
                    5, 16, 0.02f
            ));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 14),
                    new ItemStack(ModItems.JADE_LEGGINGS.get(), 1),
                    5, 14, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 8),
                    new ItemStack(ModItems.JADE_BOOTS.get(), 1),
                    5, 8, 0.02f
            ));
        }

        if (event.getType() == ModVillagers.ENDER_TRADER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(Items.ENDER_EYE, 1),
                    new ItemStack(ModItems.VILLAGE_EYE.get(), 1),
                    10, 30, 0.02f
            ));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(Items.ENDER_EYE, 1),
                    new ItemStack(ModItems.MINESHAFT_EYE.get(), 1),
                    10, 30, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.GOLD_INFUSED_EMERALD.get(), 2),
                    new ItemStack(Items.ENDER_EYE, 1),
                    new ItemStack(ModItems.NETHER_FORTRESS_EYE.get(), 1),
                    10, 30, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.GOLD_INFUSED_EMERALD.get(), 2),
                    new ItemStack(Items.ENDER_EYE, 1),
                    new ItemStack(ModItems.BASTION_EYE.get(), 1),
                    10, 30, 0.02f
            ));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.DIAMOND_INFUSED_EMERALD.get(), 2),
                    new ItemStack(Items.ENDER_EYE, 1),
                    new ItemStack(ModItems.OCEAN_MONUMENT_EYE.get(), 1),
                    10, 30, 0.02f
            ));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.DIAMOND_INFUSED_EMERALD.get(), 2),
                    new ItemStack(Items.ENDER_EYE, 1),
                    new ItemStack(ModItems.ANCIENT_CITY_EYE.get(), 1),
                    10, 30, 0.02f
            ));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.NETHERITE_INFUSED_EMERALD.get(), 2),
                    new ItemStack(Items.ENDER_EYE, 1),
                    new ItemStack(ModItems.END_CITY_EYE.get(), 1),
                    10, 30, 0.02f
            ));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.NETHERITE_INFUSED_EMERALD.get(), 2),
                    new ItemStack(Items.ENDER_PEARL, 1),
                    new ItemStack(ModItems.HOME_PEARL.get(), 1),
                    10, 30, 0.02f
            ));
        }

        if (event.getType() == ModVillagers.MYSTIC_MERCENARY.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.COPPER_INFUSED_EMERALD.get(), 1),
                    16, 8, 0.02f
            ));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ModItems.IRON_INFUSED_EMERALD.get(), 1),
                    16, 12, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COPPER_INFUSED_EMERALD.get(), 1),
                    new ItemStack(ModItems.IRON_INFUSED_EMERALD.get(), 1),
                    new ItemStack(ModItems.GOLD_INFUSED_EMERALD.get(), 1),
                    16, 16, 0.02f
            ));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ModItems.GOLD_INFUSED_EMERALD.get(), 1),
                    new ItemStack(ModItems.REDSTONE_INFUSED_EMERALD.get(), 1),
                    16, 16, 0.02f
            ));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ModItems.GOLD_INFUSED_EMERALD.get(), 1),
                    new ItemStack(ModItems.LAPIS_INFUSED_EMERALD.get(), 1),
                    16, 16, 0.02f
            ));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.REDSTONE_INFUSED_EMERALD.get(), 1),
                    new ItemStack(ModItems.LAPIS_INFUSED_EMERALD.get(), 1),
                    new ItemStack(ModItems.DIAMOND_INFUSED_EMERALD.get(), 1),
                    16, 24, 0.02f
            ));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.DIAMOND_INFUSED_EMERALD.get(), 2),
                    new ItemStack(ModItems.NETHERITE_INFUSED_EMERALD.get(), 1),
                    16, 32, 0.02f
            ));
        }
    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.JADE_HELMET.get(), 1),
                5, 10, 0.2f
        ));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 16),
                new ItemStack(ModItems.JADE_CHESTPLATE.get(), 1),
                5, 16, 0.2f
        ));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 14),
                new ItemStack(ModItems.JADE_LEGGINGS.get(), 1),
                5, 14, 0.2f
        ));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 8),
                new ItemStack(ModItems.JADE_BOOTS.get(), 1),
                5, 8, 0.2f
        ));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 30),
                new ItemStack(ModItems.METAL_DETECTOR.get(), 1),
                2, 30, 0.2f
        ));
    }
}
