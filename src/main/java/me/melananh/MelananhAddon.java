package me.melananh;

import me.melananh.hud.MelananhInfoHud;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class MelananhAddon extends MeteorAddon {
    public static final Category CATEGORY = new Category("MẸ LAN ANH");

    @Override
    public void onInitialize() {
        Hud.get().register(MelananhInfoHud.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "me.melananh";
    }
}
