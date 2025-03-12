package me.oondanomala.fpkmod.labels.lb;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.landingblock.LandingBlock;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelPB extends TextLabel {
    // TODO: Rework to support multiple landing blocks
    //  this is a temporary implementation!
    public LabelPB() {
        super("PB", 2, 65);
    }

    @Override
    protected String getLabelText() {
        LandingBlock landingBlock = LBManager.getSelectedLandingBlock();
        if (landingBlock == null || landingBlock.bestOffset == null) {
            return "N/A";
        }
        return TextUtil.formatDouble(landingBlock.bestOffset.getAxisOffset(landingBlock.landAxis));
    }
}
