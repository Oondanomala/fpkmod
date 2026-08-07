package me.oondanomala.fpkmod.util;

import imgui.ImGui;
import imgui.ImVec4;
import imgui.flag.ImGuiHoveredFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiMouseCursor;
import imgui.type.ImDouble;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.function.BooleanSupplier;

public final class ImGuiUtil {
    private ImGuiUtil() {
    }

    /**
     * The color used for warnings.
     * It's yellow ({@code #B0920C}).
     */
    public static final ImVec4 WARNING_COLOR = new ImVec4(176 / 255f, 146 / 255f, 12 / 255f, 1);

    /**
     * Creates a small square button.
     *
     * @param label The label of the button
     * @return Whether the button has been pressed
     */
    public static boolean squareButton(String label) {
        float size = ImGui.getFrameHeight();
        return ImGui.button(label, size, size);
    }

    /**
     * Creates a number input for the provided axis.
     * <p>
     * The input will be set to the player's current position
     * in the provided axis when right-clicked.
     *
     * @param label The input label
     * @param value The input value
     * @param axis  The coordinate axis
     * @return <tt>true</tt> if the value has changed, <tt>false</tt> otherwise
     */
    public static boolean coordinateInput(String label, ImDouble value, PlayerAxis axis) {
        boolean res = ImGui.inputDouble(label, value, 0, 0, ImGuiInputTextFlags.CharsDecimal);

        ImGui.setItemTooltip("Right-Click to set to your current " + axis.toString() + " position");
        if (ImGui.isItemHovered() && !isTextInputActive() && ImGui.isMouseReleased(ImGuiMouseButton.Right)) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            value.set(switch (axis) {
                case X -> player.posX;
                case Y -> player.posY;
                case Z -> player.posZ;
            });
            return true;
        }
        return res;
    }

    /**
     * Adds a tooltip to the previous item.
     *
     * @param text The tooltip text
     */
    public static void tooltip(String text) {
        if (ImGui.isItemHovered(ImGuiHoveredFlags.DelayNormal)) {
            ImGui.setTooltip(text);
        }
    }

    /**
     * Makes the provided {@code element}
     * disabled if {@code disabledCondition} is <tt>true</tt>.
     *
     * @param element           The element to disable
     * @param disabledTooltip   The tooltip to show when the element is disabled
     * @param disabledCondition Whether the element should be disabled or not
     * @return The return value of {@code element}, or <tt>false</tt> if {@code disabledCondition} is <tt>true</tt>
     */
    public static boolean disabled(BooleanSupplier element, String disabledTooltip, boolean disabledCondition) {
        if (disabledCondition) {
            ImGui.beginDisabled();
            element.getAsBoolean();
            if (ImGui.isItemHovered(ImGuiHoveredFlags.AllowWhenDisabled)) {
                ImGui.setMouseCursor(ImGuiMouseCursor.NotAllowed);
                ImGui.setTooltip(disabledTooltip);
            }
            ImGui.endDisabled();
            return false;
        } else {
            return element.getAsBoolean();
        }
    }

    /**
     * Whether the last text input is active
     * (the user is writing text).
     */
    public static boolean isTextInputActive() {
        return ImGui.isItemFocused() && ImGui.getIO().getWantTextInput();
    }

    public enum PlayerAxis {
        X, Y, Z
    }
}
