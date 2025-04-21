package me.oondanomala.fpkmod.config;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class Config {
    public final Configuration configuration;

    public String prefix;
    public String color1;
    public String color2;
    public boolean enableLabels;
    public int doublePrecision;
    public boolean trimZeroes;
    public boolean mpkCommand;
    public boolean sendOffsetInChat;
    public boolean ignoreZeroOffsets;

    public boolean renderLandingBox;
    public boolean renderCondBox;

    public Config(File configFile) {
        configuration = new Configuration(configFile);
        loadConfig();
    }

    public void loadConfig() {
        // Hacky, but it works!
        final String[] colorNames = {"§0Black", "§1Dark Blue", "§2Dark Green", "§3Dark Aqua", "§4Dark Red", "§5Dark Purple", "§6Gold", "§7Gray", "§8Dark Gray", "§9Blue", "§aGreen", "§bAqua", "§cRed", "§dLight Purple", "§eYellow", "§fWhite"};

        prefix = configuration.getString(
                "Prefix",
                Configuration.CATEGORY_CLIENT,
                "<FPK>",
                "The prefix that will be used in chat messages."
        );
        color1 = configuration.getString(
                "Primary Color",
                Configuration.CATEGORY_CLIENT,
                "§6Gold",
                "The main color used in text.",
                colorNames
        ).substring(0, 2);
        color2 = configuration.getString(
                "Secondary Color",
                Configuration.CATEGORY_CLIENT,
                "§fWhite",
                "The secondary color used in text.",
                colorNames
        ).substring(0, 2);
        enableLabels = configuration.getBoolean(
                "Enable Labels",
                Configuration.CATEGORY_CLIENT,
                true,
                "Whether to show labels at all."
        );
        doublePrecision = configuration.get(
                Configuration.CATEGORY_CLIENT,
                "Coord Precision",
                6,
                "The number of decimals shown in floating point numbers.",
                0,
                16
        ).setConfigEntryClass(GuiConfigEntries.NumberSliderEntry.class).getInt();
        trimZeroes = configuration.getBoolean(
                "Trim Zeroes",
                Configuration.CATEGORY_CLIENT,
                false,
                "Whether to remove trailing zeroes from floating point numbers."
        );
        mpkCommand = configuration.get(
                Configuration.CATEGORY_CLIENT,
                "MPK Command",
                true,
                "Whether to create the \"mpk\" alias to the \"fpk\" command."
        ).setRequiresMcRestart(true).getBoolean();
        sendOffsetInChat = configuration.getBoolean(
                "Send Offsets In Chat",
                Configuration.CATEGORY_CLIENT,
                false,
                "Whether to send the land offsets in chat when landing on a landing block."
        );
        ignoreZeroOffsets = configuration.getBoolean(
                "Ignore -0 Offsets",
                Configuration.CATEGORY_CLIENT,
                false,
                "Whether to not count -0 offsets as PBs."
        );

        renderLandingBox = configuration.get(Configuration.CATEGORY_CLIENT, "renderLB", true).setShowInGui(false).getBoolean();
        renderCondBox = configuration.get(Configuration.CATEGORY_CLIENT, "renderCond", false).setShowInGui(false).getBoolean();

        TextUtil.setDecimalPrecision(doublePrecision, trimZeroes);

        configuration.save();
    }

    public void setConfigOption(String optionName, int value) {
        configuration.getCategory(Configuration.CATEGORY_CLIENT).get(optionName).set(value);
        saveConfig();
    }

    public void setConfigOption(String optionName, boolean value) {
        configuration.getCategory(Configuration.CATEGORY_CLIENT).get(optionName).set(value);
        saveConfig();
    }

    public void saveConfig() {
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(FPKMod.MODID)) {
            loadConfig();
        }
    }
}
