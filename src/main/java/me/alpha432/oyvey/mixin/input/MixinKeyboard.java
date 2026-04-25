package me.alpha432.oyvey.mixin.input;

import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.alpha432.oyvey.Oyvey; // Путь к главному классу
import me.alpha432.oyvey.modules.player.GUIMove; // Путь к модулю

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKey(long window, int key, int scancode, int action, int mods, CallbackInfo ci) {
        // Если открыт чат — миксин не должен работать, чтобы можно было писать
        if (net.minecraft.client.MinecraftClient.getInstance().currentScreen instanceof ChatScreen) return;

        // Если модуль включен, мы можем отменить стандартную обработку кликов, 
        // если она мешает GUI, но обычно достаточно просто аксессора в модуле.
    }
}
