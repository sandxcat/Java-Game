package javanetworking.UIComponents;

import java.io.IOException;
import java.util.ArrayList;
import javanetworking.GameHandler;
import javanetworking.du;
import javanetworking.Server;
import javanetworking.Client;

public class UIHandler {
    private static GameHandler gh;
    public String host = "Enter Host IP Address Here";
    private ArrayList<Button> buttons = new ArrayList<Button>();

    public UIHandler(GameHandler _gh) {
        gh = _gh;
        generateBtns();
    }

    private void generateBtns() {
        // Menu buttons
        Button startBtn = new Button("startBtn", Button.BtnType.Main, 677, 315, GameHandler.GameState.Menu,"");
        Button optionsBtn = new Button("optionsBtn", Button.BtnType.Main, 677, 500, GameHandler.GameState.Menu,"");
        Button exitBtn = new Button("exitBtn", Button.BtnType.Main, 677, 685, GameHandler.GameState.Menu,"");
        buttons.add(startBtn);
        buttons.add(optionsBtn);
        buttons.add(exitBtn);
        // Connect buttons
        Button ipTextBtn = new Button("ipTextBtn", Button.BtnType.Main, 677, 315, GameHandler.GameState.Connect,"");
        Button connectBtn = new Button("connectBtn", Button.BtnType.Main, 677, 560, GameHandler.GameState.Connect,"");
        Button hostBtn = new Button("hostBtn", Button.BtnType.Main, 677, 810, GameHandler.GameState.Connect,"");
        Button menuBtn = new Button("menuBtn", Button.BtnType.Small, 887, 992, GameHandler.GameState.Connect,"");
        Button cancelHostBtn = new Button("cancelHostBtn", Button.BtnType.Small, 1250, 810, GameHandler.GameState.Connect,"");
        Button clientConnectCntDwnBtn = new Button("clientConnectCntDwnBtn", Button.BtnType.Main, 1920/2, 1080/2, GameHandler.GameState.Connect,"");
        buttons.add(ipTextBtn);
        buttons.add(hostBtn);
        buttons.add(connectBtn);
        buttons.add(menuBtn);
        buttons.add(cancelHostBtn);
        buttons.add(clientConnectCntDwnBtn);
    }

    public void isButton(int[] mPos) {
        String hitButtonName = "";
        for (Button btnObj : buttons) {
            if (btnObj.uiState == gh.gameState && btnObj.buttonHitbox(mPos)) 
                    hitButtonName = btnObj.name;
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
                    Server.serverThread = new Server();
                    Server.serverThread.start();
                    Button.setFocus("cancelHostBtn");
                    break;
                case "connectBtn":
                    Client.clientThread = new Client();
                    Client.clientThread.start();
                    break;
                case "menuBtn":
                    gh.gameState = GameHandler.GameState.Menu;
                    break;
                case "cancelHostBtn":
                    Button.setFocus("");
                    //Server.serverThread.stop();
                    Server.stopServer();
                    Server.isHosting = false;
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

    public int[] getButtonPosition(String s){
        for(var btn : buttons){
            if(btn.name == s){
                return btn.position;
            }
        }
        return null;
    }
}
