package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.util.CommandUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TeleportCommand extends FPKSubCommand {
    public TeleportCommand() {
        super("tp", "Improved version of /tp. Allows teleporting to saved coords if no arguments are given.", "Run /fpk tp");
    }

    @Override
    protected void internalRun(String[] args) throws CommandException {
        // Ends up calling the vanilla /tp command, to make it work on servers.
        // This however means it is at the mercy of servers that don't implement the command right.
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        if (args.length == 0) {
            if (CoordsCommand.savedCoords == null) {
                throw new CommandException("No saved coordinates.");
            }
            String coords = CoordsCommand.savedCoords.xCoord + " " +
                    CoordsCommand.savedCoords.yCoord + " " +
                    CoordsCommand.savedCoords.zCoord + " " +
                    CoordsCommand.savedYaw + " " +
                    CoordsCommand.savedPitch;

            player.sendChatMessage("/tp " + player.getName() + " " + coords);
            return;
        }

        if (args.length < 3 || args.length > 5 || !Arrays.stream(args).allMatch(CommandUtil::isValidDouble)) {
            throw new SyntaxErrorException();
        }

        // The vanilla /tp implementation turns 1 into 1.5, but some servers differ.
        // Turn 1 into 1.0 to prevent that, as wanting the exact number is more likely to be wanted
        // and will be consistent across servers.
        args = Arrays.stream(args).map(a -> {
            if (!a.contains(".") && !a.startsWith("~")) {
                return Double.toString(Double.parseDouble(a));
            }
            return a;
        }).toArray(String[]::new);

        String command = "/tp " + player.getName() + " " + args[0] + " " + args[1] + " " + args[2];
        if (args.length == 4) {
            // Use ~ to allow for optional pitch when specifying yaw.
            command += " " + args[3] + " ~";
        } else if (args.length == 5) {
            command += " " + args[3] + " " + args[4];
        }

        player.sendChatMessage(command);
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        if (!Arrays.stream(args).allMatch(CommandUtil::isValidDouble)) {
            return Collections.emptyList();
        }

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        switch (args.length) {
            case 0:
                return Collections.singletonList(TextUtil.formatDoubleExact(player.posX));
            case 1:
                return Collections.singletonList(TextUtil.formatDoubleExact(player.posY));
            case 2:
                return Collections.singletonList(TextUtil.formatDoubleExact(player.posZ));
            case 3:
                return Collections.singletonList(TextUtil.formatAngleExact(player.rotationYaw));
            case 4:
                return Collections.singletonList(TextUtil.formatAngleExact(player.rotationPitch));
        }

        return Collections.emptyList();
    }

    @Override
    protected String getUsage() {
        return "[<x> <y> <z> [yaw] [pitch]]";
    }

    @Override
    public boolean canSignExecute() {
        return true;
    }
}
