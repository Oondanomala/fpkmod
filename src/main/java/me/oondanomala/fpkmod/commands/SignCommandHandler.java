package me.oondanomala.fpkmod.commands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.StringJoiner;

public class SignCommandHandler {
    private long lastClickTime;

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK ||
            System.currentTimeMillis() - lastClickTime <= 250 ||
            event.entityPlayer.isSneaking()
        ) {
            return;
        }

        TileEntity tileEntity = event.world.getTileEntity(event.pos);
        if (tileEntity instanceof TileEntitySign) {
            lastClickTime = System.currentTimeMillis();

            StringJoiner joiner = new StringJoiner(" ");
            for (IChatComponent chatComponent : ((TileEntitySign) tileEntity).signText) {
                if (chatComponent != null) {
                    joiner.add(chatComponent.getUnformattedText().toLowerCase(Locale.ENGLISH));
                }
            }
            String signText = joiner.toString().trim().replaceAll("\\s+", " ");
            String[] args = signText.split(" ");

            if (args.length >= 2 && args[0].startsWith("/")) {
                FPKMainCommand fpkCommand = FPKMod.fpkCommand;
                Set<String> fpkCommandNames = new HashSet<>(fpkCommand.getCommandAliases());
                fpkCommandNames.add(fpkCommand.getCommandName());

                if (fpkCommandNames.contains(args[0].substring(1))) {
                    FPKSubCommand command = fpkCommand.subCommands.get(args[1]);
                    if (command != null && command.canSignExecute()) {
                        TextUtil.showChatMessage("Executing \"" + signText + "\"...");
                        command.run(Arrays.copyOfRange(args, 2, args.length));
                    }
                }
            }
        }
    }
}
