package com.example.examplemod.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ModSound {
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
