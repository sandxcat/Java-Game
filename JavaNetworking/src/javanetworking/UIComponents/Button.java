package javanetworking.UIComponents;

import javanetworking.GameHandler.GameState;
import javanetworking.du;

public class Button {

    static public enum BtnType {
        Main, Small
    }

    public String buttonName;
    public BtnType buttonType;
    public int[] position = new int[2];
    public int[] size = new int[2];
    public GameState uiState;

    static private int[] mainBtn() {
        int xsize = 350;
        int ysize = 50;
        return new int[] { xsize, ysize };
    }

    static private int[] smallBtn() {
        int xsize = 90;
        int ysize = 50;
        return new int[] { xsize, ysize };
    }

    Button(String _buttonName, BtnType _buttonType, int xPos, int yPos, GameState _uiState) {
        buttonName = _buttonName;
        buttonType = _buttonType;
        position = new int[] { xPos, yPos };
        if (buttonType == BtnType.Main)
            size = mainBtn();
        else if (buttonType == BtnType.Small)
            size = smallBtn();

        uiState = _uiState;

    }

    Boolean buttonHitbox(int[] mousePos) {
        if (mousePos[du.x] >= position[du.x] && mousePos[du.x] <= (position[du.x] + size[du.x])
                && mousePos[du.y] >= position[du.y] && mousePos[du.y] <= (position[du.y] + size[du.y])) {
            return true;
        }
        return false;
    }

}
