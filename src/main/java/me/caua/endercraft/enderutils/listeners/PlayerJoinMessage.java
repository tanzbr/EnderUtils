package me.caua.endercraft.enderutils.listeners;

import me.caua.endercraft.enderutils.Main;
import me.caua.endercraft.enderutils.utils.ColorTranslate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.codehaus.plexus.util.StringUtils;

import java.util.List;

public class PlayerJoinMessage implements Listener {

    private List<String> message;
    private String messageFirst;
    private boolean sendFirstMessage;
    private boolean sendJoinMessage;

    public PlayerJoinMessage() {
        message = Main.getInstance().getConfig().getStringList("JoinMessage.message");
        messageFirst = Main.getInstance().getConfig().getString("JoinMessage.messageFirst");
        sendJoinMessage = Main.getInstance().getConfig().getBoolean("JoinMessage.enable");
        sendFirstMessage = Main.getInstance().getConfig().getBoolean("JoinMessage.enableFirstMessage");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.joinMessage(null);
        if (sendFirstMessage) {
            if (!p.hasPlayedBefore()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage("");
                    player.sendMessage(ColorTranslate.chatColors(p, messageFirst).replaceAll("<player>", p.getName()));
                    player.sendMessage("");
                }
            }
        }
        if (sendJoinMessage) {
            for (String messagem : message) {
                if (messagem.contains("<center>")) {
                    p.sendMessage(centerText(ColorTranslate.chatColors(p, messagem).replaceAll("<center>", "").replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size()))));
                } else {
                    p.sendMessage(ColorTranslate.chatColors(p, messagem).replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())));
                }
            }
        }
    }



    private String centerText(String text) {
        int maxWidth = 80,
                spaces = (int) Math.round((maxWidth-1.4* ChatColor.stripColor(text).length())/2);
        return StringUtils.repeat(" ", spaces)+text;
    }

}
