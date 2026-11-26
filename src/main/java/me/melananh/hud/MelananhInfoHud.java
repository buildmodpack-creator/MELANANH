package me.melananh.hud;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
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

    private final SettingGroup general = settings.getDefaultGroup();

    private final Setting<Boolean> showCoordinates = general.add(new BoolSetting.Builder()
        .name("show-coordinates")
        .description("Display the player's block coordinates.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> showFps = general.add(new BoolSetting.Builder()
        .name("show-fps")
        .description("Display client frames per second.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> showDimension = general.add(new BoolSetting.Builder()
        .name("show-dimension")
        .description("Display the current dimension.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> shadow = general.add(new BoolSetting.Builder()
        .name("shadow")
        .description("Draw text with a shadow.")
        .defaultValue(true)
        .build());

    private final SettingColor textColor = new SettingColor(230, 230, 230);

    public MelananhInfoHud() {
        super(INFO);
        setSize(150, 42);
    }

    @Override
    public void render(HudRenderer renderer) {
        double scale = Hud.get().getTextScale();
        boolean drawShadow = shadow.get();
        String coords = "XYZ: -- / -- / --";
        String dimension = "DIM: --";

        if (mc.player != null && mc.world != null && !isInEditor()) {
            BlockPos pos = mc.player.getBlockPos();
            coords = "XYZ: " + pos.getX() + " / " + pos.getY() + " / " + pos.getZ();
            dimension = "DIM: " + mc.world.getRegistryKey().getValue().toString();
        } else if (isInEditor()) {
            coords = "XYZ: 123 / 64 / -456";
            dimension = "DIM: minecraft:overworld";
        }

        java.util.ArrayList<String> lines = new java.util.ArrayList<>();
        if (showCoordinates.get()) lines.add(coords);
        if (showFps.get()) lines.add("FPS: " + mc.getCurrentFps());
        if (showDimension.get()) lines.add(dimension);

        double lineHeight = renderer.textHeight(drawShadow, scale) + 2;
        double width = 0;
        for (String line : lines) width = Math.max(width, renderer.textWidth(line, drawShadow, scale));
        setSize(Math.max(4, width + 4), Math.max(4, lineHeight * lines.size() + 2));

        for (int i = 0; i < lines.size(); i++) {
            renderer.text(lines.get(i), x + 2, y + 2 + i * lineHeight, textColor, drawShadow, scale);
        }
    }
}
