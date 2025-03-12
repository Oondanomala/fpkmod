package me.oondanomala.fpkmod;

import me.oondanomala.fpkmod.commands.CommandKeybindHandler;
import me.oondanomala.fpkmod.commands.FPKMainCommand;
import me.oondanomala.fpkmod.config.Config;
import me.oondanomala.fpkmod.labels.LabelManager;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.movement.PlayerTickHandler;
import me.oondanomala.fpkmod.util.CommandUtil;
import me.oondanomala.fpkmod.util.GuiUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = FPKMod.MODID, name = FPKMod.NAME, version = FPKMod.VERSION, acceptedMinecraftVersions = "1.8", clientSideOnly = true, guiFactory = "me.oondanomala.fpkmod.config.GuiFactory", updateJSON = "https://raw.githubusercontent.com/Oondanomala/fpkmod/master/versions.json")
public class FPKMod {
    public static final String MODID = "fpkmod";
    public static final String NAME = "FPK Mod";
    public static final String VERSION = "0.0.1";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static Config config;
    public static FPKMainCommand fpkCommand;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registerEvents(
                config,
                new GuiUtil(),
                LabelManager.INSTANCE,
                new LBManager(),
                new CommandKeybindHandler(),
                new PlayerTickHandler()
        );
        fpkCommand = new FPKMainCommand();
        CommandUtil.registerCommands(fpkCommand);
    }

    private void registerEvents(Object... events) {
        for (Object event : events) {
            MinecraftForge.EVENT_BUS.register(event);
        }
    }
}
