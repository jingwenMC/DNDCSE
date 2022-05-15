package cn.southplex.dndcse.runnable;

import cn.southplex.dndcse.DNDCSE;
import cn.southplex.dndcse.managers.BungeeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameEnd extends BukkitRunnable {
    @Override
    public void run() {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                for(String lobby : DNDCSE.getInstance().getConfig().getStringList("lobbies")) {
                    DNDCSE.getInstance().getBungeeManager().quitSend(p,lobby);
                }
            }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
