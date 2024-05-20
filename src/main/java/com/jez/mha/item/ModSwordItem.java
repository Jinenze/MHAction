package com.jez.mha.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ModSwordItem extends Item implements ModSword {
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int damage = stack.getDamage() + this.getStackDamage(target);
        if (damage > stack.getMaxDamage()) {
            stack.setDamage(stack.getDamage());
        } else {
            stack.setDamage(damage);
        }
        return true;
    }

    public Item getSwordItem(){
        return null;
    }

    public Item getSheathItem(){
        return null;
    }

    public ModSwordItem(Settings settings) {
        super(settings);
    }
}
