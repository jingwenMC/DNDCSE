package cn.southplex.dndcse.runnable;

import cn.southplex.dndcse.DNDCSE;
import cn.southplex.dndcse.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public class GameLoopSec extends BukkitRunnable {
    NMSUtil nmsUtil = new NMSHandler();
    @Override
    public void run() {
        int i = DNDCSE.getInstance().getCt();
        //if(DNDCSE.getInstance().getGameManager().getGameState())
        if(DNDCSE.getInstance().getGameManager().getGameState()==GameState.WAIT|| DNDCSE.getInstance().getGameManager().getGameState()==GameState.READY) {
            for (Player p : Bukkit.getOnlinePlayers()) p.setLevel(i);
            for(Player p : Bukkit.getOnlinePlayers())
            {
                GamePlayer gp = DNDCSE.getInstance().getPlayerManager().getGamePlayer(p);
                ScoreboardUtil.updateOneScoreboard(gp,0);
            }
            if (Bukkit.getOnlinePlayers().size() >= DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("min")) {
                if(i == DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("wait_time"))
                    Bukkit.broadcastMessage(ChatColor.AQUA + "[游戏]开始倒计时!");
                DNDCSE.getInstance().getGameManager().setGameState(GameState.READY);
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
                        Bukkit.getWorld(DNDCSE.getInstance().getWorldName2()).setGameRuleValue("naturalRegeneration", "false");
                        p.setGameMode(GameMode.SURVIVAL);
                        p.teleport(DNDCSE.getInstance().getSpawnLocation());
                        p.getInventory().clear();
                        p.sendMessage(""+ChatColor.YELLOW+ChatColor.BOLD+"                    不要做挑战");
                        p.sendMessage(ChatColor.GRAY + "在这个游戏中，我们会被很多的规则圈包围，每当触发一个规则时就会扣一条生命值\n" +
                                ChatColor.GRAY + "当很多人与你一起竞争时，谁才能活到最后呢？在这种你死我活，勾心斗角的残酷游戏内，谁才能获得最终胜利呢？");
                        p.sendMessage(ChatColor.AQUA + "测试阶段游戏相关建议请到论坛bbs.accentry.cn进行反馈！");
                        p.sendMessage(ChatColor.GOLD + "[游戏]游戏开始!");
                        p.sendMessage(ChatColor.GOLD + "[游戏]Tip:做事需谨慎!");
                        p.sendMessage(ChatColor.GOLD + "[游戏]Tip:完成计分板显示的游戏任务同样可以获胜!");
                        p.sendMessage(ChatColor.GOLD + "[游戏]Tip:执行/q <玩家名>查询其他玩家的挑战!(不能查自己)");
                        nmsUtil.sendTitle(p, 0, 60, 20, ChatColor.RED + "游戏开始!", "争取活到最后!");
                        GamePlayer gamePlayer = DNDCSE.getInstance().getPlayerManager().getGamePlayer(p);
                        gamePlayer.setPlayerState(GPlayerState.ALIVE);
                        gamePlayer.newTask();
                        DNDCSE.getInstance().getGameManager().setGameState(GameState.GAMING);
                    }
                    i= DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("game_time")+1;
                    DNDCSE.getInstance().setCt(DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("game_time")+1);
                    DNDCSE.getInstance().getGameManager().setRandomMission();
                    DNDCSE.getInstance().getGameManager().setGameState(GameState.GAMING);
                    BukkitTask bukkitTaska = new NewTask().runTaskTimer(DNDCSE.getInstance(),2400,2400);
                }
                i--;
                DNDCSE.getInstance().setCt(i);
            } else {
                if (i != DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("wait_time")) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + "[游戏]人数不足,倒计时取消!");
                    DNDCSE.getInstance().setCt(DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("wait_time"));
                    DNDCSE.getInstance().getGameManager().setGameState(GameState.WAIT);
                }
            }
        }
        if(DNDCSE.getInstance().getGameManager().getGameState()==GameState.GAMING) {
            checkMission();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (DNDCSE.getInstance().getGameManager().getGameState() == GameState.GAMING)
                    DNDCSE.getInstance().getPlayerManager().getGamePlayer(p).setIdle(DNDCSE.getInstance().getPlayerManager().getGamePlayer(p).getIdle() - 1);
                if (DNDCSE.getInstance().getPlayerManager().getGamePlayer(p).getPlayerState() == GPlayerState.DEATH) {
                    DNDCSE.getInstance().getPlayerManager().getGamePlayer(p).setIdle(30);
                }
                if (DNDCSE.getInstance().getPlayerManager().getGamePlayer(p).getIdle() <= 0) {
                    p.sendMessage(ChatColor.AQUA + "[游戏]你因长时间未移动而失败挑战!");
                    DNDCSE.getInstance().getPlayerManager().getGamePlayer(p).failTask();
                    DNDCSE.getInstance().getPlayerManager().getGamePlayer(p).setIdle(30);
                }
            }
            if(i>=0) {
                i--;
                DNDCSE.getInstance().setCt(i);
            }
        }
    }

    private void checkMission() {
        String winner = null;
        boolean lock = false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerInventory inventory = p.getInventory();
            if(inventory.contains(DNDCSE.getInstance().getGameManager().getCurrentMission().getMaterial()
                    , DNDCSE.getInstance().getGameManager().getCurrentMission().getAmount()))
                winner = p.getName();
        }
        if(winner!=null && (!lock)) {
            Bukkit.broadcastMessage(ChatColor.GOLD +"[游戏]恭喜玩家 "+winner+" 完成了游戏任务!游戏结束!");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if(!Objects.equals(p.getName(), winner)) {
                    GamePlayer gamePlayer = DNDCSE.getInstance().getPlayerManager().getGamePlayer(p);
                    gamePlayer.setPlayerState(GPlayerState.DEATH);
                    p.getInventory().clear();
                    p.setGameMode(GameMode.SPECTATOR);
                }
            }
        }
    }
}
