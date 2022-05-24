package com.cpmason.nebuhc.game;

import com.cpmason.nebuhc.util.JsonHelper;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.scenarios.ScenarioManager;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.json.JSONObject;

import java.util.*;

import static com.cpmason.nebuhc.NeBUhc.HANDLE;
import static org.bukkit.Bukkit.getServer;

public class NeBManager {
    private static final NeBManager NEB_MANAGER = new NeBManager();

    private static final String SCENARIO_DATA_PATH = "scenario-data.json";
    private static final String START_LOADING_MSG = "Loading NeBUhc...";
    private static final String DONE_LOADING_MSG = "Done :)";


    private NeBManager() {
        // Singleton
    }

    public static NeBManager getManager() {
        return NEB_MANAGER;
    }

    public void loadNeB() {
        ConsoleCommandSender sender = getServer().getConsoleSender();

        sender.sendMessage(HANDLE + START_LOADING_MSG);

        registerScenarios();

        sender.sendMessage(HANDLE + DONE_LOADING_MSG);
    }

     private void registerScenarios() {

        JSONObject scenariosJson = JsonHelper.read(SCENARIO_DATA_PATH);
        ScenarioManager scenarioManager = GameManager.getGameManager().getScenarioManager();

        for (Iterator<String> it = scenariosJson.keys(); it.hasNext();) {
            String key = it.next();
            JSONObject thisJson = scenariosJson.getJSONObject(key);
            scenarioManager.registerScenario(JsonHelper.parseScenario(key, thisJson));

        }

    }


}
