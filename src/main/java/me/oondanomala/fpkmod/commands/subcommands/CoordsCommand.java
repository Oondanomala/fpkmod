package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CoordsCommand extends FPKSubCommand {
    static Vec3 savedCoords;
    static float savedYaw;
    static float savedPitch;

    public CoordsCommand() {
        super("coords", "Manages the player coordinates.");
    }

    @Override
    protected void internalRun(String[] args) throws CommandException {
        if (args.length != 1) {
            throw new SyntaxErrorException();
        }

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        switch (args[0]) {
            case "save":
            case "s":
                savedCoords = player.getPositionVector();
                savedYaw = MathHelper.wrapAngleTo180_float(player.rotationYaw);
                savedPitch = player.rotationPitch;
                TextUtil.showChatMessage("Saved coordinates.");
                return;
            case "copy":
            case "c":
                String coords = TextUtil.formatDoubleExact(player.posX) + " " +
                        TextUtil.formatDoubleExact(player.posY) + " " +
                        TextUtil.formatDoubleExact(player.posZ) + " " +
                        TextUtil.formatAngleExact(player.rotationYaw) + " " +
                        TextUtil.formatAngleExact(player.rotationPitch);

                GuiScreen.setClipboardString(coords);
                TextUtil.showChatMessage("Copied coordinates to the clipboard.");
                return;
            case "clear":
                savedCoords = null;
                savedYaw = Float.NaN;
                savedPitch = Float.NaN;
                TextUtil.showChatMessage("Cleared coordinates.");
                return;
        }
        throw new SyntaxErrorException();
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        if (args.length == 0) {
            return Arrays.asList("save", "copy", "clear");
        }
        return Collections.emptyList();
    }

    @Override
    protected String getUsage() {
        return "<save|copy|clear>";
    }
}
