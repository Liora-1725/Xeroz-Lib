package meteordevelopment.meteorclient.mixin.render;

import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.player.Fullbright;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
    private static void onGetBrightness(CallbackInfoReturnable<Float> info) {
        Fullbright fullbright = Modules.get().get(Fullbright.class);
        
        // Режим Gamma: заставляем игру думать, что яркость всегда на максимуме
        if (fullbright != null && fullbright.isActive() && fullbright.getGamma()) {
            info.setReturnValue(1000f); 
        }
    }
}
