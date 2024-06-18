package com.jez.mha.client.render;

import com.jez.mha.MHAction;
import com.jez.mha.client.ModClient;
import com.jez.mha.client.render.impl.ModBakedModel;
import com.jez.mha.mixin.ItemRendererInvoker;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.impl.IAnimatedPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class LongSwordRender implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        BakedModel model = ((ModBakedModel) itemRenderer.getModel(stack, null, null, 0)).getWrappedModel();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, false));

        if (mode == ModelTransformationMode.GUI) {
            ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
            return;
        }

        matrices.pop();
        matrices.pop();
        matrices.push();

        MinecraftClient mc = MinecraftClient.getInstance();
        ModelPart modelPart = ((PlayerEntityRenderer) mc.getEntityRenderDispatcher().getRenderer(mc.player)).getModel().rightArm;
        matrices.translate(modelPart.pivotX / 16.0f, modelPart.pivotY / 16.0f, modelPart.pivotZ / 16.0f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
        //左右手位置？
        matrices.translate(0.0625f, 0.125f, -0.625f);

//        if (mode.isFirstPerson()) {
//            matrices.translate(ModClient.config.itemRender.first_translate_x, ModClient.config.itemRender.first_translate_y, ModClient.config.itemRender.first_translate_z);
//            matrices.scale(ModClient.config.itemRender.first_scale_x, ModClient.config.itemRender.first_scale_y, ModClient.config.itemRender.first_scale_z);
//            matrices.multiply(RotationAxis.POSITIVE_X.rotation(ModClient.config.itemRender.first_multiply_x));
//            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(ModClient.config.itemRender.first_multiply_y));
//            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(ModClient.config.itemRender.first_multiply_z));
//            ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
//            matrices.pop();
//        } else
        if (mode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND) {
            matrices.translate(ModClient.config.itemRender.third_translate_x, ModClient.config.itemRender.third_translate_y, ModClient.config.itemRender.third_translate_z);
            matrices.scale(ModClient.config.itemRender.third_scale_x, ModClient.config.itemRender.third_scale_y, ModClient.config.itemRender.third_scale_z);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(ModClient.config.itemRender.third_multiply_x));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(ModClient.config.itemRender.third_multiply_y));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(ModClient.config.itemRender.third_multiply_z));

            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            var animationPlayer = ((IAnimatedPlayer) player).playerAnimator_getAnimation();
            if (animationPlayer.isActive()) {
                matrices.push();
                Vec3f vec3f1 = animationPlayer.get3DTransform("rightItem", TransformType.POSITION, Vec3f.ZERO).scale(1 / 16f);
                matrices.translate(vec3f1.getX(), vec3f1.getY(), vec3f1.getZ());
                Vec3f vec3f2 = animationPlayer.get3DTransform("rightItem", TransformType.ROTATION, Vec3f.ZERO);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotation(vec3f2.getZ()));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotation(vec3f2.getY()));
                matrices.multiply(RotationAxis.POSITIVE_X.rotation(vec3f2.getX()));
                matrices.translate(-0.5f, -0.5f, -0.5f);
                BakedModel bakedModel = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(new Identifier(MHAction.MODID, "katana"), "inventory"));
                ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemModel(bakedModel, stack, light, overlay, matrices, vertexConsumer);
                matrices.pop();

                matrices.pop();
                matrices.push();
                ModelPart modelPart1 = ((PlayerEntityRenderer) mc.getEntityRenderDispatcher().getRenderer(mc.player)).getModel().leftArm;
                matrices.translate(modelPart1.pivotX / 16.0f, modelPart1.pivotY / 16.0f, modelPart1.pivotZ / 16.0f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
                matrices.translate(-0.0625f, 0.125f, -0.625f);
                Vec3f vec3f3 = animationPlayer.get3DTransform("leftItem", TransformType.POSITION, Vec3f.ZERO).scale(1 / 16f);
                matrices.translate(vec3f3.getX(), vec3f3.getY(), vec3f3.getZ());
                Vec3f vec3f4 = animationPlayer.get3DTransform("leftItem", TransformType.ROTATION, Vec3f.ZERO);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotation(vec3f4.getZ()));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotation(vec3f4.getY()));
                matrices.multiply(RotationAxis.POSITIVE_X.rotation(vec3f4.getX()));
                matrices.translate(-0.5f, -0.5f, -0.5f);
                ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemModel(MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(new Identifier(MHAction.MODID, "katanaa"), "inventory")), stack, light, overlay, matrices, vertexConsumer);
            } else {
                model.getTransformation().getTransformation(mode).apply(false, matrices);
                matrices.translate(-0.5f, -0.5f, -0.5f);
                ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
            }
            matrices.pop();
        } else {
            matrices.pop();
        }

        matrices.push();
        matrices.push();
    }
}
