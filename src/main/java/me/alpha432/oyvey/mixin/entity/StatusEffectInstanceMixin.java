package oyvey.mixin.entity;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatusEffectInstance.class)
public class StatusEffectInstanceMixin {
    // В 1.21 можно просто через Shadow менять поле, если миксин в том же пакете
    @Shadow private int duration;

    // Этот метод можно вызвать из модуля, если сделать интерфейс, 
    // но для Potion режима в Oyvey проще просто перенакладывать эффект (как в коде выше)
}
