package me.lifelessnerd.kdbbridgehighscores;

import org.bukkit.event.EventHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.entity.ArmorStand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.block.Action;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Listener;

public class ClickSignEvent implements Listener
{
    @EventHandler
    public void onPlayerClickSign(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final String playerName = event.getPlayer().getName();
        if (player.getWorld().getName().equalsIgnoreCase("kbevent")) {

            if (event.getClickedBlock().getType() != null) {

                if ((event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    final Sign sign = (Sign)event.getClickedBlock().getState();

                    if (sign.getLine(1).equalsIgnoreCase("Highscores")) {
                        final Scoreboard scoreboard = player.getScoreboard();
                        final Objective objective = scoreboard.getObjective("KBwins");
                        final Score kitScore = objective.getScore(playerName);
                        final Set<String> entries = (Set<String>)scoreboard.getEntries();
                        final ConcurrentHashMap<Integer, String> scoresMap = new ConcurrentHashMap<Integer, String>();
                        int highestScore = 0;

                        for (final String entryName : entries) {
                            final Score entryScore = objective.getScore(entryName);
                            final int entryIntScore = entryScore.getScore();
                            scoresMap.put(entryIntScore, entryName);
                        }
                        // scoresMap created and filled

                        //getting best player based on highest key
                        for (final int entryIntScoreFE : scoresMap.keySet()) {
                            if (entryIntScoreFE >= highestScore) {
                                highestScore = entryIntScoreFE;

                            }
                        }
                        final String highestScorePlayer = scoresMap.get(highestScore);
                        final Location loc = new Location(Bukkit.getWorld("kbevent"), 26.5, 102.0, 0.5);
                        final Location locln2 = new Location(Bukkit.getWorld("kbevent"), 26.5, 101.75, 0.5);
                        final Location locDate = new Location(Bukkit.getWorld("kbevent"), 26.5, 100.0, 3.0);
                        final List<Entity> existingEntities = (List<Entity>)player.getLocation().getWorld().getEntities();

                        for (final Entity entity : existingEntities) {
                            if (entity.getType().equals((Object)EntityType.ARMOR_STAND) && (entity.getLocation().equals((Object)loc) | entity.getLocation().equals((Object)locln2)) | entity.getLocation().equals(locDate)) {

                                entity.remove();
                            }
                        }
                        final ArmorStand hologram = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                        hologram.setVisible(false);
                        hologram.setCustomName("§b" + highestScorePlayer);
                        hologram.setCustomNameVisible(true);
                        hologram.setGravity(false);
                        final ArmorStand hologramln2 = (ArmorStand) player.getWorld().spawnEntity(locln2, EntityType.ARMOR_STAND);
                        hologramln2.setVisible(false);
                        hologramln2.setCustomName(String.valueOf(highestScore));
                        hologramln2.setCustomNameVisible(true);
                        hologramln2.setGravity(false);
                        player.sendMessage("§aScores zijn ververst.");
                        System.out.println("Scores were updated in kbevent");

                        // getting the second best player
                        player.sendMessage(scoresMap.toString());
                        scoresMap.remove(highestScore);
                        player.sendMessage(highestScore + " deleted");
                        highestScore = 0;
                        player.sendMessage(scoresMap.toString());
                        for (final int entryIntScoreFE : scoresMap.keySet()) {
                            if (entryIntScoreFE >= highestScore) {
                                highestScore = entryIntScoreFE;

                            }
                        }
                        player.sendMessage("Second best is " + highestScore + " by " + scoresMap.get(highestScore));
                        // tweede plek nu goede gecalibreerd
                        // nieuwe hologram voor tweede plek hieronder



                        // date thingy
                        LocalDateTime myDateObj = LocalDateTime.now();
                        System.out.println("Before formatting: " + myDateObj);
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMM HH:mm:ss");

                        String formattedDate = myDateObj.format(myFormatObj);
                        System.out.println("After formatting: " + formattedDate);

                        final ArmorStand hologramDate = (ArmorStand) player.getWorld().spawnEntity(locDate, EntityType.ARMOR_STAND);
                        hologramDate.setVisible(false);
                        hologramDate.setCustomName(formattedDate);
                        hologramDate.setCustomNameVisible(true);
                        hologramDate.setGravity(false);




                        sign.update();
                    }
                }
            }
        }
    }
}
