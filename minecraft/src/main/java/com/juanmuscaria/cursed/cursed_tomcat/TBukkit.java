package com.juanmuscaria.cursed.cursed_tomcat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Paths;
import java.util.logging.Logger;

public class TBukkit extends JavaPlugin {
    Logger log;

    @Override
    public void onEnable() {
        try {
            log = getLogger();
        } catch (Throwable ignored) {
            // Bukkit version too old, create our own logger
            log = Logger.getLogger("Tomcat4Bukkit");
        }
        startTomcat();
    }

    @Override
    public void onDisable() {
        stopSilently();
    }

    ITomcatContainer tomcat;
    private void stopSilently() {
        try {
            tomcat.stopAndAwait();
        } catch (Exception ignored) {}
    }

    private boolean startTomcat() {
        try {
            tomcat = TomcatBootstrap.configure(Paths.get(getDataFolder().getAbsolutePath()), true);
            tomcat.start();
            return true;
        } catch (Exception e) {
            log.severe("Unable to initialize Apache Tomcat!");
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(label);
        return false;
    }
}
