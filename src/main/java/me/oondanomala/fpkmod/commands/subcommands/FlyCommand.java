package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;

public class FlyCommand extends FPKSubCommand {
    public FlyCommand() {
        super("fly", "Disables flight when in creative mode.");
    }

    @Override
    protected void internalRun(String[] args) throws CommandException {
        // TODO: Allow this in survival if the player can fly?
        if (!Minecraft.getMinecraft().playerController.isInCreativeMode()) {
            throw new CommandException("You can only use this command in creative mode.");
        }
        boolean canFly = !Minecraft.getMinecraft().thePlayer.capabilities.allowFlying;
        Minecraft.getMinecraft().thePlayer.capabilities.allowFlying = canFly;

        if (!canFly) {
            Minecraft.getMinecraft().thePlayer.capabilities.isFlying = false;
            TextUtil.showChatMessage("Flight is now disabled.");
        } else {
            TextUtil.showChatMessage("Flight is now enabled.");
        }
    }
}
