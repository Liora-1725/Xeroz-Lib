package oyvey.mixin.render;

import oyvey.features.modules.player.Fullbright;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldChunk.class)
public class WorldChunkMixin {
    @Inject(method = "getLightLevel", at = @At("RETURN"), cancellable = true)
    private void onGetLightLevel(LightType type, BlockPos pos, CallbackInfoReturnable<Integer> info) {
        if (Fullbright.INSTANCE == null) return;
        
        int level = Fullbright.INSTANCE.getLuminance();
        if (level != -1 && info.getReturnValue() < level) {
            info.setReturnValue(level);
        }
    }
}
