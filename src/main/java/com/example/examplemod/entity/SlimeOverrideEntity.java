package com.example.examplemod.entity;

import com.example.examplemod.client.action.ClientActionRunner;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;

public class SlimeOverrideEntity extends SlimeEntity {
    @Override
    protected void damage(LivingEntity target){
        if(this.isAlive()){
            //发包到Client
            ClientActionRunner.actionAttackCallBack();
        }
    }
    @Override
    public boolean damage(DamageSource source, float amount){
        if(this.isAlive()){
            ClientActionRunner.actionAttackCallBack();
        }
        return true;
    }
    public SlimeOverrideEntity(EntityType<? extends SlimeEntity> entityType, World world) {
        super(entityType, world);
    }
}
