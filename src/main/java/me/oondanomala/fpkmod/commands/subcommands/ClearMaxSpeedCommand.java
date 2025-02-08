package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.movement.ParkourHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class ClearMaxSpeedCommand extends FPKSubCommand {
    public ClearMaxSpeedCommand() {
        super("clearmaxspeed", "Resets the max speed labels back to 0.");
    }

    @Override
    protected void internalRun(String[] args) {
        ParkourHandler.maxSpeedX = 0;
        ParkourHandler.maxSpeedY = 0;
        ParkourHandler.maxSpeedZ = 0;
        TextUtil.showChatMessage("Cleared max speed.");
    }
}
