package cn.southplex.dndcse.utils;

import org.bukkit.Material;

public enum Missions {
    THREE_BEDS("合成三个床",Material.BED,3),
    CRAFT_IRON_BLOCK("合成一个铁块",Material.IRON_BLOCK,1),
    GET_WATER_BUCKET("获得一桶水",Material.WATER_BUCKET,1),
    COOK_SHEEP("烤六块羊肉",Material.COOKED_MUTTON,6),
    A_SET_OF_STONE("收集四组圆石",Material.COBBLESTONE,256),
    A_SET_OF_WOOD("收集四组木板",Material.WOOD,256),
    EIGHT_WOOL("获得八块羊毛",Material.WOOL,8);
    private final String name;
    private final Material material;
    private final int amount;
    Missions(String value,Material material,int amount) {
        this.name = value;
        this.material = material;
        this.amount = amount;
    }
    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }
}
