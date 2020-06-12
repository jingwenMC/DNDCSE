package cn.southplex.dndcse.managers;

import cn.southplex.dndcse.utils.GameState;

public class GameManager {
    private GameState gameState = null;
    public void setGameState(GameState gameState2) {
        if(gameState2==GameState.WAIT)gameState=GameState.WAIT;
        if(gameState2==GameState.READY)gameState=GameState.READY;
        if(gameState2==GameState.GAMING)gameState=GameState.GAMING;
        if(gameState2==GameState.END)gameState=GameState.END;
    }
    public GameState getGameState() {
        return gameState;
    }
}
