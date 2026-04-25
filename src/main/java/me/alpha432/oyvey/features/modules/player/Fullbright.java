package oyvey.features.modules.player;

import oyvey.features.modules.Module;
import oyvey.features.setting.Setting;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.LightType;
import net.minecraft.client.render.WorldRenderer;

public class Fullbright extends Module {
    public static Fullbright INSTANCE;
    
    public Setting<Mode> mode = register(new Setting<>("Mode", Mode.Gamma));
    public Setting<Integer> minLight = register(new Setting<>("MinLight", 15, 0, 15, v -> mode.getValue() == Mode.Luminance));

    public Fullbright() {
        super("Fullbright", "Lights up the world", Category.PLAYER, true, false, false);
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (fullNullCheck() || mode.getValue() != Mode.Potion) return;

        StatusEffectInstance nv = mc.player.getStatusEffect(StatusEffects.NIGHT_VISION);
        if (nv == null) {
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 520, 0, false, false));
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null && mc.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
            mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }

    @Override
    public void onEnable() {
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }

    public int getLuminance() {
        return (isEnabled() && mode.getValue() == Mode.Luminance) ? minLight.getValue() : -1;
    }

    public enum Mode { Gamma, Potion, Luminance }
}
