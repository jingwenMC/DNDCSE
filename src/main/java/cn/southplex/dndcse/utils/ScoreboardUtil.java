package cn.southplex.dndcse.utils;

import cn.southplex.dndcse.main;
import cn.southplex.dndcse.runnable.GameLoopSec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
        Score s = obj.getScore(ChatColor.GRAY+str+" "+ChatColor.GRAY.toString()+main.getInstance().getConfig().getString("server"));
        s.setScore(9);
        s = obj.getScore(ChatColor.DARK_GRAY.toString()+"   ");
        s.setScore(8);
        s = obj.getScore("地图: "+ChatColor.AQUA+main.getInstance().getConfig().getString("map"));
        s.setScore(7);
        s = obj.getScore("   ");
        s.setScore(6);
        if(main.getInstance().getGameManager().getGameState()==GameState.GAMING||main.getInstance().getGameManager().getGameState()==GameState.END)
        s=obj.getScore("存活玩家: "+ChatColor.YELLOW+alive);
        else
            s=obj.getScore("倒计时: "+ChatColor.YELLOW+ main.getInstance().getCt());
        s.setScore(5);
        s = obj.getScore(ChatColor.BOLD.toString()+"   ");
        s.setScore(4);
        s=obj.getScore("当前状态: "+ChatColor.YELLOW+ main.getInstance().getGameManager().getGameState().getValue());
        s.setScore(3);
        s = obj.getScore(ChatColor.RESET.toString()+"   ");
        s.setScore(2);
        s = obj.getScore(ChatColor.AQUA + ChatColor.BOLD.toString()+ "www.southplex.cn");
        s.setScore(1);
        gp.getPlayer().setScoreboard(sb);
    }
}