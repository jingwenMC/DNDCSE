package cn.southplex.dndcse.utils;

public enum GPlayerState {
    WAIT("等待"),ALIVE("存活"),DEATH("死亡");
    private String value;
    GPlayerState(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
