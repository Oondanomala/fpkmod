package me.oondanomala.fpkmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.ClientCommandHandler;

public final class CommandUtil {
    private CommandUtil() {
    }

    /**
     * Equivalent to {@link Integer#parseInt(String)},
     * but will throw a {@link SyntaxErrorException} with the provided {@code errorMessage} instead of a {@link NumberFormatException}.
     *
     * @param number       The string to parse
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
     * Equivalent to {@link CommandBase#parseBlockPos(net.minecraft.command.ICommandSender, String[], int, boolean) CommandBase.parseBlockPos()},
     * but will throw a {@link SyntaxErrorException} with the provided {@code errorMessage} instead of a {@link NumberInvalidException}.
     *
     * @param args         The array with the coordinates
     * @param startIndex   At what index of {@code args} the coordinates should start
     * @param errorMessage The error message for the {@link SyntaxErrorException} that will be thrown
     * @throws SyntaxErrorException           If the given arguments were not a valid {@link BlockPos}
     * @throws ArrayIndexOutOfBoundsException if {@code startIndex + 3 > args.length}
     */
    public static BlockPos parseBlockPos(String[] args, int startIndex, String errorMessage) throws SyntaxErrorException {
        try {
            return CommandBase.parseBlockPos(Minecraft.getMinecraft().thePlayer, args, startIndex, true);
        } catch (NumberInvalidException e) {
            throw new SyntaxErrorException(errorMessage);
        }
    }

    /**
     * @param args       The array with the coordinates
     * @param startIndex At what index of {@code args} the coordinates should start
     * @throws ArrayIndexOutOfBoundsException if {@code startIndex + 3 > args.length}
     * @see #isValidDouble(String)
     */
    public static boolean isValidBlockPos(String[] args, int startIndex) {
        return isValidDouble(args[startIndex]) && isValidDouble(args[startIndex + 1]) && isValidDouble(args[startIndex + 2]);
    }

    /**
     * Returns <tt>true</tt> if the provided string is a valid coordinate double, and <tt>false</tt> otherwise.
     * That is, a finite double that can optionally start with {@code ~} to represent relative coordinates
     * (note that just "~" is also valid).
     *
     * @param number The string to check
     * @return Whether the provided string is a valid coordinate double.
     */
    public static boolean isValidDouble(String number) {
        if (number.startsWith("~")) {
            number = number.substring(1);
            if (number.isEmpty()) return true;
        }
        try {
            double num = Double.parseDouble(number);
            return Double.isFinite(num);
        } catch (NumberFormatException e) {
            return false;
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
