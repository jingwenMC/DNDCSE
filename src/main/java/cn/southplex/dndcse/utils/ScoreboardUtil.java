package cn.southplex.dndcse.utils;

import cn.southplex.dndcse.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreboardUtil {
    private static Scoreboard getEmptyScoreBoard() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective ob = board.registerNewObjective("sidebar", "dummy");
        ob.setDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "不要做挑战(公测)");
        ob.setDisplaySlot(DisplaySlot.SIDEBAR);
        return board;
    }
    public static void updateOneScoreboard(GamePlayer gp,int alive) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String str = sdf.format(d);
        Scoreboard sb = getEmptyScoreBoard();
        Objective obj = sb.getObjective(DisplaySlot.SIDEBAR);
        if(main.getInstance().getGameManager().getGameState()==GameState.GAMING||main.getInstance().getGameManager().getGameState()==GameState.END) {
            Score s = obj.getScore(ChatColor.GRAY+str+" "+ChatColor.GRAY.toString()+main.getInstance().getConfig().getString("server"));
            s.setScore(11);
            s = obj.getScore(ChatColor.DARK_GRAY.toString()+"   ");
            s.setScore(10);
            s = obj.getScore("地图: "+ChatColor.AQUA+main.getInstance().getConfig().getString("map"));
            s.setScore(9);
            s = obj.getScore("   ");
            s.setScore(8);
            s = obj.getScore("存活玩家: " + ChatColor.YELLOW + alive);
            s.setScore(7);
            s = obj.getScore("倒计时: " + ChatColor.YELLOW + main.getInstance().getCt());
            s.setScore(6);
            s = obj.getScore("游戏任务: " + ChatColor.YELLOW + main.getInstance().getGameManager().getCurrentMission().getName());
            s.setScore(5);
            s = obj.getScore(ChatColor.RESET.toString() + "   ");
            s.setScore(4);
            s = obj.getScore("当前状态: " + ChatColor.YELLOW + main.getInstance().getGameManager().getGameState().getValue());
            s.setScore(3);
            s = obj.getScore(ChatColor.RESET.toString() + "   ");
            s.setScore(2);
            s = obj.getScore(ChatColor.AQUA + ChatColor.BOLD.toString() + "www.southplex.cn");
            s.setScore(1);
        } else {
            Score s = obj.getScore(ChatColor.GRAY+str+" "+ChatColor.GRAY.toString()+main.getInstance().getConfig().getString("server"));
            s.setScore(10);
            s = obj.getScore(ChatColor.DARK_GRAY.toString()+"   ");
            s.setScore(9);
            s = obj.getScore("地图: "+ChatColor.AQUA+main.getInstance().getConfig().getString("map"));
            s.setScore(8);
            s = obj.getScore("   ");
            s.setScore(7);
            s = obj.getScore("在线玩家: " + ChatColor.YELLOW + alive + "/" + main.getPlugin(main.class).getConfig().getInt("max"));
            s.setScore(6);
            s = obj.getScore("倒计时: " + ChatColor.YELLOW + main.getInstance().getCt());
            s.setScore(5);
            s = obj.getScore(ChatColor.BOLD.toString() + "   ");
            s.setScore(4);
            s = obj.getScore("当前状态: " + ChatColor.YELLOW + main.getInstance().getGameManager().getGameState().getValue());
            s.setScore(3);
            s = obj.getScore(ChatColor.RESET.toString() + "   ");
            s.setScore(2);
            s = obj.getScore(ChatColor.AQUA + ChatColor.BOLD.toString() + "www.southplex.cn");
            s.setScore(1);
        }
        gp.getPlayer().setScoreboard(sb);
    }
}