package cn.southplex.dndcse.runnable;

import cn.southplex.dndcse.main;
import cn.southplex.dndcse.managers.BungeeManager;
import cn.southplex.dndcse.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GameLoop extends BukkitRunnable {
    NMSUtil nmsUtil = new NMSHandler();
    static int sb = 0;
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(main.getInstance().getPlayerManager().getGamePlayer(p).getPlayerState()== GPlayerState.DEATH)
            {
                nmsUtil.sendActionBar(p, ChatColor.AQUA+"你现在是旁观模式!输入 /hub 返回大厅.");
                p.setGameMode(GameMode.SPECTATOR);
            }
        }
        if(main.getInstance().getGameManager().getGameState() == GameState.GAMING && Bukkit.getOnlinePlayers().size()>0)
        {
            int i =0;
            GamePlayer gamePlayer = null;
            for(Player p : Bukkit.getOnlinePlayers())
            {
                if(main.getInstance().getPlayerManager().getGamePlayer(p).getPlayerState()==GPlayerState.ALIVE)
                {
                    i++;
                    gamePlayer=main.getInstance().getPlayerManager().getGamePlayer(p);
                }
            }
            if(i==1)
            {
                main.getInstance().getGameManager().setGameState(GameState.END);
                Bukkit.broadcastMessage(ChatColor.RED+"游戏结束!");
                Bukkit.broadcastMessage(ChatColor.GOLD+"==============================");
                Bukkit.broadcastMessage("获胜者: "+ChatColor.GOLD+gamePlayer.getPlayer().getName());
                for(Player p : Bukkit.getOnlinePlayers())
                {
                    if(main.getInstance().getPlayerManager().getGamePlayer(p).getPlayerState()==GPlayerState.ALIVE)
                        nmsUtil.sendTitle(p,0,200,20,ChatColor.GOLD+"胜利!","你赢得了比赛!");
                    else nmsUtil.sendTitle(p,0,200,20,ChatColor.RED+"游戏结束!","你没有赢得比赛!");
                }
                Bukkit.broadcastMessage(ChatColor.GOLD+"==============================");
                Bukkit.broadcastMessage(ChatColor.AQUA+"游戏结束!将在5秒后将你传送到大厅...");
                try {
                    BukkitTask task = new GameEnd().runTaskLater(main.getInstance(),100);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    BukkitTask task2 = new BukkitRunnable()
                    {

                        @Override
                        public void run() {
                            Bukkit.shutdown();
                        }
                    }.runTaskLater(main.getInstance(),105);

                }

            }
                if(sb==0) {
                    sb=20;
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        GamePlayer gp = main.getInstance().getPlayerManager().getGamePlayer(p);
                        ScoreboardUtil.updateOneScoreboard(gp, i);
                    }
                }
                sb--;
        }
        Bukkit.getWorld(main.getInstance().getWorldName()).setTime(6000);
        Bukkit.getWorld(main.getInstance().getWorldName2()).setTime(6000);
        //doDaylightCycle
    }
}
