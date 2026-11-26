package me.melananh.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import static meteordevelopment.meteorclient.MeteorClient.mc;

/** Read-only client diagnostics; reports local client state only. */
public class ClientDiagnostics extends Module {
    public ClientDiagnostics() {
        super(me.melananh.MelananhAddon.CATEGORY, "client-diagnostics", "Shows basic client performance and local world information.");
    }

    @Override
    public String getInfoString() {
        if (mc.player == null || mc.world == null) return "No world";
        long usedMb = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024L * 1024L);
        return String.format("FPS %d | %s | XYZ %.0f %.0f %.0f | RAM %d MB",
            mc.getCurrentFps(),
            mc.world.getRegistryKey().getValue(),
            mc.player.getX(), mc.player.getY(), mc.player.getZ(),
            usedMb);
    }

    @Override
    public void onActivate() {
        if (mc.player == null || mc.world == null) {
            info("No world loaded.");
            return;
        }
        info("FPS: %d | Dimension: %s | RAM: %d MB",
            mc.getCurrentFps(),
            mc.world.getRegistryKey().getValue(),
            (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024L * 1024L));
    }
}
