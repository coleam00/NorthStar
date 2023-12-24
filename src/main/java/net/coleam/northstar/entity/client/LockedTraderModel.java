package net.coleam.northstar.entity.client;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.entity.custom.LockedTraderEntity;
import net.coleam.northstar.entity.custom.MurphyEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class LockedTraderModel extends GeoModel<LockedTraderEntity> {
    @Override
    public ResourceLocation getModelResource(LockedTraderEntity animatable) {
        return new ResourceLocation(NorthStar.MOD_ID, "geo/lockedtrader.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LockedTraderEntity animatable) {
        return new ResourceLocation(NorthStar.MOD_ID, "textures/entity/lockedtrader.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LockedTraderEntity animatable) {
        return new ResourceLocation(NorthStar.MOD_ID, "animations/lockedtrader.animation.json");
    }

    @Override
    public void setCustomAnimations(LockedTraderEntity animatable, long instanceId, AnimationState<LockedTraderEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");


        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            float headPitch = 20.0f;
            float headYaw = 75.0f;

            float currHeadPitch = entityData.headPitch();
            float currHeadYaw = entityData.netHeadYaw();

            if (Math.abs(currHeadPitch) < headPitch) {
                headPitch = currHeadPitch;
            }
            else {
                headPitch = (currHeadPitch * headPitch) / Math.abs(currHeadPitch);
            }

            if (Math.abs(currHeadYaw) < headYaw) {
                headYaw = currHeadYaw;
            }
            else {
                headYaw = (currHeadYaw * headYaw) / Math.abs(currHeadYaw);
            }

            head.setRotX(headPitch * Mth.DEG_TO_RAD);
            head.setRotY(headYaw * Mth.DEG_TO_RAD);
        }
    }
}
