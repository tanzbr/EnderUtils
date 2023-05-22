package me.caua.endercraft.enderutils.utils;

import me.caua.endercraft.enderutils.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.floodgate.api.FloodgateApi;

public class ExecuteFloodgateCmds implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!FloodgateApi.getInstance().isFloodgatePlayer(e.getPlayer().getUniqueId())) return;
        for (String cmd : Main.getInstance().getConfig().getStringList("Utils.floodgateJoinCmds.commands")) {
            if (cmd.equals("")) return;
            if (cmd.contains(";firstjoin")) {
                if (!e.getPlayer().hasPlayedBefore()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", e.getPlayer().getName()).replaceAll(";firstjoin", ""));
                }
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", e.getPlayer().getName()));
            }

        }
    }

}
