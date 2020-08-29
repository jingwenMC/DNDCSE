package cn.southplex.dndcse.commands;

import cn.southplex.dndcse.main;
import cn.southplex.dndcse.utils.GamePlayer;
import cn.southplex.dndcse.utils.Topics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class dndcse implements CommandExecutor {
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
        sendmsg(sender,"输入的指令有误/权限不足");
        return false;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==0)return error(sender);
        if(args[0].equalsIgnoreCase("q"))
        {
            Player target = Bukkit.getPlayerExact(args[1]);
            if(args.length==2&&target!=null&&sender.getName()!=target.getName())
            {
                GamePlayer gp = main.getInstance().getPlayerManager().getGamePlayer(target);
                sendmsg(sender, "被查询人的词语:"+gp.getTopic().getValue());
                return true;
            }
            else return error(sender);
        }
        if(args[0].equalsIgnoreCase("reset"))
        {
            if(args.length==2&&sender.isOp())
            {
                Player target = Bukkit.getPlayerExact(args[1]);
                if(target != null) {
                    main.getInstance().getPlayerManager().getGamePlayer(target).newTask();
                    sendmsg(sender, "已成功重置");
                    return true;
                }
                else return error(sender);
            }
            else return error(sender);
        }
        if(args[0].equalsIgnoreCase("get"))
        {
            if(args.length==2&&sender.isOp())
            {
                Player target = Bukkit.getPlayerExact(args[1]);
                GamePlayer gp = main.getInstance().getPlayerManager().getGamePlayer(target);
                if(target != null) {
                    sendmsg(sender, "词语:"+gp.getTopic().getValue());
                    return true;
                }
                else return error(sender);
            }
            else return error(sender);
        }
        if(args[0].equalsIgnoreCase("help"))
        {
            if(sender.isOp())
            {
                sendmsg(sender,"指令帮助:");
                sendmsg(sender,"/dndcs reset <player> 重置一名玩家的词语");
                sendmsg(sender,"/dndcs get <player> 获得一名玩家的词语");
                return true;
            }
            else {
                sendmsg(sender,"指令帮助:");
                sendmsg(sender,"/dndc q <player> 获得一名玩家的词语(不能是自己)");
                return true;
            }
        }
        return error(sender);
    }
}
