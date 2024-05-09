package me.oondanomala.spkmod.labels.misc;

import me.oondanomala.spkmod.labels.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LabelDate extends Label {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uu");

    public LabelDate() {
        super("Date");
    }

    @Override
    protected String getLabelText() {
        return LocalDate.now().format(dateFormatter);
    }
}
