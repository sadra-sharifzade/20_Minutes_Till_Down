package com.tillDown.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.*;
import com.tillDown.Main;
import com.tillDown.Models.FileDropListener;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static void createApplication() {
        Main game = new Main();
        Lwjgl3ApplicationConfiguration config = getDefaultConfiguration();
        config.setWindowListener(new FileDropListener());
        Lwjgl3Application app = new Lwjgl3Application(game, getDefaultConfiguration());


    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("20minsTillDawn");
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        configuration.setMaximized(true);
        configuration.setWindowedMode(640, 480);
        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        //// They can also be loaded from the root of assets/ .
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        Lwjgl3WindowListener listener = new Lwjgl3WindowListener() {
            @Override
            public void created(Lwjgl3Window window) {
            }

            @Override
            public void filesDropped(String[] files) {
                Main.getMain().handleDroppedFiles(files);
            }

            // Other required methods
            @Override public void iconified(boolean isIconified) {}
            @Override public void maximized(boolean isMaximized) {}
            @Override public void focusLost() {}
            @Override public void focusGained() {}
            @Override public boolean closeRequested() { return true; }
            @Override public void refreshRequested() {}
        };

        // Set listener BEFORE creating application
        configuration.setWindowListener(listener);
        return configuration;
    }
}
