package cn.southplex.dndcse.runnable;

import cn.southplex.dndcse.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameEnd extends BukkitRunnable {
    @Override
    public void run() {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                main.getInstance().getBungeeManager().quitSend(p);
            }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
