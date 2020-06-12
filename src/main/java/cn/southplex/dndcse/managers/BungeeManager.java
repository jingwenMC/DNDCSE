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
		/*
		out.writeUTF(p.getName());
		  try{
		  Core.get().getPc().getGamePlayer(p).getGs().show();
		  }catch(Exception e){};
		  p.sendPluginMessage(Core.get(), "LobbyConnect", out.toByteArray());*/
        out.writeUTF("Connect");
        out.writeUTF("lobby");
        // 如果你不关心玩家是谁，可以使用下面的代码
        // Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        // 或者，你希望指定一个玩家接收消息

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
