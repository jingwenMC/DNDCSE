package cn.southplex.dndcse;

import cn.southplex.dndcse.commands.CMDq;
import cn.southplex.dndcse.listener.PlayerListener;
import cn.southplex.dndcse.listener.PlayerServerListener;
import cn.southplex.dndcse.managers.BungeeManager;
import cn.southplex.dndcse.managers.GameManager;
import cn.southplex.dndcse.managers.PlayerManager;
import cn.southplex.dndcse.runnable.GameLoop;
import cn.southplex.dndcse.runnable.GameLoopSec;
import cn.southplex.dndcse.utils.GameState;
import cn.southplex.dndcse.utils.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import cn.southplex.dndcse.commands.dndcse;
import org.bukkit.scheduler.BukkitTask;

public final class DNDCSE extends JavaPlugin {
    private static DNDCSE instance;
    private PlayerManager playerManager;
    private GameManager gameManager;
    private BungeeManager bungeeManager;
    private String WorldName = getConfig().getString("world");
    private String WorldName2 = getConfig().getString("world_spawn");
    public static final String VERSION = "2.1.2-AC";
    private WorldUtil worldUtil;
    private int rmb = 0;
    //public Location lobbyLocation = null;
    //public Location spawnLocation = null;
    private int ct=0;
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info(ChatColor.AQUA+"[DNDCSE-R]正在加载插件...");
        instance = this;
        Bukkit.getLogger().info(ChatColor.AQUA+"[DNDCSE-R]正在加载:配置文件...");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        setCt(DNDCSE.getPlugin(DNDCSE.class).getConfig().getInt("wait_time"));
        worldUtil = new WorldUtil();
        worldUtil.loadWorld(WorldName);
        worldUtil.loadWorld(WorldName2);
        Bukkit.getLogger().info(ChatColor.AQUA+"[DNDCSE-R]正在加载:指令...");
        getCommand("dndcse").setExecutor(new dndcse());
        getCommand("q").setExecutor(new CMDq());
        Bukkit.getLogger().info(ChatColor.AQUA+"[DNDCSE-R]正在加载:管理器...");
        playerManager = new PlayerManager();
        gameManager = new GameManager();
        gameManager.setGameState(GameState.WAIT);
        bungeeManager = new BungeeManager();
        Bukkit.getWorld(DNDCSE.getInstance().getWorldName2()).setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld(DNDCSE.getInstance().getWorldName()).setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getLogger().info(ChatColor.AQUA+"[DNDCSE-R]正在加载:游戏循环...");
        BukkitTask bukkitTask = new GameLoop().runTaskTimer(this,0,2);
        BukkitTask bukkitTask2 = new GameLoopSec().runTaskTimer(this,0,20);
        Bukkit.getLogger().info(ChatColor.AQUA+"[DNDCSE-R]正在加载:游戏事件...");
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerServerListener(),this);
        getServer().getPluginManager().registerEvents(new BungeeManager(),this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getLogger().info("\n ____  _      ____  ____  ____  _____      ____ \n" +
                "/  _ \\/ \\  /|/  _ \\/   _\\/ ___\\/  __/     /  __\\\n" +
                "| | \\|| |\\ ||| | \\||  /  |    \\|  \\ _____ |  \\/|\n" +
                "| |_/|| | \\||| |_/||  \\__\\___ ||  /_\\____\\|    /\n" +
                "\\____/\\_/  \\|\\____/\\____/\\____/\\____\\     \\_/\\_\\\n" +
                "                                   DNDCSE-R version "+VERSION+" by jingwenMC\n"+
                "                                   Aria Craft 服务器定制版");
        Bukkit.getLogger().info(ChatColor.AQUA+"[DNDCSE-R]加载完成!");
        gameManager.setGameState(GameState.WAIT);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info(ChatColor.AQUA+"[DNDCSE-R]插件关闭!再见!");
    }
    public static DNDCSE getInstance(){return instance;}
    public PlayerManager getPlayerManager() {
        return playerManager;
    }
    public GameManager getGameManager() {
        return gameManager;
    }
    public BungeeManager getBungeeManager() {
        return bungeeManager;
    }
    public Location getSpawnLocation() {
        return new Location(Bukkit.getWorld(WorldName2),getConfig().getDouble("spawn.x"),getConfig().getDouble("spawn.y"),getConfig().getDouble("spawn.z"));
    }
    public Location getLobbyLocation() {
        return new Location(Bukkit.getWorld(WorldName),getConfig().getDouble("lobby.x"),getConfig().getDouble("lobby.y"),getConfig().getDouble("lobby.z"));
    }
    public String getWorldName2() {
        return WorldName2;
    }

    public String getWorldName() {
        return WorldName;
    }

    /**
     * Countdown
     * @return countdown second
     */
    public int getCt() {
        return ct;
    }
    public void setCt(int ct) {
        this.ct = ct;
    }
    public WorldUtil getWorldUtil() {
        return worldUtil;
    }

    public int getRmb() {
        return rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }
}
