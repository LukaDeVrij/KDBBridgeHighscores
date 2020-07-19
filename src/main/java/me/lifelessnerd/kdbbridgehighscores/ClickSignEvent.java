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
        if (player.getWorld().getName().equalsIgnoreCase("kbevent")) {

            if (event.getClickedBlock().getType() != null)
                if ((event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    final Sign sign = (Sign) event.getClickedBlock().getState();


                    if (sign.getLine(1).equalsIgnoreCase("Highscores")) {
                        final Scoreboard scoreboard = player.getScoreboard();
                        final Objective objective = scoreboard.getObjective("KBwins");
                        final Set<String> entries = scoreboard.getEntries();
                        final ConcurrentHashMap<Integer, String> scoresMap = new ConcurrentHashMap<>();
                        int highestScore = 0;

                        for (final String entryName : entries) {
                            final Score entryScore = objective.getScore(entryName);
                            int entryIntScore = entryScore.getScore();
                            scoresMap.put(entryIntScore, entryName);
                        }
                        // scoresMap created and filled

                        //getting best player based on highest key
                        for (final int entryIntScoreFE : scoresMap.keySet()) {
                            if (entryIntScoreFE > highestScore) {
                                highestScore = entryIntScoreFE;

                            }
                        }
                        final String highestScorePlayer = scoresMap.get(highestScore);
                        final Location firstName = new Location(Bukkit.getWorld("kbevent"), 26.5, 102.0, 0.5);
                        final Location firstScore = new Location(Bukkit.getWorld("kbevent"), 26.5, 101.75, 0.5);
                        final Location locDate = new Location(Bukkit.getWorld("kbevent"), 26.5, 100.0, 3.0);
                        final Location secondName = new Location(Bukkit.getWorld("kbevent"), 26.5, 101.25, 0.5);
                        final Location secondScore = new Location(Bukkit.getWorld("kbevent"), 26.5, 101.0, 0.5);
                        final Location thirdName = new Location(Bukkit.getWorld("kbevent"), 26.5, 100.5, 0.5);
                        final Location thirdScore = new Location(Bukkit.getWorld("kbevent"), 26.5, 100.25, 0.5);

                        final List<Entity> existingEntities = player.getLocation().getWorld().getEntities();

                        for (final Entity entity : existingEntities) {
                            if (entity.getType().equals(EntityType.ARMOR_STAND) &&
                                    (entity.getLocation().equals(firstName) |
                                            entity.getLocation().equals(firstScore) |
                                            entity.getLocation().equals(locDate) |
                                            entity.getLocation().equals(secondName) |
                                            entity.getLocation().equals(secondScore) |
                                            entity.getLocation().equals(thirdName) |
                                            entity.getLocation().equals(thirdScore))) {

                                entity.remove();
                            }
                        }
                        final ArmorStand holoFirstName = (ArmorStand) player.getWorld().spawnEntity(firstName, EntityType.ARMOR_STAND);
                        holoFirstName.setVisible(false);
                        holoFirstName.setCustomName("§b" + highestScorePlayer);
                        holoFirstName.setCustomNameVisible(true);
                        holoFirstName.setGravity(false);
                        final ArmorStand holoFirstScore = (ArmorStand) player.getWorld().spawnEntity(firstScore, EntityType.ARMOR_STAND);
                        holoFirstScore.setVisible(false);
                        holoFirstScore.setCustomName(String.valueOf(highestScore));
                        holoFirstScore.setCustomNameVisible(true);
                        holoFirstScore.setGravity(false);
                        player.sendMessage("[§6KDB§r] §aScores zijn ververst.");
                        System.out.println("Scores were updated in kbevent");

                        // getting the second best player
                        scoresMap.remove(highestScore);
                        highestScore = 0;
                        for (final int entryIntScoreFE : scoresMap.keySet()) {
                            if (entryIntScoreFE > highestScore) {
                                highestScore = entryIntScoreFE;
                            }
                        }

                        // tweede plek nu goede gecalibreerd
                        // nieuwe hologram voor tweede plek hieronder
                        final ArmorStand holoSecondName = (ArmorStand) player.getWorld().spawnEntity(secondName, EntityType.ARMOR_STAND);
                        holoSecondName.setVisible(false);
                        holoSecondName.setCustomName("§b" + scoresMap.get(highestScore));
                        holoSecondName.setCustomNameVisible(true);
                        holoSecondName.setGravity(false);

                        final ArmorStand holoSecondScore = (ArmorStand) player.getWorld().spawnEntity(secondScore, EntityType.ARMOR_STAND);
                        holoSecondScore.setVisible(false);
                        holoSecondScore.setCustomName(String.valueOf(highestScore));
                        holoSecondScore.setCustomNameVisible(true);
                        holoSecondScore.setGravity(false);

                        // getting the third best player
                        scoresMap.remove(highestScore);
                        highestScore = 0;
                        for (final int entryIntScoreFE : scoresMap.keySet()) {
                            if (entryIntScoreFE > highestScore) {
                                highestScore = entryIntScoreFE;
                            }
                        }
                        //derde plek gecalibreerd
                        final ArmorStand holoThirdName = (ArmorStand) player.getWorld().spawnEntity(thirdName, EntityType.ARMOR_STAND);
                        holoThirdName.setVisible(false);
                        holoThirdName.setCustomName("§b" + scoresMap.get(highestScore));
                        holoThirdName.setCustomNameVisible(true);
                        holoThirdName.setGravity(false);

                        final ArmorStand holoThirdScore = (ArmorStand) player.getWorld().spawnEntity(thirdScore, EntityType.ARMOR_STAND);
                        holoThirdScore.setVisible(false);
                        holoThirdScore.setCustomName(String.valueOf(highestScore));
                        holoThirdScore.setCustomNameVisible(true);
                        holoThirdScore.setGravity(false);


                        // date thingy
                        LocalDateTime myDateObj = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMM HH:mm:ss");

                        String formattedDate = myDateObj.format(myFormatObj);
                        System.out.println("Update time: " + formattedDate);

                        final ArmorStand hologramDate = (ArmorStand) player.getWorld().spawnEntity(locDate, EntityType.ARMOR_STAND);
                        hologramDate.setVisible(false);
                        hologramDate.setCustomName(formattedDate);
                        hologramDate.setCustomNameVisible(true);
                        hologramDate.setGravity(false);
                        //


                        sign.update();
                    }
                }
        }
    }
}
