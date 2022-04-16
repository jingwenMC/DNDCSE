package cn.southplex.dndcse.runnable;

import cn.southplex.dndcse.DNDCSE;
import cn.southplex.dndcse.utils.GPlayerState;
import cn.southplex.dndcse.utils.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NewTask extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            GamePlayer gamePlayer = DNDCSE.getInstance().getPlayerManager().getGamePlayer(p);
            if(gamePlayer.getPlayerState()== GPlayerState.DEATH)continue;
            else
            {
                gamePlayer.newTask();
            }
        }
        Bukkit.broadcastMessage(ChatColor.AQUA+"[游戏]挑战已重置");
    }
}
