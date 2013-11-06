package net.mcsquared.plugins.common.util;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;


/**
 * Sets up Scoreboard for players
 */
public class SetupScoreboards extends JavaPlugin implements Listener {



    private Scoreboard board;

    private static Team hunters;

    private static Team survivors;

    private static Team spectators;

    private int time = 1200;

    private int ptime = 120;          //Lobby timer

    private int pregameTime = 30;

    private int endTime = 120;

    private int SurvivorsInt = 0;

    private int HuntersInt = 0;

    private int SpectatorsInt = 0;

    private gameState.GameState state;

    private Location spawn = new Location(world, 5, 5, 5);

    private Location spawn2 = new Location(world, 5, 5, 5);

    private Location pad1 = new Location(world, 5, 5, 5);

    private String prefix = "ChatColor.WHITE + "[" + ChatColor.GREEN + "ManHunt" + ChatColor.WHITE "]";


    public void onEnable() {
        board = getServer().getScoreboardManager().getNewScoreboard();

        hunters = board.registerNewTeam("Hunters");

        survivors = board.registerNewTeam("Survivors");

        spectators = board.registerNewTeam("Spectators");

        hunters.setPrefix(ChatColor.RED + "");

        survivors.setPrefix(ChatColor.GREEN + "");

        spectators.setPrefix(ChatColor.AQUA + "");

        final Objective obj = board.registerNewObjective("Kills", "dummy");

        obj.setDisplayName(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Time: " + formatMMSS(time));

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        hunters.setAllowFriendlyFire(false);

        survivors.setAllowFriendlyFire(false);

        spectators.setAllowFriendlyFire(false);

        final Score score = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "Time: "));

        final Score score1 = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Hunters: "));

        final Score score2 = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Survivors: "));

        final Score score3 = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Players: "));

        Lobby();

        new BukkitRunnable() {
            public void run() {
                score.setScore(time);
                score1.setScore(HuntersInt);
                score2.setScore(SurvivorsInt);
                score3.setScore(SpectatorsInt);
                if (time == 0) {
                    onTimerFin();
                    cancel();
                    runNewTask();
                } else {
                    onMin();
                }
                time--;
            }
        }.runTaskTimerAsynchronously(this, 20L, 20L);
    }

    public void showPreScore(String time) {
  }

    public void Lobby() {
        showPreScore("ptime");
        ptime--;
        if(ptime == 0) {
            if(Bukkit.getServer().getOnlinePlayers().length == 8){
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.teleport(pad1);
                    state = gameState.GameState.Pregame;
                    PreGame();
                }
            }
        }
        else{
            Bukkit.getServer().broadcastMessage(prefix + ChatColor.RED + "Not enough players online!");
            ptime = 120;
        }
    }

    public void PreGame() {
        showPreScore("pregameTime");
        pregameTime--;
        if(pregameTime == 0) {
            state = gameState.GameState.ingame;
            ingame();
            }
        }

    public void ingame() {
        showPreScore("time");
        time--;
        if(pregameTime == 0) {
            state = gameState.GameState.endgame;
            endGame();
        }
    }

    public void endGame() {
        showPreScore("endTime");
        endTime--;
        if(endTime == 0) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(ChatColor.AQUA + "Game Over! \nServer restarting!");
            }
        }
    }





    public static String formatMMSS(int seconds) {
        int mins = (int) Math.floor(seconds/60);
        int secs = seconds-((mins)*60);

        return (mins < 10 ? "0" + mins : mins + "") + ":" + (secs < 10 ? "0" + secs : secs + "");
    }

    public static String formatHHMMSS(int seconds) {
        int hours = (int) Math.floor((int) Math.floor(seconds/60));
        int mins = (int) Math.floor(((hours*60)*60) - seconds/60);
        int secs = seconds-((mins)*60);

        return (hours < 10 ? "0" + hours : hours + "") + ":" + (mins < 10 ? "0" + mins : mins + "") + ":" + (secs < 10 ? "0" + secs : secs + "");
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
                          event.getPlayer().setScoreboard(board);
        event.getPlayer().sendMessage(ChatColor.GREEN + " Welcome to " + ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "Man Hunt");
        Player player = event.getPlayer();
        spectators.addPlayer(player);
        player.getInventory().clear();
        player.updateInventory();

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) book.getItemMeta();
        bm.addPage(ChatColor.ITALIC + "" + ChatColor.AQUA + "Man Hunt" + ChatColor.BLUE + "\nWelcome to Man Hunt" + "\nMan Hunt is a unique Gamemode to MCSquared."
                + "\nThe objectives of Man Hunt are as follows" + ChatColor.GREEN +
                "\nAs the Survivors you have to survive the time limit against the hunters. " +
                "Team up with fellow Survivors to increase your chances of survival. " +
                "Loot chests scattered around the maps to improve your survival chances."
                + ChatColor.RED + "\nAs the Hunters, your aim is to eliminate all Survivors, be wary they will fight back!"
                + "\nIf you die you will have a respawn time until you can respawn" + ChatColor.BLUE + "\nVisit www.mc-sq.net for more infomation on all of our gamemodes!"  );
        bm.setAuthor(ChatColor.GREEN + "MCSquared Network");
        bm.setTitle(ChatColor.DARK_GREEN + "Man Hunt");
        book.setItemMeta(bm);
        player.getInventory().addItem(book);

       if(state == gameState.GameState.Lobby)  {
               event.getPlayer().setGameMode(GameMode.SURVIVAL);
               player.teleport(spawn);

       }
    }




    private void onMin() {
        final int mins = time / 60;

        if (time > 60) {

            if (time % 60 == 0) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.AQUA
                        + "Vertigo" + ChatColor.GOLD + "] "
                        + ChatColor.DARK_AQUA + "" + mins
                        + " Minutes Remaining!");
            }
        } else if (time == 60) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.AQUA
                    + "Vertigo" + ChatColor.GOLD + "] " + ChatColor.DARK_AQUA
                    + "" + mins + " Minute Remain!");
        }

        else if (time <= 10) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.AQUA
                    + "Vertigo" + ChatColor.GOLD + "] " + ChatColor.DARK_AQUA
                    + "" + time + " Seconds Remaining!");
        } else if (time == 1) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[" + ChatColor.AQUA
                    + "Vertigo" + ChatColor.GOLD + "] " + ChatColor.DARK_AQUA
                    + "" + time + " Second Remain!");
        }
    }










    }

