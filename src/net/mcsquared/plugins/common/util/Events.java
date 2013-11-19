package net.mcsquared.plugins.common.util;

import net.mcsquared.plugins.common.Gamer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created with IntelliJ IDEA.
 * User: Richard
 * Date: 18/11/13
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */
public class Events extends JavaPlugin implements Listener {


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Gamer gamer = Gamers.getGamer(event.getEntity().getPlayer());

        gamer.onDeath();

        Gamer killer = Gamers.getGamer(event.getEntity().getKiller() );

        killer.onKill();
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Gamer gameronline = Gamers.getGamer(event.getPlayer());
        gameronline
    }


}

