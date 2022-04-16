package cn.southplex.dndcse.managers;

import cn.southplex.dndcse.DNDCSE;
import cn.southplex.dndcse.utils.GameState;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class BungeeManager implements Listener {
    public void quitSend(Player p){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("ptlobby-1");

        p.sendPluginMessage(DNDCSE.getInstance(), "BungeeCord", out.toByteArray());
    }

    @EventHandler
    public void motdChanger(ServerListPingEvent evt){
        if(DNDCSE.getInstance().getGameManager() == null){
            evt.setMotd("初始化中");
            return;
        }
        if(!(DNDCSE.getInstance().getGameManager().getGameState().equals(GameState.WAIT) || DNDCSE.getInstance().getGameManager().getGameState().equals(GameState.READY)))
        evt.setMotd(DNDCSE.getInstance().getGameManager().getGameState().getValue());
        else if(Bukkit.getOnlinePlayers().size()<DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("max")) evt.setMotd("wait");
                else evt.setMotd("人数已满");
    }
}
