package me.oondanomala.fpkmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;

/**
 * A gui button that will cycle through the provided enum values when clicked.
 * <p>
 * The button text will be displayed as {@code prefix + " " + enumValue.toString()}.
 *
 * @param <E> The enum type
 */
public class EnumButton<E extends Enum<E>> extends GuiButtonExt {
    private final E[] enumValues;
    private final String prefix;
    private int currentValue;

    public EnumButton(int id, int xPos, int yPos, int width, int height, String prefix, E initialValue) {
        super(id, xPos, yPos, width, height, prefix);
        this.enumValues = initialValue.getDeclaringClass().getEnumConstants();
        this.prefix = prefix;
        this.currentValue = initialValue.ordinal();
        updateText();
    }

    public E getCurrentValue() {
        return enumValues[currentValue];
    }

    public void setCurrentValue(E value) {
        currentValue = value.ordinal();
        updateText();
    }

    private void updateText() {
        displayString = prefix + " " + enumValues[currentValue].toString();
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            currentValue = (currentValue + enumValues.length + (GuiScreen.isShiftKeyDown() ? -1 : 1)) % enumValues.length;
            updateText();
            return true;
        }
        return false;
    }
}
