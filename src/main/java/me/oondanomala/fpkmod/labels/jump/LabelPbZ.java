package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.landingblock.LandAxis;
import me.oondanomala.fpkmod.landingblock.LandOffset;
import me.oondanomala.fpkmod.landingblock.LandingBlock;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelPbZ extends TextLabel {
    public LabelPbZ() {
        super("PB Z");
    }

    @Override
    protected String getLabelText() {
        LandingBlock lb = LBManager.getSelectedLandingBlock();
        LandOffset offset = lb != null ? lb.pbOffset : null;
        return TextUtil.formatDouble(offset != null ? offset.getAxisOffset(LandAxis.Z) : 0.0);
    }
}
