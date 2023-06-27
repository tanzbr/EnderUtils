package me.caua.endercraft.enderutils;

import me.caua.endercraft.enderutils.listeners.PlayerJoinMessage;
import me.caua.endercraft.enderutils.utils.AlwaysDay;
import me.caua.endercraft.enderutils.utils.BlockHiddenSyntax;
import me.caua.endercraft.enderutils.utils.ExecuteFloodgateCmds;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Objects;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class Main extends JavaPlugin {

    private static Main instance;
    private static AlwaysDay alwaysDay;
    private List<String> logFilters;
    private BukkitTask filterexec;

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

        // Enable log filters
        if (getConfig().getBoolean("Utils.logFilterEnable")) {
            filterExec();
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
        if (getConfig().getBoolean("Utils.floodgateJoinCmds.enable")) {
            if (Bukkit.getPluginManager().getPlugin("Floodgate") != null) {
                Bukkit.getConsoleSender().sendMessage("§b[EnderUtils] §aAtivando Floodgate Commands.");
                Bukkit.getPluginManager().registerEvents(new ExecuteFloodgateCmds(), this);
            } else {
                Bukkit.getConsoleSender().sendMessage("§b[EnderUtils] §cFloodgate não encontrado.");
            }
        }
    }

    private void loadConfig(){
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }

    public void filterExec() {
        filterexec = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage("§b[EnderUtils] §aAplicando filtros de logs.");
                logFilters = getConfig().getStringList("Utils.logFilters");
                Filter f = new Filter(){
                    public boolean isLoggable(LogRecord line) {
                        for (String filter : logFilters) {
                            if (line.getMessage().contains(filter)) {
                                return false;
                            }
                        }
                        return false;
                    }
                    public String doFilter(String arg0) {
                        return null;
                    }
                    public String doFilterUrl(String arg0) {
                        return null;
                    }};
                getServer().getLogger().setFilter(f);
                this.cancel();
            }
        }.runTaskTimerAsynchronously(instance, 1200, 1200);
    }

    public static Main getInstance() {
        return instance;
    }
}