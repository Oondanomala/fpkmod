package me.oondanomala.spkmod.config;

import me.oondanomala.spkmod.SPKMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class SPKConfig extends GuiConfig {
    public SPKConfig(GuiScreen guiScreen) {
        super(guiScreen,
                new ConfigElement(SPKMod.config.configuration.getCategory(Configuration.CATEGORY_CLIENT)).getChildElements(),
                SPKMod.MODID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(SPKMod.config.configuration.toString()),
                "SPK Config");
    }
}
