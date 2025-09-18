package me.oondanomala.fpkmod.commands;

import me.oondanomala.fpkmod.util.KeyBindUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.Collections;
import java.util.List;

/**
 * Implement this class to add a new subcommand to {@code /fpk}.
 * Don't forget to register it in {@link FPKMainCommand}'s constructor!
 */
public abstract class FPKSubCommand {
    public final String name;
    public final String helpMessage;
    public final KeyBinding keybind;

    /**
     * Constructs a new subcommand with the provided name and help message, and no keybind.
     *
     * @param name        The name of the subcommand, e.g. {@code /fpk command}
     * @param helpMessage The text that will be displayed in the {@code /fpk help} subcommand
     */
    protected FPKSubCommand(String name, String helpMessage) {
        this(name, helpMessage, null);
    }

    /**
     * Constructs a new subcommand with the provided name, help message, and keybind.
     * The keybind has no default key, and will call {@link #run(String[])} with no arguments when pressed.
     *
     * @param name        The name of the subcommand, e.g. {@code /fpk command}
     * @param helpMessage The text that will be displayed in the {@code /fpk help} subcommand
     * @param keybindName The name of the keybind, or <tt>null</tt> for no keybind
     */
    protected FPKSubCommand(String name, String helpMessage, String keybindName) {
        this.name = name;
        this.helpMessage = helpMessage;

        if (keybindName == null) {
            keybind = null;
        } else {
            keybind = new KeyBinding(keybindName, Keyboard.KEY_NONE, KeyBindUtil.KEYBIND_CATEGORY);
        }
    }

    public final void run(String[] args) {
        try {
            internalRun(args);
        } catch (SyntaxErrorException e) {
            TextUtil.showChatMessage(EnumChatFormatting.RED + "/fpk " + name + " " + getUsage());
        } catch (CommandException e) {
            TextUtil.showChatMessage(e.getMessage());
        }
    }

    /**
     * Gets a list of all the subcommand options available at the provided {@code args}.
     * Called when the user presses tab while typing the subcommand arguments.
     *
     * @param args The subcommand arguments the user has already provided
     * @implSpec The default implementation always returns an empty list.
     */
    public List<String> getTabCompletions(String[] args) {
        return Collections.emptyList();
    }

    /**
     * Gets the subcommand usage. This is shown to the user when the subcommand throws a {@link SyntaxErrorException}.
     *
     * @return The subcommand usage
     * @implSpec The default implementation always throws an {@link UnsupportedOperationException},
     *           and <strong>must</strong> be overridden if the subcommand throws a {@link SyntaxErrorException}!
     */
    protected String getUsage() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns <tt>true</tt> if this command can be executed by right-clicking a sign
     * with the command and command arguments on it, <tt>false</tt> otherwise.
     *
     * @implSpec The default implementation always returns <tt>false</tt>.
     */
    public boolean canSignExecute() {
        return false;
    }

    /**
     * Called when the subcommand is run.
     *
     * @param args The subcommand arguments
     * @throws SyntaxErrorException When a syntax error occurs in the command. The {@link #getUsage() command usage} will be shown in chat, formatted appropriately
     * @throws CommandException     When a generic error occurs in the command. The exception message will be shown in chat, without additional formatting
     */
    protected abstract void internalRun(String[] args) throws CommandException;
}
