package me.oondanomala.fpkmod.gui;

import imgui.ImGui;
import imgui.flag.ImGuiChildFlags;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;
import imgui.type.ImInt;
import me.oondanomala.fpkmod.movement.calc.TierFinder;
import me.oondanomala.fpkmod.movement.calc.TierFinder.BadPositionJumpMode;
import me.oondanomala.fpkmod.movement.calc.TierFinder.LandGap;
import me.oondanomala.fpkmod.movement.sim.YMovementSim;
import me.oondanomala.fpkmod.util.ImGuiUtil;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class TierFinderGUI extends ImGuiBasedGUIScreen {
    public static final TierFinderGUI INSTANCE = new TierFinderGUI();
    private TierFinderGUI() {
    }

    private TierFinder tierFinder = new TierFinder();
    private YMovementSim movementSim = new YMovementSim();
    // Sim options
    private final ImDouble goodStartingPosition = new ImDouble();
    private final ImBoolean jump = new ImBoolean(true);
    private final List<ImDouble> badStartingPositions = new ArrayList<>();
    private final ImInt badJumpMode = new ImInt();

    private static final String[] BAD_JUMP_MODE_ITEMS = {"Both", "No Jump", "Only Jump"};
    private LandGap[] workingGaps = new LandGap[0];

    public void setGoodStartingPos(double pos) {
        goodStartingPosition.set(pos);
    }

    public void addBadStartingPos(double pos) {
        badStartingPositions.add(new ImDouble(pos));
    }

    @Override
    protected void drawScreen() {
        // TODO: Make the window auto resize on Y while allowing resizing on X
        if (ImGui.begin("TierFinder")) {
            // Recalculate the tiers when an option has changed
            boolean hasChanged = false;

            ImGui.separatorText("Good Starting Position");

            ImGui.alignTextToFramePadding();
            ImGui.text("Starting Position");
            ImGuiUtil.tooltip("The position from which the tier jump should work");
            ImGui.sameLine();
            hasChanged |= coordInput("##goodStartingPos", goodStartingPosition, false);

            hasChanged |= ImGui.checkbox("Jump?", jump);
            ImGuiUtil.tooltip("Whether to jump or walk off the good starting position");

            ImGui.separatorText("Bad Starting Positions");

            ImGui.alignTextToFramePadding();
            ImGui.text("Jump Mode");
            ImGuiUtil.tooltip("The jump mode for all bad starting positions");
            ImGui.sameLine();
            ImGui.setNextItemWidth(200);
            hasChanged |= ImGui.combo("##badPositionJumpMode", badJumpMode, BAD_JUMP_MODE_ITEMS);
            ImGuiUtil.tooltip("The jump mode for all bad starting positions");

            ImGui.setNextWindowSizeConstraints(0, ImGui.getTextLineHeightWithSpacing() * 2.5f, Float.MAX_VALUE, Float.MAX_VALUE);
            if (ImGui.beginChild("badStartingPositionListWindow",
                    ImGuiChildFlags.Borders | ImGuiChildFlags.ResizeY | ImGuiChildFlags.NavFlattened, ImGuiWindowFlags.None)) {
                if (ImGui.beginTable("badStartingPosList", 3, ImGuiTableFlags.SizingFixedFit)) {
                    Set<Double> existingPositions = new HashSet<>(badStartingPositions.size());
                    for (ListIterator<ImDouble> it = badStartingPositions.listIterator(); it.hasNext(); ) {
                        ImGui.pushID(it.nextIndex());
                        ImDouble badPos = it.next();
                        ImGui.tableNextRow();

                        if (ImGui.tableNextColumn()) {
                            ImGui.alignTextToFramePadding();
                            ImGui.text("Bad Starting Pos");
                        }
                        if (ImGui.tableNextColumn()) {
                            hasChanged |= coordInput("##badStartingPos", badPos, existingPositions.contains(badPos.get()));
                        }
                        if (ImGui.tableNextColumn() && ImGuiUtil.squareButton("-") && !hasChanged) {
                            it.remove();
                            hasChanged = true;
                        }
                        existingPositions.add(badPos.get());
                        ImGui.popID();
                    }

                    ImGui.endTable();
                }
            }
            ImGui.endChild();

            if (ImGui.button("Add Bad Starting Position")) {
                badStartingPositions.add(new ImDouble(Minecraft.getMinecraft().thePlayer.posY));
                hasChanged = true;
            }
            ImGui.sameLine();
            if (ImGuiUtil.disabled(() -> ImGui.button("Remove All"), "No bad starting positions!", badStartingPositions.isEmpty())) {
                badStartingPositions.clear();
                hasChanged = true;
            }

            ImGui.separatorText("Results");

            if (hasChanged) {
                // TODO:
                //  Slimes
                //  Changing simulation settings
                //  Option to only count tiers going up / going down?
                //  Consider making it run on another thread and add a progress bar
                workingGaps = tierFinder.getWorkingGaps(
                    movementSim,
                    goodStartingPosition.get(), jump.get(),
                    badStartingPositions.stream().mapToDouble(ImDouble::get).toArray(),
                    BadPositionJumpMode.fromOrdinal(badJumpMode.get())
                );
            }

            ImGui.setNextWindowSizeConstraints(0, ImGui.getTextLineHeightWithSpacing() * 2.5f, Float.MAX_VALUE, Float.MAX_VALUE);
            if (ImGui.beginChild("resultListWindow", ImGuiChildFlags.Borders | ImGuiChildFlags.ResizeY | ImGuiChildFlags.NavFlattened, ImGuiWindowFlags.None)) {
                for (LandGap workingGap : workingGaps) {
                    ImGui.text("Found gap: " + workingGap.floorY() + ' ' + workingGap.ceilingY());
                }
            }
            ImGui.endChild();
        }
        ImGui.end();
    }

    private boolean coordInput(String label, ImDouble value, boolean isDuplicate) {
        // Yes, this applies to the old value. There is no way around that (afaik)
        boolean coordWarning = !isHeightBuildable(value.get());
        if (isDuplicate || coordWarning) {
            ImGui.pushStyleColor(ImGuiCol.FrameBg, ImGuiUtil.WARNING_COLOR);
        }
        ImGui.setNextItemWidth(150);
        boolean res = ImGuiUtil.coordinateInput(label, value, ImGuiUtil.PlayerAxis.Y);
        // Clamp to reasonable values
        if (value.get() < 0) value.set(0);
        if (value.get() > 1000) value.set(1000);

        if (isDuplicate || coordWarning) {
            StringBuilder sb = new StringBuilder();
            if (isDuplicate) sb.append("⚠ Duplicate position!\n");
            if (coordWarning) sb.append("⚠ Not a buildable floor height!\n");
            sb.append("\nRight-Click to set to your current Y position");
            ImGui.setItemTooltip(sb.toString());
            ImGui.popStyleColor();
        }
        return res;
    }

    private static boolean isHeightBuildable(double y) {
        // Special case: 256.5 is buildable with 1.5 tall blocks
        return y == 256.5 || (y >= 0 && y <= 256 && Arrays.binarySearch(buildableFloorHeights, y % 1) >= 0);
    }
    private static final double[] buildableFloorHeights = new double[]{.0, .015625, .0625, .125, .1875, .25, 0.3125, .375, .5, .5625, .625, .75, .8125, .875, .9375};
}
