package meteordevelopment.meteorclient.systems.modules.player; // Изменил на Player по просьбе

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixininterface.IStatusEffectInstance;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.LightType;

public class Fullbright extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Метод освещения.")
        .defaultValue(Mode.Gamma)
        .onChanged(v -> {
            if (isActive()) {
                if (v != Mode.Potion) disableNightVision();
                if (mc.worldRenderer != null) mc.worldRenderer.reload();
            }
        })
        .build()
    );

    private final Setting<LightType> lightType = sgGeneral.add(new EnumSetting.Builder<LightType>()
        .name("light-type")
        .description("Тип света для Luminance.")
        .defaultValue(LightType.BLOCK)
        .visible(() -> mode.get() == Mode.Luminance)
        .onChanged(v -> { if (isActive()) mc.worldRenderer.reload(); })
        .build()
    );

    private final Setting<Integer> minLight = sgGeneral.add(new IntSetting.Builder()
        .name("min-light-level")
        .defaultValue(15)
        .range(0, 15)
        .sliderMax(15)
        .visible(() -> mode.get() == Mode.Luminance)
        .onChanged(v -> { if (isActive()) mc.worldRenderer.reload(); })
        .build()
    );

    public Fullbright() {
        super(Categories.Player, "fullbright", "Максимальная яркость везде.");
    }

    @Override
    public void onActivate() {
        if (mode.get() == Mode.Luminance) mc.worldRenderer.reload();
    }

    @Override
    public void onDeactivate() {
        if (mode.get() == Mode.Luminance) mc.worldRenderer.reload();
        if (mode.get() == Mode.Potion) disableNightVision();
    }

    // Метод для миксина
    public int getLuminance(LightType type) {
        if (!isActive() || mode.get() != Mode.Luminance || type != lightType.get()) return -1;
        return minLight.get();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mode.get() != Mode.Potion) return;

        StatusEffectInstance nv = mc.player.getStatusEffect(StatusEffects.NIGHT_VISION);
        if (nv != null) {
            // В 1.21.x используем каст к интерфейсу-аксессору
            if (nv.getDuration() < 420) ((IStatusEffectInstance) nv).meteor$setDuration(420);
        } else {
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 420, 0, false, false));
        }
    }

    private void disableNightVision() {
        if (mc.player != null && mc.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
            mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }

    public enum Mode { Gamma, Potion, Luminance }
}
