package me.oondanomala.fpkmod.labels.misc;

import me.oondanomala.fpkmod.labels.TextLabel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LabelDate extends TextLabel {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uu");

    public LabelDate() {
        super("Date");
    }

    @Override
    protected String getLabelText() {
        return LocalDate.now().format(dateFormatter);
    }
}
