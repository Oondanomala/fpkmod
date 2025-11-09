package me.oondanomala.fpkmod.config;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.util.EnumChatFormatting;
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
    public boolean enableAntiCP;
    public int antiCPTime;

    public boolean sprintToggled;
    public boolean renderLandingBox;
    public boolean renderCondBox;

    public Config(File configFile) {
        configuration = new Configuration(configFile);
        loadConfig();
    }

    public void loadConfig() {
        prefix = getStringConfig(
                "Prefix",
                "The prefix that will be used in chat messages.",
                "<FPK>"
        );
        color1 = getColorConfig(
                "Primary Color",
                "The main color used in text.",
                EnumChatFormatting.GOLD
        );
        color2 = getColorConfig(
                "Secondary Color",
                "The secondary color used in text.",
                EnumChatFormatting.WHITE
        );
        enableLabels = getBooleanConfig(
                "Enable Labels",
                "Whether to show labels at all.",
                true
        );
        doublePrecision = getIntSliderConfig(
                "Coord Precision",
                "The number of decimals shown in floating point numbers.",
                6,
                0,
                16
        );
        trimZeroes = getBooleanConfig(
                "Trim Zeroes",
                "Whether to remove trailing zeroes from floating point numbers.",
                false
        );
        mpkCommand = configuration.get(
                Configuration.CATEGORY_CLIENT,
                "MPK Command",
                true,
                "Whether to create the \"mpk\" alias to the \"fpk\" command."
        ).setRequiresMcRestart(true).setRequiresWorldRestart(false).getBoolean();
        sendOffsetInChat = getBooleanConfig(
                "Send Offsets In Chat",
                "Whether to send the land offsets in chat when landing on a landing block.",
                false
        );
        ignoreZeroOffsets = getBooleanConfig(
                "Ignore -0 Offsets",
                "Whether to not count -0 offsets as PBs.",
                false
        );
        enableAntiCP = getBooleanConfig(
                "AntiCP",
                "Whether to block right-clicks after landing for a specified amount of time.",
                false
        );
        antiCPTime = (int) (getDoubleConfig(
                "AntiCP Time",
                "How long to block right-clicks for, in seconds.",
                2,
                0,
                20
        ) * 1000);

        sprintToggled = configuration.get(Configuration.CATEGORY_CLIENT, "togglesprint", false).setShowInGui(false).getBoolean();
        renderLandingBox = configuration.get(Configuration.CATEGORY_CLIENT, "renderLB", true).setShowInGui(false).getBoolean();
        renderCondBox = configuration.get(Configuration.CATEGORY_CLIENT, "renderCond", false).setShowInGui(false).getBoolean();

        TextUtil.setDecimalPrecision(doublePrecision, trimZeroes);

        configuration.save();
    }

    /**
     * Creates a {@link String} config option,
     * and returns its value.
     *
     * @param name         The name of the option, this is also used as an identifier
     * @param description  A description of what the option does
     * @param defaultValue The value to use if the option did not previously exist
     * @return The config value if it already exists, otherwise {@code defaultValue}
     */
    private String getStringConfig(String name, String description, String defaultValue) {
        return configuration.get(Configuration.CATEGORY_CLIENT, name, defaultValue, description).getString();
    }

    /**
     * Creates a <tt>boolean</tt> config option,
     * and returns its value.
     *
     * @param name         The name of the option, this is also used as an identifier
     * @param description  A description of what the option does
     * @param defaultValue The value to use if the option did not previously exist
     * @return The config value if it already exists, otherwise {@code defaultValue}
     */
    private boolean getBooleanConfig(String name, String description, boolean defaultValue) {
        return configuration.get(Configuration.CATEGORY_CLIENT, name, defaultValue, description).getBoolean();
    }

    /**
     * Creates an <tt>int</tt> config option with a minimum and a maximum allowed value,
     * and returns its value.
     * It will be shown in the configuration GUI as a slider.
     *
     * @param name         The name of the option, this is also used as an identifier
     * @param description  A description of what the option does
     * @param defaultValue The value to use if the option did not previously exist
     * @param minValue     The minimum value the option can be set to
     * @param maxValue     The maximum value the option can be set to
     * @return The config value if it already exists, otherwise {@code defaultValue}
     */
    private int getIntSliderConfig(String name, String description, int defaultValue, int minValue, int maxValue) {
        return configuration.get(Configuration.CATEGORY_CLIENT, name, defaultValue, description, minValue, maxValue)
                .setConfigEntryClass(GuiConfigEntries.NumberSliderEntry.class).getInt();
    }

    /**
     * Creates a <tt>double</tt> config option with a minimum and a maximum allowed value,
     * and returns its value.
     *
     * @param name         The name of the option, this is also used as an identifier
     * @param description  A description of what the option does
     * @param defaultValue The value to use if the option did not previously exist
     * @param minValue     The minimum value the option can be set to
     * @param maxValue     The maximum value the option can be set to
     * @return The config value if it already exists, otherwise {@code defaultValue}
     */
    private double getDoubleConfig(String name, String description, double defaultValue, double minValue, double maxValue) {
        return configuration.get(Configuration.CATEGORY_CLIENT, name, defaultValue, description, minValue, maxValue).getDouble();
    }

    /**
     * Creates a color picker config option,
     * allowing the user to choose between all color formatting codes.
     * The color is returned as a 2 character string composed of the
     * control character ({@code §}) and the formatting character,
     * so that it can be easily used in text.
     *
     * @param name         The name of the option, this is also used as an identifier
     * @param description  A description of what the option does
     * @param defaultValue The value to use if the option did not previously exist
     * @return A 2 character string representing the config value if it already exists,
     *         otherwise the 2 character string representing {@code defaultValue}
     * @see EnumChatFormatting
     */
    private String getColorConfig(String name, String description, EnumChatFormatting defaultValue) {
        if (!defaultValue.isColor() || defaultValue.ordinal() > COLOR_NAMES.length - 1) {
            throw new IllegalArgumentException("The default value must be a valid color");
        }
        return configuration.get(Configuration.CATEGORY_CLIENT, name, COLOR_NAMES[defaultValue.ordinal()], description, COLOR_NAMES).getString().substring(0, 2);
    }

    private static final String[] COLOR_NAMES = {"§0Black", "§1Dark Blue", "§2Dark Green", "§3Dark Aqua", "§4Dark Red", "§5Dark Purple", "§6Gold", "§7Gray", "§8Dark Gray", "§9Blue", "§aGreen", "§bAqua", "§cRed", "§dLight Purple", "§eYellow", "§fWhite"};

    /**
     * Sets the provided config option to {@code value}.
     * Does not save the configs.
     *
     * @param optionName The name of the config option
     * @param value      The value to change it to
     * @see #saveConfig()
     */
    public void setConfigOption(String optionName, int value) {
        configuration.getCategory(Configuration.CATEGORY_CLIENT).get(optionName).set(value);
    }

    /**
     * Sets the provided config option to {@code value}.
     * Does not save the configs.
     *
     * @param optionName The name of the config option
     * @param value      The value to change it to
     * @see #saveConfig()
     */
    public void setConfigOption(String optionName, double value) {
        configuration.getCategory(Configuration.CATEGORY_CLIENT).get(optionName).set(value);
    }

    /**
     * Sets the provided config option to {@code value}.
     * Does not save the configs.
     *
     * @param optionName The name of the config option
     * @param value      The value to change it to
     * @see #saveConfig()
     */
    public void setConfigOption(String optionName, boolean value) {
        configuration.getCategory(Configuration.CATEGORY_CLIENT).get(optionName).set(value);
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
