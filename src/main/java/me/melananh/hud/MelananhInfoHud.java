package me.melananh.hud;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.math.BlockPos;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/** Lightweight configurable player information HUD. */
public class MelananhInfoHud extends HudElement {
    public static final HudElementInfo<MelananhInfoHud> INFO =
        new HudElementInfo<>(Hud.GROUP, "melananh-info", "Displays selected player information.", MelananhInfoHud::new);

    private final Setting<Boolean> showCoordinates = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-coordinates")
        .description("Display the player's block coordinates.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> showFps = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-fps")
        .description("Display client frames per second.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> showDimension = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-dimension")
        .description("Display the current dimension.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> showHealth = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-health")
        .description("Display current and maximum health.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> showFood = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-food")
        .description("Display hunger level.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> showWorldTime = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-world-time")
        .description("Display Minecraft world time.")
        .defaultValue(false)
        .build());

    private final Setting<Boolean> shadow = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("shadow")
        .description("Draw text with a shadow.")
        .defaultValue(true)
        .build());

    private final SettingColor textColor = new SettingColor(230, 230, 230);

    public MelananhInfoHud() {
        super(INFO);
        setSize(150, 60);
    }

    @Override
    public void render(HudRenderer renderer) {
        double scale = Hud.get().getTextScale();
        boolean drawShadow = shadow.get();
        java.util.ArrayList<String> lines = new java.util.ArrayList<>();

        boolean inGame = mc.player != null && mc.world != null && !isInEditor();
        if (showCoordinates.get()) {
            if (inGame) {
                BlockPos pos = mc.player.getBlockPos();
                lines.add("XYZ: " + pos.getX() + " / " + pos.getY() + " / " + pos.getZ());
            } else lines.add(isInEditor() ? "XYZ: 123 / 64 / -456" : "XYZ: -- / -- / --");
        }
        if (showFps.get()) lines.add("FPS: " + mc.getCurrentFps());
        if (showDimension.get()) {
            lines.add(inGame ? "DIM: " + mc.world.getRegistryKey().getValue() : (isInEditor() ? "DIM: minecraft:overworld" : "DIM: --"));
        }
        if (showHealth.get()) {
            lines.add(inGame ? String.format(java.util.Locale.ROOT, "HP: %.1f / %.1f", mc.player.getHealth(), mc.player.getMaxHealth()) : "HP: --");
        }
        if (showFood.get()) {
            lines.add(inGame ? "FOOD: " + mc.player.getHungerManager().getFoodLevel() + " / 20" : "FOOD: --");
        }
        if (showWorldTime.get()) {
            if (inGame) {
                long time = mc.world.getTimeOfDay() % 24000L;
                long hours = ((time / 1000L + 6L) % 24L);
                long minutes = (time % 1000L) * 60L / 1000L;
                lines.add(String.format(java.util.Locale.ROOT, "TIME: %02d:%02d", hours, minutes));
            } else lines.add("TIME: --:--");
        }

        double lineHeight = renderer.textHeight(drawShadow, scale) + 2;
        double width = 0;
        for (String line : lines) width = Math.max(width, renderer.textWidth(line, drawShadow, scale));
        setSize(Math.max(4, width + 4), Math.max(4, lineHeight * lines.size() + 2));

        for (int i = 0; i < lines.size(); i++) {
            renderer.text(lines.get(i), x + 2, y + 2 + i * lineHeight, textColor, drawShadow, scale);
        }
    }
}
