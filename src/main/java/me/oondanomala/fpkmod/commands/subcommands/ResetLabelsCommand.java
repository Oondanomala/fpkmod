package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.labels.LabelManager;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;

public class ResetLabelsCommand extends FPKSubCommand {
    public ResetLabelsCommand() {
        super("resetlabels", "Resets all labels to their default positions.");
    }

    @Override
    protected void internalRun(String[] args) {
        for (ConfigCategory category : FPKMod.config.configuration.getCategory("labels").getChildren()) {
            for (Property property : category.values()) {
                property.setToDefault();
            }
        }
        // For some reason settings don't get marked as changed when reset to default
        FPKMod.config.configuration.save();
        LabelManager.INSTANCE.loadLabelsConfig();

        TextUtil.showChatMessage("Labels have been reset.");
    }
}
