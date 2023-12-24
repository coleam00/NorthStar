package net.coleam.northstar.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.coleam.northstar.NorthStar;
import net.coleam.northstar.entity.custom.JadeHoarderEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class JadeHoarderRenderer extends GeoEntityRenderer<JadeHoarderEntity> {

    public JadeHoarderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new JadeHoarderModel());
    }

    @Override
    public ResourceLocation getTextureLocation(JadeHoarderEntity animatable) {
        return new ResourceLocation(NorthStar.MOD_ID, "textures/entity/jadehoarder.png");
    }

    @Override
    public void render(JadeHoarderEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        // if(entity.isBaby()) {
        //     poseStack.scale(0.4f, 0.4f, 0.4f);
        // }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
