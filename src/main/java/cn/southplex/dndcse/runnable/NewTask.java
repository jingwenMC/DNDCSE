package cn.southplex.dndcse.runnable;

import cn.southplex.dndcse.main;
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
            GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(p);
            if(gamePlayer.getPlayerState()== GPlayerState.DEATH)continue;
            else
            {
                gamePlayer.newTask();
            }
        }
        Bukkit.broadcastMessage(ChatColor.AQUA+"[游戏]任务已重置");
    }
}
