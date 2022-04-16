package cn.southplex.dndcse.commands;

import cn.southplex.dndcse.DNDCSE;
import cn.southplex.dndcse.utils.GPlayerState;
import cn.southplex.dndcse.utils.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CMDq implements CommandExecutor {

    private void sendmsg(CommandSender sender,String str)
    {
        //In Stance Of
        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            player.sendMessage(ChatColor.AQUA+"[游戏]"+str);
        }
        else
        {
            System.out.println(ChatColor.AQUA +"[游戏]"+str);
        }
    }
    private boolean error(CommandSender sender)
    {
        sendmsg(sender,"输入的指令有误");
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length!=1) {
            sendmsg(sender,"请使用/q <被查询玩家>查询另一名玩家的挑战");
            return error(sender);
        }
        Player target = Bukkit.getPlayerExact(args[0]);

        if(target != null && !Objects.equals(sender.getName(), target.getName()))
        {
            GamePlayer gp = DNDCSE.getInstance().getPlayerManager().getGamePlayer(target);
            if(gp.getPlayerState().equals(GPlayerState.DEATH)) {
                sendmsg(sender,"不能查询旁观者!");
                return error(sender);
            }
            sendmsg(sender, "被查询人的词语:"+gp.getTopic().getValue());
            return true;
        }
        else return error(sender);
    }
}
