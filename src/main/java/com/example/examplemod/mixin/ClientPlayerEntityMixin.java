package com.example.examplemod.mixin;

import com.example.examplemod.client.action.Action;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//@Mixin(ClientPlayerEntity.class)
//public abstract class ClientPlayerEntityMixin extends Entity {
//
//    public ClientPlayerEntityMixin(EntityType<?> type, World world) {
//        super(type, world);
//    }
//
//    @Inject(method = "tick", at = @At("RETURN"))
//    private void tickInject(CallbackInfo ci) {
//        if (Action.stopping) {
//            this.setVelocity(Vec3d.ZERO);
//        }
//    }
//}