package net.mcsquared.plugins.common;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class Gamer {
    int kills = 0;
    int deaths = 0;
    int online = 0;
    int kd = 0;




    public void onlineGamer() {}
    online= 1;


    public void onKill() {
        kills++;
        kd= kills/deaths;
    }

    public void onDeath() {
        deaths++;
        kd= kills/deaths;
    }

    public void KillDeath() {

    }
}
