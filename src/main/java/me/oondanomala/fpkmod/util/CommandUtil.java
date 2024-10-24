package me.oondanomala.fpkmod.util;

import net.minecraft.command.ICommand;
import net.minecraft.command.SyntaxErrorException;
import net.minecraftforge.client.ClientCommandHandler;

public final class CommandUtil {
    private CommandUtil() {
    }

    /**
     * Equivalent to {@link Integer#parseInt(String)},
     * but will throw a {@link SyntaxErrorException} with the provided {@code errorMessage} instead of a {@link NumberFormatException}.
     *
     * @param number       The text to parse
     * @param errorMessage The error message for the {@link SyntaxErrorException} that will be thrown
     * @throws SyntaxErrorException If the given argument is not a valid integer.
     */
    public static int parseInt(String number, String errorMessage) throws SyntaxErrorException {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new SyntaxErrorException(errorMessage);
        }
    }

    /**
     * Registers the provided commands as client commands in {@link ClientCommandHandler}.
     *
     * @param commands The commands to register
     */
    public static void registerCommands(ICommand... commands) {
        for (ICommand command : commands) {
            ClientCommandHandler.instance.registerCommand(command);
        }
    }
}
