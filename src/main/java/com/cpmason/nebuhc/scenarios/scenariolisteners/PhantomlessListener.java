package com.cpmason.nebuhc.scenarios.scenariolisteners;

import com.gmail.val59000mc.events.UhcGameStateChangedEvent;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;

public class PhantomlessListener extends ScenarioListener {

    @EventHandler
    public void onGameStart(UhcGameStateChangedEvent event) {
        if (event.getOldGameState() == GameState.STARTING) {
            World uhcWorld = GameManager.getGameManager().getMapLoader().getUhcWorld(World.Environment.NORMAL);
            if (uhcWorld != null) {
                uhcWorld.setGameRule(GameRule.DO_INSOMNIA, false);
            }
        }
    }
}
