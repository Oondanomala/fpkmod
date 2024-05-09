package me.oondanomala.spkmod.labels.misc;

import me.oondanomala.spkmod.labels.Label;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LabelTime extends Label {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public LabelTime() {
        super("Time");
    }

    @Override
    protected String getLabelText() {
        return LocalTime.now().format(timeFormatter);
    }
}
