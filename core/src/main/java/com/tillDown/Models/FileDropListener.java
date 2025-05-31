package com.tillDown.Models;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowListener;
import com.tillDown.Main;

public class FileDropListener implements Lwjgl3WindowListener {
    @Override
    public void filesDropped(String[] files) {
        System.out.println("FilesDropped event triggered"); // Debug line
        if (files == null) {
            System.out.println("Files array is null!");
            return;
        }

        System.out.println("Number of files dropped: " + files.length);
        for (String file : files) {
            System.out.println("Dropped file: " + file);
        }

        Gdx.app.postRunnable(() -> {
            System.out.println("Inside postRunnable"); // Debug line
            if (Main.getMain() != null) {
                Main.getMain().handleDroppedFiles(files);
            }
        });
    }

    // Add logging to all other methods
    @Override public void created(Lwjgl3Window window) {
        System.out.println("Window created");
    }

    @Override public void iconified(boolean isIconified) {
        System.out.println("Window iconified: " + isIconified);
    }
    @Override public void maximized(boolean isMaximized) {}
    @Override public void focusLost() {}
    @Override public void focusGained() {}
    @Override public boolean closeRequested() { return true; }
    @Override public void refreshRequested() {}
}
