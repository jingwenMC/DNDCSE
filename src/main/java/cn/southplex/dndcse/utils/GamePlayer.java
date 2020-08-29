package cn.southplex.dndcse.utils;

import cn.southplex.dndcse.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Random;

public class GamePlayer {
    private Player player;
    private GPlayerState playerState;
    private Topics topic;
    NMSUtil nmsUtil = new NMSHandler();
    private int idle = 60;
    private Random r = new Random(System.currentTimeMillis());
    public GamePlayer(Player p){
        player = p;
    }
    public Player getPlayer()
    {
        return this.player;
    }
    public GPlayerState getPlayerState()
    {
        return this.playerState;
    }
    public Topics getTopic() {
        return this.topic;
    }
    public void failTask()
    {
        Boolean flag = false;
        if(getPlayerState()==GPlayerState.DEATH)return;
        if(player.getHealth()>2.0) {
            player.setHealth(player.getHealth() - 2.0);
            nmsUtil.sendTitle(player, 0, 60, 0, ChatColor.RED + "任务失败", ChatColor.WHITE + "你的任务是: " + topic.getValue());
            flag=true;
        }
        Bukkit.broadcastMessage(ChatColor.AQUA+"[游戏] "+player.getName()+" 失败了,他的上一个任务:"+topic.getValue());
        if(flag)newTask();
        else
        {
            Bukkit.broadcastMessage(ChatColor.RED+"[游戏]玩家 "+player.getDisplayName()+" 死了!");
            //nmsUtil.respawnPlayer(playerDeathEvent.getEntity());
            GamePlayer gamePlayer = main.getInstance().getPlayerManager().getGamePlayer(player);
            gamePlayer.setPlayerState(GPlayerState.DEATH);
            player.getInventory().clear();
            player.setGameMode(GameMode.SPECTATOR);
            nmsUtil.sendTitle(player,0,60,0,ChatColor.RED+"你失败了",ChatColor.WHITE+"你现在是一名旁观者.");
        }
    }
    public void newTask()
    {
        //r ;
        r.setSeed(System.currentTimeMillis()+player.getEntityId()+main.getInstance().getRmb());
        int ran1 = r.nextInt(Topics.values().length);
        Topics newValue = Topics.values()[(int) ((ran1+System.currentTimeMillis()-114514+player.getEntityId()+main.getInstance().getRmb()-1919810)%Topics.values().length)];
        main.getInstance().setRmb(main.getInstance().getRmb()+1);
        topic = newValue;
    }

    public void setPlayerState(GPlayerState playerState2) {
        if(playerState2 == GPlayerState.ALIVE)playerState=GPlayerState.ALIVE;
        if(playerState2 == GPlayerState.WAIT)playerState=GPlayerState.WAIT;
        if(playerState2 == GPlayerState.DEATH)playerState=GPlayerState.DEATH;
    }
    public void showToAll(){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.hidePlayer(this.player);
            p.showPlayer(this.player);
        }
    }
    public int getIdle() {
        return idle;
    }
    public void setIdle(int idle) {
        this.idle = idle;
    }
}
