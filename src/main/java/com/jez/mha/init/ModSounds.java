package com.jez.mha.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ModSounds {
    public enum DODGE {
        START(SoundEvents.BLOCK_BELL_USE);
        public final SoundEvent soundEvent;

        DODGE(SoundEvent soundEvent) {
            this.soundEvent = soundEvent;
        }
    }

    public static void register() {

    }
}
