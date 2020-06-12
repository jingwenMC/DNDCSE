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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;

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
        if(main.getInstance().getGameManager().getGameState()== GameState.GAMING)
        {
            GamePlayer gamePlayer = main.getInstance().getPlayerManager().createGamePlayer(playerJoinEvent.getPlayer());
            gamePlayer.setPlayerState(GPlayerState.DEATH);
            playerJoinEvent.getPlayer().setGameMode(GameMode.SPECTATOR);
            playerJoinEvent.getPlayer().teleport(main.getInstance().getSpawnLocation());
            playerJoinEvent.getPlayer().sendMessage(ChatColor.YELLOW+"游戏已经开始,你正在以旁观者加入!");
        }
        if(main.getInstance().getGameManager().getGameState()==GameState.WAIT||main.getInstance().getGameManager().getGameState()==GameState.READY)
        {
            GamePlayer gamePlayer = main.getInstance().getPlayerManager().createGamePlayer(playerJoinEvent.getPlayer());
            gamePlayer.setPlayerState(GPlayerState.WAIT);
            playerJoinEvent.getPlayer().teleport(main.getInstance().getLobbyLocation());
        }
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
}
