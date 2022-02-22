package cn.southplex.dndcse.managers;

import cn.southplex.dndcse.main;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class BungeeManager implements Listener {
    public void quitSend(Player p){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("lobby");

        p.sendPluginMessage(main.getInstance(), "BungeeCord", out.toByteArray());
    }

    @EventHandler
    public void motdChanger(ServerListPingEvent evt){
        if(main.getInstance().getGameManager() == null){
            evt.setMotd(ChatColor.RED + "loading");
            return;
        }
        evt.setMotd(main.getInstance().getGameManager().getGameState().getValue());

    }
}
