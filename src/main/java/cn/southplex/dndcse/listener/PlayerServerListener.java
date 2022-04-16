package cn.southplex.dndcse.listener;

import cn.southplex.dndcse.DNDCSE;
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
        if(DNDCSE.getInstance().getGameManager().getGameState()==null)
        {
            playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER,"游戏仍在准备...");
        }
        if(DNDCSE.getInstance().getGameManager().getGameState()==GameState.WAIT|| DNDCSE.getInstance().getGameManager().getGameState()==GameState.READY)
        {
            if(Bukkit.getOnlinePlayers().size()> DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("max"))playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_FULL,"服务器已满!");
        }
        if(DNDCSE.getInstance().getGameManager().getGameState()==GameState.END)
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
        GamePlayer gamePlayer = DNDCSE.getInstance().getPlayerManager().createGamePlayer(playerJoinEvent.getPlayer());
        if(DNDCSE.getInstance().getGameManager().getGameState()== GameState.GAMING)
        {
            gamePlayer.setPlayerState(GPlayerState.DEATH);
            playerJoinEvent.getPlayer().setGameMode(GameMode.SPECTATOR);
            playerJoinEvent.getPlayer().teleport(DNDCSE.getInstance().getSpawnLocation());
            playerJoinEvent.getPlayer().sendMessage(ChatColor.YELLOW+"游戏已经开始,你正在以旁观者加入!");
            playerJoinEvent.setJoinMessage("");
        }
        if(DNDCSE.getInstance().getGameManager().getGameState()==GameState.WAIT|| DNDCSE.getInstance().getGameManager().getGameState()==GameState.READY)
        {
            gamePlayer.setPlayerState(GPlayerState.WAIT);
            playerJoinEvent.getPlayer().teleport(DNDCSE.getInstance().getLobbyLocation());
            playerJoinEvent.setJoinMessage(ChatColor.YELLOW+"[加入] "+playerJoinEvent.getPlayer().getName()+" 加入了游戏"+ChatColor.AQUA+"("+Bukkit.getOnlinePlayers().size()+"/"+ DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("max")+")");
        }
        gamePlayer.showToAll();

    }
    @EventHandler
    public void onBreak(BlockBreakEvent blockBreakEvent)
    {
        if(DNDCSE.getInstance().getGameManager().getGameState()!= GameState.GAMING)blockBreakEvent.setCancelled(true);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent)
    {
        if(DNDCSE.getInstance().getGameManager().getGameState()!= GameState.GAMING)playerInteractEvent.setCancelled(true);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent)
    {
        DNDCSE.getInstance().getPlayerManager().getGamePlayer(playerMoveEvent.getPlayer()).setIdle(60);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent)
    {
        if(DNDCSE.getInstance().getGameManager().getGameState()==GameState.WAIT|| DNDCSE.getInstance().getGameManager().getGameState()==GameState.READY)
        playerQuitEvent.setQuitMessage(ChatColor.RED+"[离开] "+playerQuitEvent.getPlayer().getName()+" 离开了游戏"+ChatColor.AQUA+"("+(DNDCSE.getInstance().getPlayerManager().getPlayerSize()-1)+"/"+ DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("max")+")");
        else if(DNDCSE.getInstance().getPlayerManager().getGamePlayer(playerQuitEvent.getPlayer()).getPlayerState()==GPlayerState.ALIVE)playerQuitEvent.setQuitMessage(ChatColor.RED+"[离开] "+playerQuitEvent.getPlayer().getName()+" 离开了游戏"+ChatColor.AQUA);
        else playerQuitEvent.setQuitMessage("");
        DNDCSE.getInstance().getPlayerManager().removeGamePlayer(playerQuitEvent.getPlayer().getName());
    }
}
