package com.example.examplemod.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ModSwordItem extends Item implements ModSword {
    public static void attack(ServerPlayerEntity player, Entity target) {
        //isAttackable()在hitbox里
        if (target.handleAttack(player)) {
            return;
        }
        if (target instanceof EnderDragonPart) {
            target = ((EnderDragonPart) target).owner;
        }
        ItemStack itemStack = player.getMainHandStack();
        if (target instanceof LivingEntity && itemStack.getItem() instanceof ModSwordItem item) {
            itemStack.postHit((LivingEntity) target, player);
            target.damage(player.getDamageSources().playerAttack(player), item.getAttackDamage());
            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), item.getAttackSound(), SoundCategory.PLAYERS);
        }
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(this.getStackDamage(target), attacker, p -> {
        });
        return true;
    }

    public ModSwordItem(Settings settings) {
        super(settings);
    }
}
