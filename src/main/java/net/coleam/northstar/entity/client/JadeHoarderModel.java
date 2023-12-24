package net.coleam.northstar.entity.client;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.entity.custom.JadeHoarderEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class JadeHoarderModel extends GeoModel<JadeHoarderEntity> {
    @Override
    public ResourceLocation getModelResource(JadeHoarderEntity animatable) {
        return new ResourceLocation(NorthStar.MOD_ID, "geo/jadehoarder.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JadeHoarderEntity animatable) {
        return new ResourceLocation(NorthStar.MOD_ID, "textures/entity/jadehoarder.png");
    }

    @Override
    public ResourceLocation getAnimationResource(JadeHoarderEntity animatable) {
        return new ResourceLocation(NorthStar.MOD_ID, "animations/jadehoarder.animation.json");
    }

    @Override
    public void setCustomAnimations(JadeHoarderEntity animatable, long instanceId, AnimationState<JadeHoarderEntity> animationState) {
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
