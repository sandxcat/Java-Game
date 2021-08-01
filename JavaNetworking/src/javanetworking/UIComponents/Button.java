package javanetworking.UIComponents;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import javanetworking.GameHandler.GameState;
import javanetworking.du;
import javanetworking.JavaNetworking;

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

    public Rectangle2D setText(String s){
        text = s;
        JavaNetworking.g.setFont(new Font("Constantia", Font.ROMAN_BASELINE, 20));
        Rectangle2D tDim = JavaNetworking.g.getFontMetrics().getStringBounds(text, JavaNetworking.g);
        int[] tDimA = {(int)tDim.getWidth(),(int)tDim.getHeight()};
        int[] centerPosition = {(position[du.x]+(size[du.x]/2)),(position[du.y]+(int)(size[du.y]/1.3))};
        textPos = UIHandler.centerText(centerPosition, tDimA);
        du.log("CENTER_POS[" + centerPosition[du.x] + "," + centerPosition[du.y] + "]");
        du.log("TEXT POS:[" + textPos[du.x] + "," + textPos[du.y] + "]" + "TEXT_DIMENSIONS ARRAY: [" + tDimA[du.x] + "," + tDimA[du.y] + "]");

        return tDim;
    }

    Button(String _buttonName, BtnType _buttonType, int xPos, int yPos, GameState _uiState,String _buttonText) {
        name = _buttonName;
        type = _buttonType;
        position = new int[] { xPos, yPos };
        if (type == BtnType.Main)
            size = mainBtn();
        else if (type == BtnType.Small)
            size = smallBtn();

        if(_buttonText!=""){
           setText(_buttonText);
        }
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
