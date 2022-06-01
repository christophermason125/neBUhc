package com.cpmason.nebuhc;

import com.cpmason.nebuhc.events.BorderFinishedEvent;
import com.cpmason.nebuhc.game.NeBManager;
import com.cpmason.nebuhc.listeners.GreetingListener;
import com.cpmason.nebuhc.listeners.WaitUntilBorderShrinksListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class NeBUhc extends JavaPlugin {

    public static final String HANDLE = "[neBUhc] " + ChatColor.AQUA;
    public static final String CHAT_HANDLE = ChatColor.AQUA + "[neBUhc] " + ChatColor.RESET;

    private static final String ENABLE_MSG = "Initializing Wife Jokes...";
    private static final String DISABLE_MSG = "Smelling You Later...";
    private static NeBUhc plugin;

    @Override
    public void onEnable() {
        plugin = this;
        ConsoleCommandSender sender = getServer().getConsoleSender();

        sender.sendMessage(HANDLE + ENABLE_MSG);
        sender.sendMessage(String.valueOf(Material.AIR.isBlock()));

        registerListeners();

        NeBManager.getManager().loadNeB();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(HANDLE + DISABLE_MSG);
    }

    public static NeBUhc getNeBUhc() {
        return plugin;
    }

    private void registerListeners() {

        Listener[] listeners = {
                new GreetingListener(),
                new WaitUntilBorderShrinksListener()
        };

        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }
}
