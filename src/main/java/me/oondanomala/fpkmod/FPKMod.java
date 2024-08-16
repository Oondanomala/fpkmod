package me.oondanomala.fpkmod;

import me.oondanomala.fpkmod.commands.CommandKeybindHandler;
import me.oondanomala.fpkmod.commands.FPKMainCommand;
import me.oondanomala.fpkmod.config.Config;
import me.oondanomala.fpkmod.labels.LabelManager;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.GuiUtil;
import net.minecraft.command.CommandBase;
import net.minecraftforge.client.ClientCommandHandler;
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

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registerEvents(
                config,
                new GuiUtil(),
                LabelManager.instance,
                new CommandKeybindHandler(),
                new PlayerMovementHandler()
        );
        registerCommands(FPKMainCommand.instance);
    }

    private void registerEvents(Object... events) {
        for (Object event : events) {
            MinecraftForge.EVENT_BUS.register(event);
        }
    }

    private void registerCommands(CommandBase... commands) {
        for (CommandBase command : commands) {
            ClientCommandHandler.instance.registerCommand(command);
        }
    }
}
