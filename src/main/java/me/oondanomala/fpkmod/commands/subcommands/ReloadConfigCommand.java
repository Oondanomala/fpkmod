package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.labels.LabelManager;
import me.oondanomala.fpkmod.util.TextUtil;

public class ReloadConfigCommand extends FPKSubCommand {
    public ReloadConfigCommand() {
        super("reloadconfig", "Reloads the config file.");
    }

    @Override
    protected void internalRun(String[] args) {
        FPKMod.config.configuration.load();
        FPKMod.config.loadConfig();
        LabelManager.INSTANCE.loadLabelsConfig();

        TextUtil.showChatMessage("Reloaded config file.");
    }
}
