package javanetworking;

public class GameHandler {
    static public int framerate = 144;

    static public enum GameState {
        Menu, Connect, Game, Pause
    }

    private GameState gameState;

    public GameHandler() {
        gameState = GameState.Menu;
    }

    public String HelloWorld() {
        return "Hello World";

}
