package cn.southplex.dndcse.utils;

import org.bukkit.Material;

public enum Missions {
    GET_DIAMOND("收集一颗钻石",Material.DIAMOND),
    CRAFT_IRON_BLOCK("合成一个铁块",Material.IRON_BLOCK),
    GET_WATER_BUCKET("获得一桶水",Material.WATER_BUCKET),
    GET_LAVA_BUCKET("获得一桶岩浆",Material.LAVA_BUCKET);
    public final String name;
    public final Material material;
    Missions(String value,Material material) {
        this.name = value;
        this.material = material;
    }
    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }
}
