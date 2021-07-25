package javanetworking.UIComponents;

import java.util.ArrayList;

import javanetworking.ConnectionHandler;
import javanetworking.GameHandler;
import javanetworking.du;

public class UIHandler {
    private static GameHandler gh;
    public static ConnectionHandler connectH;
    public String host = "Enter Host IP Address Here";
    private static ArrayList<Button> buttons = new ArrayList<Button>();

    public UIHandler(GameHandler _gh, ConnectionHandler ch) {
        gh = _gh;
        connectH = ch;
        generateBtns();
    }

    private void generateBtns() {
        // Menu buttons
        Button startBtn = new Button("startBtn", Button.BtnType.Main, 225, 280, GameHandler.GameState.Menu);
        Button optionsBtn = new Button("optionsBtn", Button.BtnType.Main, 225, 390, GameHandler.GameState.Menu);
        Button exitBtn = new Button("exitBtn", Button.BtnType.Main, 225, 500, GameHandler.GameState.Menu);
        buttons.add(startBtn);
        buttons.add(optionsBtn);
        buttons.add(exitBtn);
        // Connect buttons
        Button ipTextBtn = new Button("ipTextBtn", Button.BtnType.Main, 225, 280, GameHandler.GameState.Connect);
        Button connectBtn = new Button("connectBtn", Button.BtnType.Main, 225, 443, GameHandler.GameState.Connect);
        Button hostBtn = new Button("hostBtn", Button.BtnType.Main, 225, 610, GameHandler.GameState.Connect);
        buttons.add(ipTextBtn);
        buttons.add(hostBtn);
        buttons.add(connectBtn);
    }

    public void isButton(int[] mPos) {
        String hitButtonName = "";
        for (Button btnObj : buttons) {
            if (btnObj.uiState == gh.gameState) {
                if (btnObj.buttonHitbox(mPos))
                    hitButtonName = btnObj.buttonName;
            }
        }

        if (gh.gameState == GameHandler.GameState.Menu) {
            switch (hitButtonName) {
                case "startBtn":
                    gh.gameState = GameHandler.GameState.Connect;
                    break;
                case "optionsBtn":
                    du.log("Options button pressed");
                    break;
                case "exitBtn":
                    System.exit(0);
                    break;
            }
        } else if (gh.gameState == GameHandler.GameState.Connect) {

            switch (hitButtonName) {
                case "ipTextBtn":
                    host = "";
                    break;
                case "hostBtn":
                    connectH.connect("Host", host);
                    break;
                case "connectBtn":
                    connectH.connect("Connect", host);
                    break;
            }

        } else if (gh.gameState == GameHandler.GameState.Pause) {

        }

    }

    public static int[] centerText(int[] position, int[] dimensions) {
        int[] returnPos = new int[] { 0, 0 };
        returnPos[du.x] = position[du.x] - dimensions[du.x] / 2;
        returnPos[du.y] = position[du.y] - dimensions[du.y] / 2;
        return returnPos;
    }
}
