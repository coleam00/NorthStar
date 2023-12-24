package net.coleam.northstar.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Objects;

public class HomePearlItem extends Item {
    public HomePearlItem(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        if (pLevel instanceof ServerLevel) {
            ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
            BlockPos respawnPosition = serverPlayer.getRespawnPosition();

            if (!Objects.isNull(respawnPosition)) {
                pPlayer.teleportTo(respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ());
            }
            else {
                pPlayer.sendSystemMessage(Component.literal("Set your respawn point first to use the home pearl!"));
            }
        }

        return InteractionResultHolder.success(itemstack);
    }
}
