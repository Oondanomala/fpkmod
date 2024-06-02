package me.oondanomala.spkmod.commands.subcommands;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.commands.SPKSubCommand;
import me.oondanomala.spkmod.labels.LabelManager;
import me.oondanomala.spkmod.util.TextUtil;

public class ReloadConfigCommand extends SPKSubCommand {
    public ReloadConfigCommand() {
        super("reloadconfig", "Reloads the config file.");
    }

    @Override
    protected void internalRun(String[] args) {
        SPKMod.config.configuration.load();
        SPKMod.config.loadConfig();
        LabelManager.instance.loadLabelsConfig();

        TextUtil.showChatMessage("Reloaded config file.");
    }
}
