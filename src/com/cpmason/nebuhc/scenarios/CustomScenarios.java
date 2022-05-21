package com.cpmason.nebuhc.scenarios;

import com.cpmason.nebuhc.scenarios.scenariolisteners.GlowingListener;
import com.gmail.val59000mc.scenarios.Scenario;
import org.bukkit.Material;

public class CustomScenarios {
    public static final Scenario GLOWING = new Scenario("glowing", Material.GLOWSTONE, GlowingListener.class);
}
