package net.coleam.northstar.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
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

public class NorthstarEyeItem extends Item {
    private final TagKey<Structure> structureTag;
    private final int maxZ;

    public NorthstarEyeItem(Properties pProperties, TagKey<Structure> itemStructureTag, int itemMaxZ) {
        super(pProperties);
        this.structureTag = itemStructureTag;
        this.maxZ = itemMaxZ;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        if (pLevel instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel)pLevel;
            BlockPos blockpos = serverlevel.findNearestMapStructure(this.structureTag, pPlayer.blockPosition(), 100, false);
            if (blockpos != null) {
                EyeOfEnder eyeofender = new EyeOfEnder(pLevel, pPlayer.getX(), pPlayer.getY(0.5D), pPlayer.getZ());
                eyeofender.setItem(itemstack);

                if (blockpos.getZ() < maxZ || maxZ >= 0) {
                    eyeofender.signalTo(blockpos);
                }
                else {
                    eyeofender.signalTo(new BlockPos((int)pPlayer.getX(), (int)pPlayer.getY(), (int)(pPlayer.getZ() - 1000)));
                    pPlayer.sendSystemMessage(Component.literal("The magic here is not strong enough for this pearl... You must travel further north for it to guide you!"));
                }

                pLevel.gameEvent(GameEvent.PROJECTILE_SHOOT, eyeofender.position(), GameEvent.Context.of(pPlayer));
                pLevel.addFreshEntity(eyeofender);
                if (pPlayer instanceof ServerPlayer) {
                    CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)pPlayer, blockpos);
                }

                pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                pLevel.levelEvent((Player)null, 1003, pPlayer.blockPosition(), 0);
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                pPlayer.swing(pHand, true);
                return InteractionResultHolder.success(itemstack);
            }
        }

        return InteractionResultHolder.consume(itemstack);
    }
}
