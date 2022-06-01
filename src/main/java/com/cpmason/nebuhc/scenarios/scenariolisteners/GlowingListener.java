package com.cpmason.nebuhc.scenarios.scenariolisteners;

import com.cpmason.nebuhc.NeBUhc;
import com.cpmason.nebuhc.events.BorderFinishedEvent;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

import static com.cpmason.nebuhc.NeBUhc.CHAT_HANDLE;
import static org.bukkit.Bukkit.getServer;

public class GlowingListener extends ScenarioListener {


    private static final int INFINITE_DURATION = 1000000;

    private static final String GLOWING_MSG = "All remaining players are now glowing.";
    private static final Sound GLOWING_SOUND = Sound.ENTITY_PLAYER_LEVELUP;
    private static final float GLOWING_SOUND_PITCH = 1;
    private static final float GLOWING_SOUND_VOLUME = 1;

    @EventHandler
    public void onBorderFinished(BorderFinishedEvent event) {
        PlayerManager playerManager = GameManager.getGameManager().getPlayerManager();
        getServer().broadcastMessage(CHAT_HANDLE + GLOWING_MSG);

        getServer().getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), GLOWING_SOUND, GLOWING_SOUND_PITCH, GLOWING_SOUND_VOLUME));

        Set<UhcPlayer> players = playerManager.getAllPlayingPlayers();

        for (UhcPlayer player : players) {
            try {
                glow(player.getPlayer());
            } catch (UhcPlayerNotOnlineException ex) {
                // Do nothing
            }
        }

        Bukkit.getPluginManager().registerEvents(new GiveGlowingLaterListener(), NeBUhc.getNeBUhc());
    }

    private static void glow(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, INFINITE_DURATION, 1, false, false, false));
    }

    private static class GiveGlowingLaterListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            Player joinedPlayer = event.getPlayer();
            if (joinedPlayer.getPotionEffect(PotionEffectType.GLOWING) == null) {
                for (UhcPlayer uhcPlayer : GameManager.getGameManager().getPlayerManager().getAllPlayingPlayers()) {
                    if (joinedPlayer.getUniqueId().equals(uhcPlayer.getUuid())) {
                        glow(joinedPlayer);
                    }
                }
            }

        }
    }
}
