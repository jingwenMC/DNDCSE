package cn.southplex.dndcse.managers;

import cn.southplex.dndcse.utils.GameState;
import cn.southplex.dndcse.utils.Missions;

import java.util.Random;

public class GameManager {
    private GameState gameState = null;
    private Missions currentMission = null;
    private final Random random = new Random();
    public void setGameState(GameState gameState2) {
        if(gameState2==GameState.WAIT)gameState=GameState.WAIT;
        if(gameState2==GameState.READY)gameState=GameState.READY;
        if(gameState2==GameState.GAMING)gameState=GameState.GAMING;
        if(gameState2==GameState.END)gameState=GameState.END;
    }
    public GameState getGameState() {
        return gameState;
    }

    public Missions getCurrentMission() {
        return currentMission;
    }

    public void setRandomMission() {
        this.currentMission = Missions.values()[random.nextInt(Missions.values().length)];
    }
}
