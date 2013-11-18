package net.mcsquared.plugins.common.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created with IntelliJ IDEA.
 * User: Richard
 * Date: 06/11/13
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public class ChestConfig extends JavaPlugin implements Listener {

        public void onEnable() {
                    SetupConfig(getConfig());
        }

        public void SetupConfig(FileConfiguration config) {
            config.set("chest.spawn.items", 220-1);
            config.set("chest.nearspawn.items", 220-1);
            config.set("chest.awayspawn.items", 220-1);
            config.set("chest.spawn.items", 220-1);




        }
















}
