package me.oondanomala.spkmod;

import me.oondanomala.spkmod.commands.SPKMainCommand;
import me.oondanomala.spkmod.config.Config;
import me.oondanomala.spkmod.labels.LabelManager;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.util.ForgeUtil;
import me.oondanomala.spkmod.util.GuiUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SPKMod.MODID, name = SPKMod.NAME, version = SPKMod.VERSION, acceptedMinecraftVersions = "1.8", clientSideOnly = true, guiFactory = "me.oondanomala.spkmod.config.GuiFactory")
public class SPKMod {
    public static final String MODID = "spkmod";
    public static final String NAME = "SPK Mod";
    public static final String VERSION = "0.0.1";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static Config config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ForgeUtil.registerEvents(
                config,
                new GuiUtil(),
                LabelManager.instance,
                new PlayerMovementHandler()
        );
        ForgeUtil.registerCommands(SPKMainCommand.instance);
    }
}
