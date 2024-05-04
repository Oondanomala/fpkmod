package me.oondanomala.spkmod.util;

import net.minecraft.command.CommandBase;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

// Maybe rename
public class ForgeUtil {
    public static void registerEvents(Object... events) {
        for (Object event : events) {
            MinecraftForge.EVENT_BUS.register(event);
        }
    }

    public static void registerCommands(CommandBase... commands) {
        for (CommandBase command : commands) {
            ClientCommandHandler.instance.registerCommand(command);
        }
    }
}
