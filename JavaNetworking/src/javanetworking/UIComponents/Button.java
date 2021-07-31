package javanetworking.UIComponents;

import javanetworking.GameHandler.GameState;
import javanetworking.du;

public class Button {

    static public enum BtnType {
        Main, Small
    }
    private static String focusBtn = "";
    public String name;
    public String text;
    public int[] textPos = new int[2];
    public BtnType type;
    public int[] position = new int[2];
    public int[] size = new int[2];
    public GameState uiState;

    static private int[] mainBtn() {
        int xsize = 565;
        int ysize = 80;
        return new int[] { xsize, ysize };
    }

    static private int[] smallBtn() {
        int xsize = 145;
        int ysize = 75;
        return new 
        int[] { xsize, ysize };
    }

    static public void setFocus(String s){
        focusBtn = s;
    } 

    Button(String _buttonName, BtnType _buttonType, int xPos, int yPos, GameState _uiState,String _buttonText) {
        name = _buttonName;
        type = _buttonType;
        text = _buttonText;
        position = new int[] { xPos, yPos };
        if (type == BtnType.Main)
            size = mainBtn();
        else if (type == BtnType.Small)
            size = smallBtn();

        uiState = _uiState;

    }

    Boolean buttonHitbox(int[] mousePos) {
        if(name != focusBtn && focusBtn != ""){
            return false;
        }
        if (mousePos[du.x] >= position[du.x] && mousePos[du.x] <= (position[du.x] + size[du.x])
                && mousePos[du.y] >= position[du.y] && mousePos[du.y] <= (position[du.y] + size[du.y])) {
            return true;
        }
        return false;
    }

}
