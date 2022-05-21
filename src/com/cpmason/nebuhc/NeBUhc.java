package com.cpmason.nebuhc;

import com.cpmason.nebuhc.scenarios.CustomScenarios;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.scenarios.ScenarioManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class NeBUhc extends JavaPlugin {

    private static final String HANDLE = "[neBUhc] " + ChatColor.AQUA;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(HANDLE + "Initializing Wife Jokes...");
        GameManager uhc = GameManager.getGameManager();
        ScenarioManager scenMan = uhc.getScenarioManager();
        scenMan.registerScenario(CustomScenarios.GLOWING);
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(HANDLE + "Smelling You Later...");
    }
}
