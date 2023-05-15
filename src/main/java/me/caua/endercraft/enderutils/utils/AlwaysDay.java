package me.caua.endercraft.enderutils.utils;

import me.caua.endercraft.enderutils.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class AlwaysDay {

    public AlwaysDay() {
        Main.getInstance().getConfig().getStringList("AlwaysDay.worlds").forEach((worldName) -> {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                World world = Bukkit.getWorld(worldName);
                if (world == null) return;
                if(world.getTime() > 12000) {
                    world.setTime(1000);
                }
            }, 0, 100);
        });
    }

}
