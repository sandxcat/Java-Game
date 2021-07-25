package javanetworking;

public class UIHandler {
    public static Boolean buttonHitbox(int[] mousePos, int x, int y, String btnType) {
        if (btnType == "MainBtn") {
            int xsize = 350;
            int ysize = 50;
            if (mousePos[0] >= x && mousePos[0] <= (x + xsize) && mousePos[1] >= y && mousePos[1] <= (y + ysize)) {
                return true;
            }
        } else if (btnType == "SmallBtn") {

        }
        return false;
    }
}

/*
 * public static int[] numbers() { int[] arr={5,6,7,8,9}; //initializing array
 * return arr; }
 * 
 */
