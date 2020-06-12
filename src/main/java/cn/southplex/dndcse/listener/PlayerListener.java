package cn.southplex.dndcse.listener;

import cn.southplex.dndcse.utils.*;
import cn.southplex.dndcse.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    NMSUtil nmsUtil = new NMSHandler();
    @EventHandler
    public void onCraft(CraftItemEvent craftItemEvent)
    {
        Player player = Bukkit.getPlayer(craftItemEvent.getWhoClicked().getUniqueId());
        GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(player);
        if(gamePlayer.getTopic()== Topics.CRAFT) {
            gamePlayer.failTask();
            //gamePlayer.newTask();
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent entityDamageEvent)
    {
        if(entityDamageEvent.getEntityType()== EntityType.PLAYER)
        {
            Player player = Bukkit.getPlayer(entityDamageEvent.getEntity().getUniqueId());
            GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(player);
            entityDamageEvent.setCancelled(true);
            if(gamePlayer.getTopic()==Topics.DAMAGE&&entityDamageEvent.getDamage()>=2.5)
            {
                gamePlayer.failTask();
                //gamePlayer.newTask();
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
    }
}
