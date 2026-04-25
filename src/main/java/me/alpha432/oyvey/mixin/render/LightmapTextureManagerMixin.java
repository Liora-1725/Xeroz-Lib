package oyvey.mixin.render;

import oyvey.features.modules.player.Fullbright;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
    private static void onGetBrightness(CallbackInfoReturnable<Float> info) {
        if (Fullbright.INSTANCE != null && Fullbright.INSTANCE.isEnabled() && Fullbright.INSTANCE.mode.getValue() == Fullbright.Mode.Gamma) {
            info.setReturnValue(1000f);
        }
    }
}
