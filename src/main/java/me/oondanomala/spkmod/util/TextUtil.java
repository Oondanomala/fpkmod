package me.oondanomala.spkmod.util;

import me.oondanomala.spkmod.SPKMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TextUtil {
    private static final DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    public static void showChatMessage(String message) {
        showChatMessage(message, true);
    }

    public static void showChatMessage(String message, boolean prefix) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(prefix ? assembleText(SPKMod.config.prefix, message) : message));
    }

    public static String assembleText(String text1, String text2) {
        return assembleText(text1, text2, " ");
    }

    public static String assembleText(String text1, String text2, String separator) {
        return SPKMod.config.color1 + text1 + separator + SPKMod.config.color2 + text2;
    }

    public static String formatDouble(double number) {
        decimalFormat.setMaximumFractionDigits(SPKMod.config.doublePrecision);
        if (!SPKMod.config.trimZeroes) {
            decimalFormat.setMinimumFractionDigits(SPKMod.config.doublePrecision);
        }
        return decimalFormat.format(number);
    }
}
