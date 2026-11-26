package me.melananh.modules;

import meteordevelopment.meteorclient.systems.modules.Module;

/** Tracks time since this module was enabled; local-only session utility. */
public class SessionTimer extends Module {
    private long startedAt;
    private long accumulatedMillis;

    public SessionTimer() {
        super(me.melananh.MelananhAddon.CATEGORY, "session-timer", "Tracks elapsed time for the current session while enabled.");
    }

    @Override
    public void onActivate() {
        startedAt = System.currentTimeMillis();
        info("Session timer started.");
    }

    @Override
    public void onDeactivate() {
        if (startedAt != 0) {
            accumulatedMillis += System.currentTimeMillis() - startedAt;
            startedAt = 0;
        }
    }

    @Override
    public String getInfoString() {
        long elapsed = accumulatedMillis;
        if (startedAt != 0) elapsed += System.currentTimeMillis() - startedAt;
        long seconds = elapsed / 1000;
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds / 60) % 60, seconds % 60);
    }
}
