package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.gui.TierFinderGUI;
import me.oondanomala.fpkmod.util.CommandUtil;
import me.oondanomala.fpkmod.util.GuiUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TierFinderCommand extends FPKSubCommand {
    private final TierFinderGUI tierFinderGUI;

    public TierFinderCommand() {
        super("tier", "Opens and works with the tier finder.", "Open the tier finder menu");

        TierFinderGUI gui;
        try {
            gui = TierFinderGUI.INSTANCE;
        } catch (Throwable e) {
            FPKMod.LOGGER.error("ImGui failed to initialize!", e);
            gui = null;
        }
        tierFinderGUI = gui;
    }

    @Override
    protected void internalRun(String[] args) throws CommandException {
        if (tierFinderGUI == null) {
            throw new CommandException("ImGui failed to initialize, so this command is not available.");
        }
        if (args.length == 0) {
            GuiUtil.displayGui(tierFinderGUI);
        } else {
            double posY;
            if (args.length > 1) {
                posY = CommandUtil.parseDouble(args[1]);
            } else {
                posY = Minecraft.getMinecraft().thePlayer.posY;
            }

            switch (args[0]) {
                case "setgood", "good", "g" -> {
                    tierFinderGUI.setGoodStartingPos(posY);
                    TextUtil.showChatMessage("Set good starting position to " + TextUtil.formatDouble(posY) + '.');
                }
                case "addbad", "bad", "b" -> {
                    tierFinderGUI.addBadStartingPos(posY);
                    TextUtil.showChatMessage("Added bad starting position " + TextUtil.formatDouble(posY) + '.');
                }
                default -> throw new SyntaxErrorException();
            }
        }
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        if (args.length == 0) {
            return Arrays.asList("setgood", "addbad");
        }
        return Collections.emptyList();
    }

    @Override
    protected String getUsage() {
        return "[setgood|addbad [position]]";
    }
}
