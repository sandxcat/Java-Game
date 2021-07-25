package javanetworking.UIComponents;

import javanetworking.GameHandler.GameState;

public class Button {
    private final static int x = 0;
    private final static int y = 1;

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
        int xsize = 0;
        int ysize = 0;
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
        if (mousePos[x] >= position[x] && mousePos[x] <= (position[x] + size[x]) && mousePos[y] >= position[y]
                && mousePos[y] <= (position[y] + size[y])) {
            return true;
        }
        return false;
    }

}
