package cn.southplex.dndcse.utils;

public enum GameState {
    WAIT("等待玩家"),READY("即将开始"),GAMING("游戏中"),END("结束");
    private String value;
    GameState(String value) {
            this.value = value;
    }
    public String getValue() {
            return value;
        }
}
