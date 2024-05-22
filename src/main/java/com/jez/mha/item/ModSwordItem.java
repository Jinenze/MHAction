package com.jez.mha.item;

import com.jez.mha.client.ModClient;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
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

    //拿出来的时候nbt为空所以会合并一次
    @Override
    public void equip(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();
        NbtCompound nbt = inventory.main.get(inventory.selectedSlot).getNbt();
        if (nbt != null) {
            if (nbt.get("sheath_damage") == null) {
                unload(player);
            } else {
                if (inventory.offHand.get(0) != ItemStack.EMPTY) {
                    if (player.getWorld() instanceof ServerWorld world) {
                        new ItemEntity(world, player.getX(), player.getY(), player.getZ(), inventory.offHand.get(0));
                    }
                }
                inventory.offHand.set(0, new ItemStack(getSheathItem()));
                ItemStack itemStack = new ItemStack(getSwordItem());
                itemStack.getOrCreateNbt().putInt(ItemStack.DAMAGE_KEY, nbt.getInt("sheath_damage"));
                inventory.main.set(inventory.selectedSlot, itemStack);
                if(player.getWorld() instanceof ClientWorld){
                    ModClient.processor.playSubAnim(getSubAnim());
                }
            }
        } else {
            unload(player);
        }
    }

    private void unload(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();
        inventory.offHand.set(0, ItemStack.EMPTY);
        ItemStack itemStack = new ItemStack(getSwordInSheathItem());
        itemStack.getOrCreateNbt().putInt("sheath_damage", inventory.main.get(inventory.selectedSlot).getDamage());
        inventory.main.set(inventory.selectedSlot, itemStack);
        if(player.getWorld() instanceof ClientWorld){
            ModClient.processor.stopSubAnim();
        }
    }

    public KeyframeAnimation getSubAnim(){
        return null;
    }

    public Item getSwordInSheathItem() {
        return null;
    }

    public Item getSwordItem() {
        return null;
    }

    public Item getSheathItem() {
        return null;
    }

    public ModSwordItem(Settings settings) {
        super(settings);
    }
}
