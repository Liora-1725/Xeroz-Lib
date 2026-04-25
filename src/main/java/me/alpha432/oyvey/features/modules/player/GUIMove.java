package me.client.modules.player;

import me.client.events.KeyEvent; // Замените на вашу систему ивентов
import me.client.modules.Module;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class GUIMove extends Module {
    public GUIMove() {
        super("GUIMove", "Allows you to move while in screens", Category.PLAYER);
    }

    // В 1.21.5 используем стандартный массив клавиш для проверки
    private static final KeyBinding[] BINDINGS = new KeyBinding[] {
        mc.options.forwardKey, mc.options.backKey,
        mc.options.leftKey, mc.options.rightKey,
        mc.options.jumpKey, mc.options.sneakKey, mc.options.sprintKey
    };

    public void onUpdate() {
        if (mc.currentScreen == null || isWritingScreen()) {
            return;
        }

        // Обработка передвижения
        for (KeyBinding bind : BINDINGS) {
            bind.setPressed(GLFW.glfwGetKey(mc.getWindow().getHandle(), 
                ((IKeyBinding) bind).getBoundKey().getCode()) == GLFW.GLFW_PRESS);
        }

        // Обработка вращения стрелочками
        float rotateSpeed = 4.0f;
        if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS) 
            mc.player.setYaw(mc.player.getYaw() - rotateSpeed);
        if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) 
            mc.player.setYaw(mc.player.getYaw() + rotateSpeed);
        if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS) 
            mc.player.setPitch(MathHelper.clamp(mc.player.getPitch() - rotateSpeed, -90, 90));
        if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS) 
            mc.player.setPitch(MathHelper.clamp(mc.player.getPitch() + rotateSpeed, -90, 90));
    }

    private boolean isWritingScreen() {
        return mc.currentScreen instanceof ChatScreen 
            || mc.currentScreen instanceof SignEditScreen 
            || mc.currentScreen instanceof AnvilScreen 
            || mc.currentScreen instanceof AbstractCommandBlockScreen;
    }

    @Override
    public void onDisable() {
        for (KeyBinding bind : BINDINGS) {
            if (!GLFW.glfwGetKey(mc.getWindow().getHandle(), ((IKeyBinding) bind).getBoundKey().getCode()) == GLFW.GLFW_PRESS) {
                bind.setPressed(false);
            }
        }
    }
}
