package com.cpmason.nebuhc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.cpmason.nebuhc.NeBUhc.CHAT_HANDLE;

public class GreetingListener implements Listener {

    private static final String GREETING = "Welcome to NeBUhc!";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(CHAT_HANDLE + GREETING);
    }
}
