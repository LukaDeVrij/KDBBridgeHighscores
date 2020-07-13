package me.lifelessnerd.kdbbridgehighscores;

import org.bukkit.plugin.java.JavaPlugin;

public final class KDBBridgeHighScores extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ClickSignEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
