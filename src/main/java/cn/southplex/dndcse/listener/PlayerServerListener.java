package cn.southplex.dndcse.listener;

import cn.southplex.dndcse.main;
import cn.southplex.dndcse.utils.GPlayerState;
import cn.southplex.dndcse.utils.GamePlayer;
import cn.southplex.dndcse.utils.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.*;

public class PlayerServerListener implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent playerLoginEvent)
    {
        if(main.getInstance().getGameManager().getGameState()==null)
        {
            playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER,"游戏仍在准备...");
        }
        if(main.getInstance().getGameManager().getGameState()==GameState.WAIT||main.getInstance().getGameManager().getGameState()==GameState.READY)
        {
            if(Bukkit.getOnlinePlayers().size()>main.getPlugin(main.class).getConfig().getInt("max"))playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_FULL,"服务器已满!");
        }
        if(main.getInstance().getGameManager().getGameState()==GameState.END)
        {
            playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER,"游戏结束了!");
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent)
    {
        playerJoinEvent.getPlayer().setLevel(0);
        playerJoinEvent.getPlayer().setGameMode(GameMode.ADVENTURE);
        playerJoinEvent.getPlayer().setMaxHealth(20);
        playerJoinEvent.getPlayer().setHealth(20);
        playerJoinEvent.getPlayer().getInventory().clear();
        playerJoinEvent.getPlayer().setFoodLevel(20);
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().createGamePlayer(playerJoinEvent.getPlayer());
        if(main.getInstance().getGameManager().getGameState()== GameState.GAMING)
        {
            gamePlayer.setPlayerState(GPlayerState.DEATH);
            playerJoinEvent.getPlayer().setGameMode(GameMode.SPECTATOR);
            playerJoinEvent.getPlayer().teleport(main.getInstance().getSpawnLocation());
            playerJoinEvent.getPlayer().sendMessage(ChatColor.YELLOW+"游戏已经开始,你正在以旁观者加入!");
            playerJoinEvent.setJoinMessage("");
        }
        if(main.getInstance().getGameManager().getGameState()==GameState.WAIT||main.getInstance().getGameManager().getGameState()==GameState.READY)
        {
            gamePlayer.setPlayerState(GPlayerState.WAIT);
            playerJoinEvent.getPlayer().teleport(main.getInstance().getLobbyLocation());
            playerJoinEvent.setJoinMessage(ChatColor.YELLOW+"[加入] "+playerJoinEvent.getPlayer().getName()+" 加入了游戏"+ChatColor.AQUA+"("+Bukkit.getOnlinePlayers().size()+"/"+main.getPlugin(main.class).getConfig().getInt("max")+")");
        }
        gamePlayer.showToAll();

    }
    @EventHandler
    public void onBreak(BlockBreakEvent blockBreakEvent)
    {
        if(main.getInstance().getGameManager().getGameState()!= GameState.GAMING)blockBreakEvent.setCancelled(true);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent)
    {
        if(main.getInstance().getGameManager().getGameState()!= GameState.GAMING)playerInteractEvent.setCancelled(true);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent)
    {
        main.getInstance().getPlayerManager().getGamePlayer(playerMoveEvent.getPlayer()).setIdle(60);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent)
    {
        if(main.getInstance().getGameManager().getGameState()==GameState.WAIT||main.getInstance().getGameManager().getGameState()==GameState.READY)
        playerQuitEvent.setQuitMessage(ChatColor.RED+"[离开] "+playerQuitEvent.getPlayer().getName()+" 离开了游戏"+ChatColor.AQUA+"("+(main.getInstance().getPlayerManager().getPlayerSize()-1)+"/"+main.getPlugin(main.class).getConfig().getInt("max")+")");
        else if(main.getInstance().getPlayerManager().getGamePlayer(playerQuitEvent.getPlayer()).getPlayerState()==GPlayerState.ALIVE)playerQuitEvent.setQuitMessage(ChatColor.RED+"[离开] "+playerQuitEvent.getPlayer().getName()+" 离开了游戏"+ChatColor.AQUA);
        else playerQuitEvent.setQuitMessage("");
        main.getInstance().getPlayerManager().removeGamePlayer(playerQuitEvent.getPlayer().getName());
    }
}
