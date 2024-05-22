package me.oondanomala.spkmod.config;

import me.oondanomala.spkmod.SPKMod;
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

        prefix = configuration.getString("Prefix", Configuration.CATEGORY_CLIENT, "<SPK>", "The prefix that will be used in chat messages.");
        color1 = configuration.getString("Primary Color", Configuration.CATEGORY_CLIENT, "§6Gold", "", colorNames).substring(0, 2);
        color2 = configuration.getString("Secondary Color", Configuration.CATEGORY_CLIENT, "§fWhite", "", colorNames).substring(0, 2);

        enableLabels = configuration.getBoolean("Enable Labels", Configuration.CATEGORY_CLIENT, true, "");
        doublePrecision = configuration.getInt("Coord Precision", Configuration.CATEGORY_CLIENT, 6, 0, 16, "The number of decimals shown in floating point numbers.");
        trimZeroes = configuration.getBoolean("Trim Zeroes", Configuration.CATEGORY_CLIENT, false, "");

        mpkCommand = configuration.get(Configuration.CATEGORY_CLIENT, "MPK Command", true, "Whether to create the \"mpk\" alias to the \"spk\" command.").setRequiresMcRestart(true).getBoolean();

        configuration.save();
    }

    public void setConfigOption(String optionName, int value) {
        SPKMod.config.configuration.getCategory(Configuration.CATEGORY_CLIENT).get(optionName).set(value);
        saveConfig();
    }

    public void setConfigOption(String optionName, boolean value) {
        SPKMod.config.configuration.getCategory(Configuration.CATEGORY_CLIENT).get(optionName).set(value);
        saveConfig();
    }

    public void saveConfig() {
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(SPKMod.MODID)) {
            loadConfig();
        }
    }
}
