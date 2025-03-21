package me.oondanomala.fpkmod.util;

import me.oondanomala.fpkmod.FPKMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class TextUtil {
    private static final DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    private TextUtil() {
    }

    public static void showChatMessage(String message) {
        showChatMessage(message, true);
    }

    public static void showChatMessage(String message, boolean prefix) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(prefix ? assembleText(FPKMod.config.prefix, message) : message));
    }

    public static String assembleText(String text1, String text2) {
        return assembleText(text1, text2, " ");
    }

    public static String assembleText(String text1, String text2, String separator) {
        return FPKMod.config.color1 + text1 + separator + FPKMod.config.color2 + text2;
    }

    public static String formatAsDisabled(String text) {
        return formatAsDisabled(text, EnumChatFormatting.GRAY);
    }

    public static String formatAsDisabled(String text, EnumChatFormatting color) {
        return color.toString() + EnumChatFormatting.STRIKETHROUGH + StringUtils.stripControlCodes(text);
    }

    /**
     * Converts a double into a string formatted according to the current settings.
     *
     * @param number The number to format
     * @return The formatted number
     * @see #formatAngle(float)
     */
    public static String formatDouble(double number) {
        return decimalFormat.format(number);
    }

    /**
     * Converts an angle into a string formatted to the current settings.
     * In particular, it wraps it to 180 degrees, formats it as a double, and appends {@code °} to it.
     *
     * @param angle The angle to format
     * @return The formatted angle
     * @see #formatDouble(double)
     */
    public static String formatAngle(float angle) {
        return formatDouble(MathHelper.wrapAngleTo180_float(angle)) + "°";
    }

    public static void setDecimalPrecision(int digits, boolean trimZeroes) {
        decimalFormat.setMaximumFractionDigits(digits);
        decimalFormat.setMinimumFractionDigits(trimZeroes ? 0 : digits);
    }
}
