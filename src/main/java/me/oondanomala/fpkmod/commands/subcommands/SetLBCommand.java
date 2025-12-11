package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.landingblock.LandAxis;
import me.oondanomala.fpkmod.landingblock.LandMode;
import me.oondanomala.fpkmod.landingblock.LandingBlock;
import me.oondanomala.fpkmod.util.CommandUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetLBCommand extends FPKSubCommand {
    public SetLBCommand() {
        super("setlb", "Sets the landing block.", "Set a new landing block");
    }
    // TODO: Replace LB instead of adding a new one when setting it on the same block

    @Override
    protected void internalRun(String[] args) throws CommandException {
        TargetType targetType = TargetType.TARGET;
        LandMode landMode = LandMode.LAND;
        LandAxis axis = LandAxis.BOTH;
        boolean box = false;

        int argIndex = 0;
        if (args.length > 0) {
            if (args[argIndex].equals("target") || args[argIndex].equals("t")) {
                // targetType is already TARGET, no need to do anything.
                argIndex++;
            } else if (args[argIndex].equals("below") || args[argIndex].equals("b")) {
                targetType = TargetType.BELOW;
                argIndex++;
            } else if (args.length >= 3 && CommandUtil.isValidBlockPos(args, argIndex)) {
                targetType = TargetType.COORDS;
                argIndex += 3;
            }
        }

        boolean setLandMode = false;
        boolean setBox = false;
        boolean setAxis = false;
        for (String arg : Arrays.copyOfRange(args, argIndex, args.length)) {
            if (arg.equals("land") && !setLandMode) {
                // landMode is already LAND, no need to do anything
                setLandMode = true;
            } else if (arg.equals("zneo") && !setLandMode) {
                landMode = LandMode.ZNEO;
                setLandMode = true;
            } else if (arg.equals("enter") && !setLandMode) {
                landMode = LandMode.ENTER;
                setLandMode = true;
            } else if (arg.equals("hit") && !setLandMode) {
                landMode = LandMode.HIT;
                setLandMode = true;
            } else if (arg.equals("box") && !setBox) {
                box = true;
                setBox = true;
            } else if (arg.equals("x") && !setAxis) {
                axis = LandAxis.X;
                setAxis = true;
            } else if (arg.equals("z") && !setAxis) {
                axis = LandAxis.Z;
                setAxis = true;
            } else if (arg.equals("~") && !setAxis) {
                float absYaw = Math.abs(MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationYaw));
                axis = (absYaw > 45 && absYaw < 135) ? LandAxis.X : LandAxis.Z;
                setAxis = true;
            } else {
                throw new SyntaxErrorException();
            }
        }

        BlockPos pos = getLandingBlockPos(targetType, args);

        // TODO: Add an option to try to guess landing modes? e.g. setlb box on ladder, assume enter; setlb box on slime, assume hit.
        // Force LandMode to be ENTER and box mode for vines, since nothing else makes sense for them
        World world = Minecraft.getMinecraft().theWorld;
        Block block = world.getBlockState(pos).getBlock();
        if (block.getCollisionBoundingBox(world, pos, world.getBlockState(pos)) == null && block.isLadder(world, pos, null)) {
            landMode = LandMode.ENTER;
            box = true;
        }

        LBManager.addLandingBlock(new LandingBlock(pos, landMode, axis, box));
        TextUtil.showChatMessage("Set landing block.");
    }

    private BlockPos getLandingBlockPos(TargetType targetType, String[] coordArgs) throws CommandException {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        BlockPos pos;

        switch (targetType) {
            case TARGET:
                MovingObjectPosition position = player.rayTrace(20, 1);
                if (position.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK || !canLandOnBlock(position.getBlockPos())) {
                    throw new CommandException("You must be looking at a solid block.");
                }
                return position.getBlockPos();
            case BELOW:
                if (player.onGround) {
                    pos = new BlockPos(player.posX, Math.nextDown(player.posY), player.posZ);
                    if (canLandOnBlock(pos)) {
                        return pos;
                    }
                    pos = new BlockPos(player.posX, player.posY - 0.55, player.posZ);
                    if (canLandOnBlock(pos)) {
                        return pos;
                    }
                }
                throw new CommandException("You must be standing on a solid block.");
            case COORDS:
                pos = CommandUtil.parseBlockPos(coordArgs, 0);
                if (!canLandOnBlock(pos)) throw new CommandException("You must specify a solid block.");
                return pos;
        }
        throw new IllegalStateException();
    }

    private boolean canLandOnBlock(BlockPos pos) {
        World world = Minecraft.getMinecraft().theWorld;
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block.getCollisionBoundingBox(world, pos, blockState) == null) {
            // Special case for vines (or modded blocks that behave like vines)
            return block.isLadder(world, pos, null);
        }
        return true;
    }

    @Override
    public List<String> getTabCompletions(String[] args) {
        if (args.length == 0) {
            return Arrays.asList("target", "below", "land", "zneo", "enter", "hit", "box", "x", "z", "~");
        }

        List<String> targets = Arrays.asList("target", "t", "below", "b");
        List<String> landModes = Arrays.asList("land", "zneo", "enter", "hit");
        List<String> axis = Arrays.asList("x", "z", "~");

        if (targets.contains(args[0])) {
            args = Arrays.copyOfRange(args, 1, args.length);
        } else if (args.length >= 3 && CommandUtil.isValidBlockPos(args, 0)) {
            args = Arrays.copyOfRange(args, 3, args.length);
        }

        boolean hasLandModes = false;
        boolean hasBox = false;
        boolean hasAxis = false;
        for (String arg : args) {
            if (landModes.contains(arg) && !hasLandModes) {
                hasLandModes = true;
            } else if (arg.equals("box") && !hasBox) {
                hasBox = true;
            } else if (axis.contains(arg) && !hasAxis) {
                hasAxis = true;
            } else {
                return Collections.emptyList();
            }
        }

        List<String> completions = new ArrayList<>(Arrays.asList("land", "zneo", "enter", "hit", "box", "x", "z", "~"));
        if (hasLandModes) {
            completions.removeAll(landModes);
        }
        if (hasBox) {
            completions.remove("box");
        }
        if (hasAxis) {
            completions.removeAll(axis);
        }

        return completions;
    }

    @Override
    protected String getUsage() {
        return "[target|below|x y z] [land|zneo|enter|hit] [box] [x|z|~]";
    }

    @Override
    public boolean canSignExecute() {
        return true;
    }

    private enum TargetType {
        TARGET,
        BELOW,
        COORDS
    }
}
