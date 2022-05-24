package com.cpmason.nebuhc.util;

import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.Material;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

public class JsonHelper {
    private static final String NAME_KEY = "name";
    private static final String MATERIAL_KEY = "material";
    private static final String LISTENER_KEY = "listener";
    private static final String LISTENER_CLS_FULL_QUALIFICATION = "com.cpmason.nebuhc.scenarios.scenariolisteners";
    private static final String DESCRIPTION_KEY = "description";

    public static JSONObject read(String path) {
        StringBuilder jsonString = new StringBuilder();

        try (InputStream is = JsonHelper.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new NullPointerException("Input stream for " + path + " is null.");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Stream<String> stream = reader.lines();
            stream.forEach(s -> jsonString.append(s).append("\n"));
        } catch (IOException | SecurityException | NullPointerException ex) {
            ex.printStackTrace();
        }

        return new JSONObject(jsonString.toString());
    }

    @SuppressWarnings("unchecked")
    public static Scenario parseScenario(String key, JSONObject scenarioJson) {
        String name, listenerStr, materialStr;
        JSONArray description;

        // Gets the raw data of each entry
        try {
            name = scenarioJson.getString(NAME_KEY);
            listenerStr = scenarioJson.getString(LISTENER_KEY);
            materialStr = scenarioJson.getString(MATERIAL_KEY);
            description = scenarioJson.getJSONArray(DESCRIPTION_KEY);
        } catch (JSONException ex) {
            ex.printStackTrace();
            throw new JSONException("JSON Object could not be parsed.");
        }

        // Parses listener class
        Material material;
        Class<? extends ScenarioListener> listener;
        try {
            listener = (Class<? extends ScenarioListener>)
                    Class.forName(LISTENER_CLS_FULL_QUALIFICATION + "." + listenerStr);
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new JSONException("Listener class provided for " + key + " is not a ScenarioListener.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new JSONException("Listener class provided for " + key + " is not defined.");
        }

        // Parses scenario material
        try {
            material = Material.valueOf(materialStr);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            throw new JSONException("Material provided for " + key + " is not a Bukkit Material.");
        }

        // Parses scenario description
        Scenario.Info info;
        try {
            info = new Scenario.Info(name, (List<String>)(List<?>) description.toList());
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new JSONException("Scenario description of " + key + " contains non-String entries");
        }

        Scenario scenario = new Scenario(name, material, listener);
        scenario.setInfo(info);
        return scenario;
    }
}
