package me.oondanomala.spkmod.commands.subcommands;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.commands.SPKSubCommand;
import me.oondanomala.spkmod.labels.LabelManager;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;

public class ResetLabelsCommand extends SPKSubCommand {
    public ResetLabelsCommand() {
        super("resetlabels", "Resets all labels to their default positions.");
    }

    @Override
    protected void internalRun(String[] args) {
        for (ConfigCategory category : SPKMod.config.configuration.getCategory("labels").getChildren()) {
            for (Property property : category.values()) {
                property.setToDefault();
            }
        }
        // For some reason settings don't get marked as changed when reset to default
        SPKMod.config.configuration.save();
        LabelManager.instance.loadLabelsConfig();

        TextUtil.showChatMessage("Labels have been reset.");
    }
}
