package me.caua.endercraft.enderutils;

import me.caua.endercraft.enderutils.listeners.PlayerJoinMessage;
import me.caua.endercraft.enderutils.utils.AlwaysDay;
import me.caua.endercraft.enderutils.utils.BlockHiddenSyntax;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static AlwaysDay alwaysDay;

    public void onEnable() {
        instance = this;
        loadConfig();
        registerCommands();
        registerEvents();

        // Enable always day
        if (getConfig().getConfigurationSection("AlwaysDay").getBoolean("enable")) {
            Bukkit.getConsoleSender().sendMessage("§b[EnderUtils] §aAtivando AlwaysDay.");
            alwaysDay = new AlwaysDay();
        }

        Bukkit.getConsoleSender().sendMessage("§b[EnderUtils] §fPlugin iniciado com sucesso.");
    }


    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§b[EnderUtils] §fPlugin desligado com sucesso.");
    }

    public void registerCommands() {
       //getCommand("resettheend").setExecutor(new ResetTheEnd());
    }
    public void registerEvents() {
        // register player join event for join message
        Bukkit.getConsoleSender().sendMessage("§b[EnderUtils] §aAtivando PlayerJoinMessage.");
        Bukkit.getPluginManager().registerEvents(new PlayerJoinMessage(), this);
        if (getConfig().getBoolean("Protector.enable")) {
            Bukkit.getConsoleSender().sendMessage("§b[EnderUtils] §aAtivando Protector.");
            Bukkit.getPluginManager().registerEvents(new BlockHiddenSyntax(), this);
        }
    }

    private void loadConfig(){
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }

    public static Main getInstance() {
        return instance;
    }
}