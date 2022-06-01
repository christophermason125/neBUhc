package com.cpmason.nebuhc.listeners;

import com.cpmason.nebuhc.NeBUhc;
import com.cpmason.nebuhc.events.BorderFinishedEvent;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.events.UhcStartedEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WaitUntilBorderShrinksListener implements Listener {

    private static final long TICKS_PER_SECOND = 20;

    @EventHandler
    public void onGameStart(UhcStartedEvent event) {
        MainConfig config = event.getGameManager().getConfig();
        long timeUntilShrunk = (
                (long) MainConfig.BORDER_TIME_TO_SHRINK.getValue(config) +
                        (long) MainConfig.BORDER_TIME_BEFORE_SHRINK.getValue(config)) *
                TICKS_PER_SECOND;

        Bukkit.getScheduler().runTaskLater(NeBUhc.getNeBUhc(), () -> Bukkit.getPluginManager().callEvent(new BorderFinishedEvent()), timeUntilShrunk);
    }
}
