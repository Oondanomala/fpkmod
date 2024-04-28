package me.oondanomala.spkmod.labels;

public class LabelTest extends Label {
    public LabelTest() {
        super("Test");
    }

    @Override
    protected String getLabelText() {
        return "SPK Mod version 0.0.1-alpha";
    }
}
