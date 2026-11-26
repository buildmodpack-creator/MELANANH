package me.melananh.hud;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/** Lightweight configurable player information HUD. */
public class MelananhInfoHud extends HudElement {
    public static final HudElementInfo<MelananhInfoHud> INFO =
        new HudElementInfo<>(Hud.GROUP, "melananh-info", "Displays selected player information.", MelananhInfoHud::new);

    private final Setting<Boolean> showCoordinates = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-coordinates").description("Display the player's block coordinates.").defaultValue(true).build());
    private final Setting<Boolean> showFps = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-fps").description("Display client frames per second.").defaultValue(true).build());
    private final Setting<Boolean> showDimension = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-dimension").description("Display the current dimension.").defaultValue(true).build());
    private final Setting<Boolean> showFacing = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-facing").description("Display the direction the player is facing.").defaultValue(false).build());
    private final Setting<Boolean> showRotation = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-rotation").description("Display yaw and pitch angles.").defaultValue(false).build());
    private final Setting<Boolean> showHealth = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-health").description("Display current and maximum health.").defaultValue(true).build());
    private final Setting<Boolean> showFood = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-food").description("Display hunger level.").defaultValue(true).build());
    private final Setting<Boolean> showArmorDurability = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-armor-durability").description("Display remaining durability of worn armor.").defaultValue(false).build());
    private final Setting<Boolean> showHeldItem = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-held-item").description("Display the name and count of the held item.").defaultValue(false).build());
    private final Setting<Boolean> showHeldDurability = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-held-durability").description("Display remaining durability of a damageable held item.").defaultValue(false).build());
    private final Setting<Boolean> showAbsorption = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-absorption").description("Display absorption hearts.").defaultValue(false).build());
    private final Setting<Boolean> showGroundState = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-ground-state").description("Display whether the player is on the ground.").defaultValue(false).build());
    private final Setting<Boolean> showVelocity = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-velocity").description("Display local movement velocity.").defaultValue(false).build());
    private final Setting<Boolean> showWorldTime = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("show-world-time").description("Display Minecraft world time.").defaultValue(false).build());
    private final Setting<Boolean> shadow = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("shadow").description("Draw text with a shadow.").defaultValue(true).build());

    private final SettingColor textColor = new SettingColor(230, 230, 230);

    public MelananhInfoHud() {
        super(INFO);
        setSize(150, 60);
    }

    private String armorDurabilityText() {
        if (mc.player == null) return "ARMOR: --";
        int total = 0, remaining = 0, pieces = 0;
        for (ItemStack stack : mc.player.getInventory().armor) {
            if (stack == null || stack.isEmpty() || !stack.isDamageable()) continue;
            total += stack.getMaxDamage();
            remaining += Math.max(0, stack.getMaxDamage() - stack.getDamage());
            pieces++;
        }
        if (pieces == 0 || total == 0) return "ARMOR: none";
        return String.format(java.util.Locale.ROOT, "ARMOR: %d%% durability (%d/4)", (remaining * 100) / total, pieces);
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
        if (showDimension.get()) lines.add(inGame ? "DIM: " + mc.world.getRegistryKey().getValue() : (isInEditor() ? "DIM: minecraft:overworld" : "DIM: --"));
        if (showFacing.get()) lines.add(inGame ? "FACING: " + mc.player.getHorizontalFacing().getName().toUpperCase(java.util.Locale.ROOT) : "FACING: --");
        if (showRotation.get()) lines.add(inGame ? String.format(java.util.Locale.ROOT, "ROT: yaw %.1f / pitch %.1f", mc.player.getYaw(), mc.player.getPitch()) : "ROT: -- / --");
        if (showHealth.get()) lines.add(inGame ? String.format(java.util.Locale.ROOT, "HP: %.1f / %.1f", mc.player.getHealth(), mc.player.getMaxHealth()) : "HP: --");
        if (showFood.get()) lines.add(inGame ? "FOOD: " + mc.player.getHungerManager().getFoodLevel() + " / 20" : "FOOD: --");
        if (showHeldItem.get()) {
            if (inGame) {
                ItemStack held = mc.player.getMainHandStack();
                lines.add("ITEM: " + (held.isEmpty() ? "empty" : held.getName().getString() + " x" + held.getCount()));
            } else lines.add("ITEM: --");
        }
        if (showHeldDurability.get()) {
            if (inGame) {
                ItemStack held = mc.player.getMainHandStack();
                lines.add("DURABILITY: " + (held.isEmpty() || !held.isDamageable() ? "--" : (held.getMaxDamage() - held.getDamage()) + "/" + held.getMaxDamage()));
            } else lines.add("DURABILITY: --");
        }
        if (showAbsorption.get()) lines.add(inGame ? String.format(java.util.Locale.ROOT, "ABSORB: %.1f", mc.player.getAbsorptionAmount()) : "ABSORB: --");
        if (showGroundState.get()) lines.add(inGame ? "GROUND: " + (mc.player.isOnGround() ? "yes" : "no") : "GROUND: --");
        if (showVelocity.get()) {
            if (inGame) {
                net.minecraft.util.math.Vec3d velocity = mc.player.getVelocity();
                lines.add(String.format(java.util.Locale.ROOT, "VEL: %.3f / %.3f / %.3f", velocity.x, velocity.y, velocity.z));
            } else lines.add("VEL: -- / -- / --");
        }
        if (showArmorDurability.get()) lines.add(inGame ? armorDurabilityText() : "ARMOR: --");
        if (showWorldTime.get()) {
            if (inGame) {
                long time = mc.world.getTimeOfDay() % 24000L;
                long hours = (time / 1000L + 6L) % 24L;
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
