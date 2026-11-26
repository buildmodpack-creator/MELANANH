package me.melananh;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class MelananhAddon extends MeteorAddon {
    public static final Category CATEGORY = new Category("MẸ LAN ANH");

    @Override
    public void onInitialize() {
        // Feature modules will be registered here as they are implemented.
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
