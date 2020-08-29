package cn.southplex.dndcse.utils;

public enum Topics {
    DAMAGE("受伤(>2)"),CHAT("聊天"),PICK_SEED("捡起种子"),DROP_SEED("丢弃种子")
    ,CRAFT_STICK("合成木棍"),SWIM("游泳"),SNEAK("蹲下"),PICK_DIRT("捡起泥土"),
    DAMAGE_PLAYER("伤害玩家"),DROP_DIRT("丢弃泥土"),BREAK_DIRT_GRASS_BLOCK("破坏泥土/草方块");
    private String value;
    Topics(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
