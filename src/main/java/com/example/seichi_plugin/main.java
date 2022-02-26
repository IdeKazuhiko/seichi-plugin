package com.example.seichi_plugin;

import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class main extends JavaPlugin {

    blockBreakListener bbListener = null;
    blockPlaceListener bpListener = null;

    public main(){
        bbListener = new blockBreakListener();
        bpListener = new blockPlaceListener();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("seichi on enable plugin");
        getServer().getPluginManager().registerEvents(bbListener, this);
        getServer().getPluginManager().registerEvents(bpListener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("seichi on disable plugin");
        HandlerList.unregisterAll(bbListener);
        HandlerList.unregisterAll(bpListener);
    }

    // onCommand は plugin.yml に記載されたコマンドが呼ばれた時に実行
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(args.length == 0){
            getLogger().info("please set on or off");
            return false;
        }

        if (args[0].equals("on")) {
            getLogger().info("on");
            getServer().getPluginManager().registerEvents(bbListener, this);
            getServer().getPluginManager().registerEvents(bpListener, this);

        } else {
            getLogger().info("off");
            HandlerList.unregisterAll(bbListener);
            HandlerList.unregisterAll(bpListener);
        }
        return true;
    }
}
