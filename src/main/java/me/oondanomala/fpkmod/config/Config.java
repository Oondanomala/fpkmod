package me.oondanomala.fpkmod.config;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraftforge.common.config.Configuration;
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

    public Config(File configFile) {
        configuration = new Configuration(configFile);
        loadConfig();
    }

    public void loadConfig() {
        // Hacky, but it works!
        String[] colorNames = {"§0Black", "§1Dark Blue", "§2Dark Green", "§3Dark Aqua", "§4Dark Red", "§5Dark Purple", "§6Gold", "§7Gray", "§8Dark Gray", "§9Blue", "§aGreen", "§bAqua", "§cRed", "§dLight Purple", "§eYellow", "§fWhite"};

        prefix = configuration.getString("Prefix", Configuration.CATEGORY_CLIENT, "<FPK>", "The prefix that will be used in chat messages.");
        color1 = configuration.getString("Primary Color", Configuration.CATEGORY_CLIENT, "§6Gold", "The main color used in text.", colorNames).substring(0, 2);
        color2 = configuration.getString("Secondary Color", Configuration.CATEGORY_CLIENT, "§fWhite", "The secondary color used in text.", colorNames).substring(0, 2);

        enableLabels = configuration.getBoolean("Enable Labels", Configuration.CATEGORY_CLIENT, true, "Whether to show labels at all.");
        doublePrecision = configuration.getInt("Coord Precision", Configuration.CATEGORY_CLIENT, 6, 0, 16, "The number of decimals shown in floating point numbers.");
        trimZeroes = configuration.getBoolean("Trim Zeroes", Configuration.CATEGORY_CLIENT, false, "Whether to remove any trailing zeroes from labels.");

        mpkCommand = configuration.get(Configuration.CATEGORY_CLIENT, "MPK Command", true, "Whether to create the \"mpk\" alias to the \"fpk\" command.").setRequiresMcRestart(true).getBoolean();

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
