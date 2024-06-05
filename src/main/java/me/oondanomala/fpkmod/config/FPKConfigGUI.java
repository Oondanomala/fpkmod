package me.oondanomala.fpkmod.config;

import me.oondanomala.fpkmod.FPKMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class FPKConfigGUI extends GuiConfig {
    public FPKConfigGUI(GuiScreen guiScreen) {
        super(guiScreen,
                new ConfigElement(FPKMod.config.configuration.getCategory(Configuration.CATEGORY_CLIENT)).getChildElements(),
                FPKMod.MODID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(FPKMod.config.configuration.toString()),
                "FPK Config");
    }
}
