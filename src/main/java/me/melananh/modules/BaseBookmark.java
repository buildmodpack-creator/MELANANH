package me.melananh.modules;

import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Module;

/** Simple saved-coordinate bookmark for the user's own base. */
public class BaseBookmark extends Module {
    private final Setting<Integer> x = settings.getDefaultGroup().add(new IntSetting.Builder()
        .name("x").description("Saved X coordinate.").defaultValue(0).build());
    private final Setting<Integer> y = settings.getDefaultGroup().add(new IntSetting.Builder()
        .name("y").description("Saved Y coordinate.").defaultValue(64).build());
    private final Setting<Integer> z = settings.getDefaultGroup().add(new IntSetting.Builder()
        .name("z").description("Saved Z coordinate.").defaultValue(0).build());

    public BaseBookmark() {
        super(me.melananh.MelananhAddon.CATEGORY, "base-bookmark", "Stores a personal base coordinate and displays it when toggled.");
    }

    @Override
    public void onActivate() {
        info("Saved base coordinate: %d, %d, %d", x.get(), y.get(), z.get());
    }

    @Override
    public String getInfoString() {
        return x.get() + ", " + y.get() + ", " + z.get();
    }
}
