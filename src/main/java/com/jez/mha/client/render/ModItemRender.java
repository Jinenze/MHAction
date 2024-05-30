package com.jez.mha.client.render;

import com.jez.mha.client.ModClient;
import com.jez.mha.client.render.impl.ModBakedModel;
import com.jez.mha.mixin.ItemRendererInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class ModItemRender implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final Random random = Random.create();

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isEmpty()) {
            return;
        }
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        BakedModel model = ((ModBakedModel) itemRenderer.getModel(stack, null, null, 0)).getWrappedModel();
        if (model == null) {
            return;
        }
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, false));
        matrices.push();
        if (mode.isFirstPerson()) {
            matrices.translate(ModClient.config.itemRender.first_translate_x, ModClient.config.itemRender.first_translate_y, ModClient.config.itemRender.first_translate_z);
            matrices.scale(ModClient.config.itemRender.first_scale_x, ModClient.config.itemRender.first_scale_y, ModClient.config.itemRender.first_scale_z);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(ModClient.config.itemRender.first_multiply_x));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(ModClient.config.itemRender.first_multiply_y));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(ModClient.config.itemRender.first_multiply_z));
            for (Direction direction : Direction.values()) {
                random.setSeed(42L);
                ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, direction, random), stack, light, overlay);
            }
            random.setSeed(42L);
            ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, null, random), stack, light, overlay);
            matrices.pop();
        } else if (mode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND) {
            matrices.translate(ModClient.config.itemRender.third_translate_x, ModClient.config.itemRender.third_translate_y, ModClient.config.itemRender.third_translate_z);
            matrices.scale(ModClient.config.itemRender.third_scale_x, ModClient.config.itemRender.third_scale_y, ModClient.config.itemRender.third_scale_z);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(ModClient.config.itemRender.third_multiply_x));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(ModClient.config.itemRender.third_multiply_y));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(ModClient.config.itemRender.third_multiply_z));
            for (Direction direction : Direction.values()) {
                random.setSeed(42L);
                ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, direction, random), stack, light, overlay);
            }
            random.setSeed(42L);
            ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, null, random), stack, light, overlay);
            matrices.pop();
        } else {
            for (Direction direction : Direction.values()) {
                random.setSeed(42L);
                ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, direction, random), stack, light, overlay);
            }
            random.setSeed(42L);
            ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, null, random), stack, light, overlay);
            matrices.pop();
        }
    }
}
