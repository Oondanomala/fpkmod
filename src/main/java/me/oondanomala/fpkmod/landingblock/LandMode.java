package me.oondanomala.fpkmod.landingblock;

public enum LandMode {
    LAND("Land"),
    ZNEO("Z Neo"),
    ENTER("Enter"),
    HIT("Hit");

    private final String name;

    LandMode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
