package net.coleam.northstar.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.coleam.northstar.NorthStar;
import net.coleam.northstar.entity.custom.MurphyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MurphyRenderer extends GeoEntityRenderer<MurphyEntity> {

    public MurphyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MurphyModel());
    }

    @Override
    public ResourceLocation getTextureLocation(MurphyEntity animatable) {
        return new ResourceLocation(NorthStar.MOD_ID, "textures/entity/murphy.png");
    }

    @Override
    public void render(MurphyEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        // if(entity.isBaby()) {
        //     poseStack.scale(0.4f, 0.4f, 0.4f);
        // }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
