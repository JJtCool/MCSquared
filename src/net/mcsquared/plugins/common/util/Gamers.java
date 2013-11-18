package net.mcsquared.plugins.common.util;

import net.mcsquared.plugins.common.Gamer;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Richard
 * Date: 18/11/13
 * Time: 21:19
 * To change this template use File | Settings | File Templates.
 */
public class Gamers {

     private static HashMap<Player, Gamer> map = new HashMap<Player, Gamer>();


    public static Gamer getGamer(Player player) {
        Gamer g = map.get(player);
        if (g == null) {
            g = new Gamer();
            map.put(player, g);
        }
        return g;
    }
}
