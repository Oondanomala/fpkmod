package me.oondanomala.spkmod.commands;

public abstract class SPKSubCommand {
    public final String name;
    public final String helpMessage;

    public SPKSubCommand(String name, String helpMessage) {
        this.name = name;
        this.helpMessage = helpMessage;
    }

    public abstract void run(String[] args);
}
