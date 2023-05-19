package me.caua.endercraft.enderutils.utils;

import me.caua.endercraft.enderutils.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockHiddenSyntax implements Listener {

    private final boolean blockHidden = Main.getInstance().getConfig().getBoolean("Protector.blockHiddenSyntax");
    private final List<String> blockedCmds = Main.getInstance().getConfig().getStringList("Protector.blockedCommands");
    private final String errorMessage = Main.getInstance().getConfig().getString("Protector.errorMessage");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabComplete(TabCompleteEvent e) {
        if (e.isCancelled()) return;
        if (e.getSender().hasPermission("enderutils.protector.bypass")) return;

        if (blockHidden) {
            List<String> newCompletions = e.getCompletions();
            for (String cmd : e.getCompletions()) {
                if (!cmd.contains(":")) {
                    newCompletions.remove(cmd);
                }
                if (blockedCmds.contains(cmd)) {
                    newCompletions.remove(cmd);
                }
            }
            e.setCompletions(newCompletions);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (e.isCancelled()) return;
        if (e.getPlayer().hasPermission("enderutils.protector.bypass")) return;
        if (blockedCmds.size() == 0 && !blockHidden) return;

        String cmd = e.getMessage().split(" ")[0].replaceAll("/", "");
        if (blockHidden) {
            if (cmd.contains(":")) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ColorTranslate.chatColors(errorMessage));
            }
        }
        if (blockedCmds.contains(cmd)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ColorTranslate.chatColors(errorMessage));
        }
    }

}
