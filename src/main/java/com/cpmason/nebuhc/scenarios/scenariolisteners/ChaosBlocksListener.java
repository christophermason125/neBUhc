package com.cpmason.nebuhc.scenarios.scenariolisteners;

import com.cpmason.nebuhc.NeBUhc;
import com.cpmason.nebuhc.events.BorderFinishedEvent;
import com.gmail.val59000mc.events.UhcStartedEvent;
import com.gmail.val59000mc.events.UhcStartingEvent;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;

import java.util.*;

import static com.cpmason.nebuhc.NeBUhc.HANDLE;

public class ChaosBlocksListener extends ScenarioListener {

    private static final int FREQ = 60;

    /** Maximum Y Coordinate */
    private static final int MAX_Y = 128;

    private static final Random RAND = new Random();

    private static final String ERROR = "Could not run ChaosBlocks.";

    private static int task = -1;

    private static World world;
    private static WorldBorder border = null;

    private static final List<Material> validTransformations = new ArrayList<>();

    @EventHandler
    public void onGameStarting(UhcStartingEvent event) {
        for (Material material : Material.values()) {
            if (material.isBlock()) {
                validTransformations.add(material);
            }
        }

        world = GameManager.getGameManager().getMapLoader().getUhcWorld(World.Environment.NORMAL);

        if (world != null) {
            border = world.getWorldBorder();
        }
    }

    @EventHandler
    public void onGameStart(UhcStartedEvent event) {
        if (border != null) {
            task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(NeBUhc.getNeBUhc(), ChaosBlocksListener::transformBlocks, 1, 1);
        }
        if (task == -1) {
            Bukkit.getServer().getConsoleSender().sendMessage(HANDLE + ERROR);
        }
    }

    private static void transformBlocks() {
        int size = (int) Math.floor(border.getSize());
        for (int i = 0; i < FREQ; i++) {
            int x = RAND.nextInt(size) - size/2;
            int y = RAND.nextInt(MAX_Y + 1);
            int z = RAND.nextInt(size) - size/2;

            world.getBlockAt(x, y, z).setType(validTransformations.get(RAND.nextInt(validTransformations.size())));
        }
    }

    @EventHandler
    public void onBorderFinished(BorderFinishedEvent event) {
        if (task != -1) {
            Bukkit.getScheduler().cancelTask(task);
        }
    }

    @Override
    public void onDisable() {
        if (task != -1) {
            Bukkit.getScheduler().cancelTask(task);
        }
    }

}
