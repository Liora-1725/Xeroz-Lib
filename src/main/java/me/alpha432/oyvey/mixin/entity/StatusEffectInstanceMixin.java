package meteordevelopment.meteorclient.mixin.entity;

import meteordevelopment.meteorclient.mixininterface.IStatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements IStatusEffectInstance {
    @Shadow private int duration;

    @Override
    public void meteor$setDuration(int duration) {
        this.duration = duration;
    }
}
