package javanetworking.UIComponents;

import java.util.ArrayList;
import javanetworking.GameHandler;
import javanetworking.GameHandler.GameState;
import javanetworking.du;

public class UIHandler {
    private static ArrayList<Button> buttons = new ArrayList<Button>();
    private static GameHandler gh;

    public UIHandler(GameHandler _gh) {
        gh = _gh;
        generateBtns();
    }

    private void generateBtns() {
        Button startBtn = new Button("startBtn", Button.BtnType.Main, 225, 275, GameHandler.GameState.Menu);
        Button optionsBtn = new Button("optionsBtn", Button.BtnType.Main, 225, 390, GameHandler.GameState.Menu);
        Button exitBtn = new Button("exitBtn", Button.BtnType.Main, 225, 500, GameHandler.GameState.Menu);
        buttons.add(startBtn);
        buttons.add(optionsBtn);
        buttons.add(exitBtn);

    }

    public void isButton(int[] mPos) {
        String hitButtonName = "";
        for (Button btnObj : buttons) {
            if (btnObj.uiState != gh.gameState)
                return;

            if (btnObj.buttonHitbox(mPos))
                hitButtonName = btnObj.buttonName;
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

        } else if (gh.gameState == GameHandler.GameState.Pause) {

        }

    }
}

/*
 * public static int[] numbers() { int[] arr={5,6,7,8,9}; //initializing array
 * return arr; }
 * 
 */
