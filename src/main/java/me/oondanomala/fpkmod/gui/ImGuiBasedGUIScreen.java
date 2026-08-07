package me.oondanomala.fpkmod.gui;

import imgui.ImFont;
import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.ImFontAtlasFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiPopupFlags;
import loutre.imgui.ImGuiLWJGL2;
import me.oondanomala.fpkmod.FPKMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.io.IOUtils;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * A GUI screen that uses ImGui to draw elements.
 */
// TODO: Use an "overlay" instead of a separate GUI screen
public abstract class ImGuiBasedGUIScreen extends GuiScreen {
    public static final float[] PRESET_SCALES = {0.75f, 1.0f, 1.25f, 1.5f, 2.0f, 2.5f};
    private static final ImFont[] baseFonts;
    private int currentFontIndex;

    // TODO: Disable the ImGui functionality when this throws
    static {
        ImGuiLWJGL2.create(false);
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(FPKMod.modDirectory.resolve("imgui.ini").toString());
        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        ImGui.getStyle().setFrameBorderSize(1);
        io.setGetClipboardTextFn(new ImStrSupplier() {
            @Override
            public String get() {
                return getClipboardString();
            }
        });
        io.setSetClipboardTextFn(new ImStrConsumer() {
            @Override
            public void accept(String str) {
                setClipboardString(str);
            }
        });

        // Setup fonts
        // TODO: Rework font rendering once ImGui-Java lets us have backend texture support
        ImFontAtlas fontAtlas = io.getFonts();
        fontAtlas.clear();
        fontAtlas.setFreeTypeRenderer(true); // Higher quality font rendering
        fontAtlas.addFlags(ImFontAtlasFlags.NoMouseCursors);

        ImFontConfig fontConfig = null;
        try {
            fontConfig = new ImFontConfig();
            fontConfig.setGlyphRanges(calcGlyphRanges());
            baseFonts = new ImFont[PRESET_SCALES.length];

            final int baseFontSize = 18;
            final String jbMonoFontPath = "/assets/fpkmod/fonts/JetBrainsMono-Regular.ttf";
            byte[] baseFont = readFontBytes(jbMonoFontPath);
            for (int i = 0; i < PRESET_SCALES.length; i++) {
                int px = Math.round(baseFontSize * PRESET_SCALES[i]);
                fontConfig.setName("JetBrains Mono Regular " + PRESET_SCALES[i]);
                baseFonts[i] = fontAtlas.addFontFromMemoryTTF(baseFont, px, fontConfig);
            }
            fontAtlas.build();
        } finally {
            if (fontConfig != null) {
                fontConfig.destroy();
            }
        }
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        // Automatically choose the font size based on the scale factor
        int factor = mc.gameSettings.guiScale;
        if (factor == 0) { // auto
            factor = new ScaledResolution(mc).getScaleFactor();
        }
        currentFontIndex = Math.min(Math.max(factor - 1, 0), PRESET_SCALES.length - 1);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        ImGui.getIO().clearInputKeys();
    }

    @Override
    public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        ImGuiLWJGL2.draw(this::prepareDraw);
    }

    /**
     * Runs logic before {@link #drawScreen()},
     * but after {@link ImGui#newFrame()}.
     * <p>
     * Only necessary with the legacy font handling.
     */
    private void prepareDraw() {
        ImFont font = baseFonts[currentFontIndex];
        ImGui.pushFont(font, font.getLegacySize());
        drawScreen();
        ImGui.popFont();
    }

    /**
     * Draws the ImGui screen.
     * <p>
     * Render setup and input is automatically handled.
     */
    protected abstract void drawScreen();

    @Override
    public final void handleKeyboardInput() throws IOException {
        boolean imguiHandlesEscape = ImGui.isAnyItemFocused() || ImGui.getIO().getWantTextInput() || ImGui.isPopupOpen("", ImGuiPopupFlags.AnyPopup);
        if (!imguiHandlesEscape) {
            super.handleKeyboardInput();
        }
        // Do not send escape key press events when ImGui does not handle them,
        // as they will close the GUI so ImGui will never get the key release event.
        if (Keyboard.getEventKey() != Keyboard.KEY_ESCAPE || !Keyboard.getEventKeyState() || imguiHandlesEscape) {
            ImGuiLWJGL2.handleKey();
        }
    }

    @Override
    public final void handleMouseInput() throws IOException {
        super.handleMouseInput();
        ImGuiLWJGL2.handleMouse();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Calculates the glyph ranges to
     * load in the font atlas.
     * <p>
     * Only necessary with the legacy font handling.
     *
     * @return The glyph ranges to load
     */
    private static short[] calcGlyphRanges() {
        ImFontGlyphRangesBuilder builder = new ImFontGlyphRangesBuilder();
        builder.addRanges(ImGui.getIO().getFonts().getGlyphRangesDefault());
        builder.addChar('⚠');
        return builder.buildRanges();
    }

    /**
     * Reads the file at the provided resource path
     * into a <tt>byte</tt> array.
     *
     * @param path The resource path to read
     * @return The file contents as a <tt>byte</tt> array
     * @throws NullPointerException If no file is found
     * @throws UncheckedIOException If the file could not be read
     */
    private static byte[] readFontBytes(String path) {
        try (InputStream res = ImGuiBasedGUIScreen.class.getResourceAsStream(path)) {
            Objects.requireNonNull(res, "Could not find font " + path);
            return IOUtils.toByteArray(res);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load font " + path, e);
        }
    }
}
