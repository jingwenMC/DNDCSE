package cn.southplex.dndcse.utils;

public enum Topics {
    DAMAGE("受伤(>2)"),CHAT("聊天"),CRAFT("合成"),PICK_SEED("捡起种子");
    private String value;
    Topics(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
