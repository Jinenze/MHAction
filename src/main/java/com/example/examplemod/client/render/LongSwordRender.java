package com.example.examplemod.client.render;

import com.example.examplemod.client.ExampleModClient;
import com.example.examplemod.client.render.impl.ModBakedModel;
import com.example.examplemod.mixin.ItemRendererInvoker;
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

public class LongSwordRender implements BuiltinItemRendererRegistry.DynamicItemRenderer {
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
            matrices.translate(ExampleModClient.config.first_x, ExampleModClient.config.first_y, ExampleModClient.config.first_z);
            for (Direction direction : Direction.values()) {
                random.setSeed(42L);
                ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, direction, random), stack, light, overlay);
            }
            random.setSeed(42L);
            ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, null, random), stack, light, overlay);
            matrices.pop();
        } else if(mode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND){
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(ExampleModClient.config.third_x));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(ExampleModClient.config.third_y));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(ExampleModClient.config.third_z));
            for (Direction direction : Direction.values()) {
                random.setSeed(42L);
                ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, direction, random), stack, light, overlay);
            }
            random.setSeed(42L);
            ((ItemRendererInvoker) itemRenderer).mha$renderBakedItemQuads(matrices, vertexConsumer, model.getQuads(null, null, random), stack, light, overlay);
            matrices.pop();
        }else{
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
