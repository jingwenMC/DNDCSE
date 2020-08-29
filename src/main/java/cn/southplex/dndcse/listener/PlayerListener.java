package cn.southplex.dndcse.listener;

import cn.southplex.dndcse.utils.*;
import cn.southplex.dndcse.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {
    NMSUtil nmsUtil = new NMSHandler();
    @EventHandler
    public void onDamage(EntityDamageEvent entityDamageEvent)
    {
        if(main.getInstance().getGameManager().getGameState()!= GameState.GAMING)entityDamageEvent.setCancelled(true);
        if(entityDamageEvent.getEntityType()== EntityType.PLAYER)
        {
            Player player = Bukkit.getPlayer(entityDamageEvent.getEntity().getUniqueId());
            GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(player);
            entityDamageEvent.setCancelled(true);
            if(gamePlayer.getTopic()==Topics.DAMAGE&&entityDamageEvent.getDamage()>=2.5)
            {
                gamePlayer.failTask();
            }
        }
    }
    @EventHandler
    public void onChat(PlayerChatEvent playerChatEvent)
    {
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(playerChatEvent.getPlayer());
        if(gamePlayer.getTopic()==Topics.CHAT)
        {
            gamePlayer.failTask();
            //gamePlayer.newTask();
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent)
    {
        playerDeathEvent.setDeathMessage(ChatColor.RED+"玩家 "+playerDeathEvent.getEntity().getDisplayName()+" 死了!");
        nmsUtil.respawnPlayer(playerDeathEvent.getEntity());
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(playerDeathEvent.getEntity());
        gamePlayer.setPlayerState(GPlayerState.DEATH);
        playerDeathEvent.getEntity().getInventory().clear();
        playerDeathEvent.getEntity().setGameMode(GameMode.SPECTATOR);
        nmsUtil.sendTitle(playerDeathEvent.getEntity(),0,60,0,ChatColor.RED+"你死了",ChatColor.WHITE+"你现在是一名旁观者.");
    }
    @EventHandler
    public void onPick(PlayerPickupItemEvent playerPickupItemEvent)
    {
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(playerPickupItemEvent.getPlayer());
        if(playerPickupItemEvent.getItem().getItemStack().getType()==Material.SEEDS && gamePlayer.getTopic()==Topics.PICK_SEED)
        {
            gamePlayer.failTask();
        }
        if(playerPickupItemEvent.getItem().getItemStack().getType()==Material.DIRT && gamePlayer.getTopic()==Topics.PICK_DIRT)
            gamePlayer.failTask();
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent playerDropItemEvent)
    {
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(playerDropItemEvent.getPlayer());
        if(playerDropItemEvent.getItemDrop().getItemStack().getType()==Material.SEEDS && gamePlayer.getTopic()==Topics.DROP_SEED)
        {
            gamePlayer.failTask();
        }
        if(playerDropItemEvent.getItemDrop().getItemStack().getType()==Material.DIRT && gamePlayer.getTopic()==Topics.DROP_DIRT)
            gamePlayer.failTask();
    }
    @EventHandler
    public void onCraft(CraftItemEvent craftItemEvent)
    {
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer((Player)craftItemEvent.getWhoClicked());
        if(craftItemEvent.getRecipe().getResult().getType()==Material.STICK && gamePlayer.getTopic()==Topics.CRAFT_STICK)
        {
            gamePlayer.failTask();
        }
    }
    @EventHandler
    public void onSwim(PlayerMoveEvent playerMoveEvent)
    {
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(playerMoveEvent.getPlayer());
        if((playerMoveEvent.getFrom().getBlock().getType()==Material.WATER||playerMoveEvent.getTo().getBlock().getType()==Material.WATER)&&gamePlayer.getTopic()==Topics.SWIM)
        {
            gamePlayer.failTask();
        }
    }
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent playerToggleSneakEvent)
    {
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(playerToggleSneakEvent.getPlayer());
        if(playerToggleSneakEvent.isSneaking()&&gamePlayer.getTopic()==Topics.SNEAK)
            gamePlayer.failTask();
    }
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        if(event.getDamager().getType()==EntityType.PLAYER)
        {
            Player player = Bukkit.getPlayer(event.getDamager().getUniqueId());
            GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(player);
            if(main.getInstance().getGameManager().getGameState()== GameState.GAMING)
                if(gamePlayer.getTopic()==Topics.DAMAGE_PLAYER)
                    if(event.getEntityType()==EntityType.PLAYER)
                        gamePlayer.failTask();
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent blockBreakEvent)
    {
        Player player = blockBreakEvent.getPlayer();
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(player);
        if(gamePlayer.getTopic()==Topics.BREAK_DIRT_GRASS_BLOCK&&(blockBreakEvent.getBlock().getType()==Material.DIRT||blockBreakEvent.getBlock().getType()==Material.GRASS))
            gamePlayer.failTask();
    }
}
