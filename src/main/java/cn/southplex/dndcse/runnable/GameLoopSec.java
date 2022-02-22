package cn.southplex.dndcse.runnable;

import cn.southplex.dndcse.main;
import cn.southplex.dndcse.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public class GameLoopSec extends BukkitRunnable {
    NMSUtil nmsUtil = new NMSHandler();
    @Override
    public void run() {
        int i = main.getInstance().getCt();
        //if(main.getInstance().getGameManager().getGameState())
        if(main.getInstance().getGameManager().getGameState()==GameState.WAIT||main.getInstance().getGameManager().getGameState()==GameState.READY) {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                GamePlayer gp = main.getInstance().getPlayerManager().getGamePlayer(p);
                ScoreboardUtil.updateOneScoreboard(gp,0);
            }
            if (Bukkit.getOnlinePlayers().size() >= main.getPlugin(main.class).getConfig().getInt("min")) {
                if(i == main.getPlugin(main.class).getConfig().getInt("wait_time"))
                    Bukkit.broadcastMessage(ChatColor.AQUA + "[游戏]开始倒计时!");
                main.getInstance().getGameManager().setGameState(GameState.READY);
                for (Player p : Bukkit.getOnlinePlayers()) p.setLevel(i);
                if (i == 60) Bukkit.broadcastMessage(ChatColor.AQUA + "倒计时60秒!");
                if (i == 30) Bukkit.broadcastMessage(ChatColor.AQUA + "倒计时30秒!");
                if (i == 15) Bukkit.broadcastMessage(ChatColor.AQUA + "倒计时15秒!");
                if (i == 10) Bukkit.broadcastMessage(ChatColor.YELLOW + "倒计时10秒!");
                if (i == 3) Bukkit.broadcastMessage(ChatColor.RED + "倒计时3秒!");
                if (i == 3) for (Player p : Bukkit.getOnlinePlayers())
                    nmsUtil.sendTitle(p, 0, 30, 0, ChatColor.YELLOW + "3", "即将开始");
                if (i == 2) Bukkit.broadcastMessage(ChatColor.RED + "倒计时2秒!");
                if (i == 2) for (Player p : Bukkit.getOnlinePlayers())
                    nmsUtil.sendTitle(p, 0, 30, 0, ChatColor.GOLD + "2", "即将开始");
                if (i == 1) Bukkit.broadcastMessage(ChatColor.RED + "倒计时1秒!");
                if (i == 1) for (Player p : Bukkit.getOnlinePlayers())
                    nmsUtil.sendTitle(p, 0, 30, 0, ChatColor.RED + "1", "即将开始");
                if (i == 0) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.setMaxHealth(6);
                        Bukkit.getWorld(main.getInstance().getWorldName2()).setGameRuleValue("naturalRegeneration", "false");
                        p.setGameMode(GameMode.SURVIVAL);
                        p.teleport(main.getInstance().getSpawnLocation());
                        p.getInventory().clear();
                        p.sendMessage(ChatColor.GOLD + "[游戏]游戏开始!");
                        p.sendMessage(ChatColor.GOLD + "[游戏]Tip:做事需谨慎!");
                        p.sendMessage(ChatColor.GOLD + "[游戏]Tip:输入/dndc q <玩家名>进行查询!(不能查自己)");
                        nmsUtil.sendTitle(p, 0, 60, 20, ChatColor.RED + "游戏开始!", "争取活到最后!");
                        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(p);
                        gamePlayer.setPlayerState(GPlayerState.ALIVE);
                        gamePlayer.newTask();
                        main.getInstance().getGameManager().setGameState(GameState.GAMING);
                    }
                    i=main.getPlugin(main.class).getConfig().getInt("game_time")+1;
                    main.getInstance().setCt(main.getPlugin(main.class).getConfig().getInt("game_time")+1);
                    main.getInstance().getGameManager().setRandomMission();
                    main.getInstance().getGameManager().setGameState(GameState.GAMING);
                    BukkitTask bukkitTaska = new NewTask().runTaskTimer(main.getInstance(),2400,2400);
                }
                i--;
                main.getInstance().setCt(i);
            } else {
                if (i != main.getPlugin(main.class).getConfig().getInt("wait_time")) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + "[游戏]人数不足,倒计时取消!");
                    main.getInstance().setCt(main.getPlugin(main.class).getConfig().getInt("wait_time"));
                    main.getInstance().getGameManager().setGameState(GameState.WAIT);
                }
            }
        }
        if(main.getInstance().getGameManager().getGameState()==GameState.GAMING) {
            checkMission();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (main.getInstance().getGameManager().getGameState() == GameState.GAMING)
                    main.getInstance().getPlayerManager().getGamePlayer(p).setIdle(main.getInstance().getPlayerManager().getGamePlayer(p).getIdle() - 1);
                if (main.getInstance().getPlayerManager().getGamePlayer(p).getPlayerState() == GPlayerState.DEATH) {
                    main.getInstance().getPlayerManager().getGamePlayer(p).setIdle(30);
                }
                if (main.getInstance().getPlayerManager().getGamePlayer(p).getIdle() <= 0) {
                    p.sendMessage(ChatColor.AQUA + "[游戏]你因长时间未移动而失败任务!");
                    main.getInstance().getPlayerManager().getGamePlayer(p).failTask();
                    main.getInstance().getPlayerManager().getGamePlayer(p).setIdle(30);
                }
            }
            if(i>=0) {
                i--;
                main.getInstance().setCt(i);
            }
        }
    }

    private void checkMission() {
        String winner = null;
        boolean lock = false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerInventory inventory = p.getInventory();
            if(inventory.contains(main.getInstance().getGameManager().getCurrentMission().getMaterial()))
                winner = p.getName();
        }
        if(winner!=null && (!lock)) {
            Bukkit.broadcastMessage(ChatColor.GOLD +"[游戏]恭喜玩家 "+winner+" 完成了游戏任务!游戏结束!");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if(!Objects.equals(p.getName(), winner)) {
                    GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(p);
                    gamePlayer.setPlayerState(GPlayerState.DEATH);
                    p.getInventory().clear();
                    p.setGameMode(GameMode.SPECTATOR);
                }
            }
        }
    }
}
