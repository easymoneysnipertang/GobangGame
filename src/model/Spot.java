package model;


//棋子类
public class Spot {
	//棋子的几种状态
    public final static String notChess = "none";
    public final static String blackChess = "black";
    public final static String whiteChess = "white";

    private String color;
    private int row;
    private int col;

    //行列取值0-18
    public Spot(int row, int col, String color) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    //返回一个相反颜色棋子
    public static String getBackColor(String mColor) {
        return Spot.blackChess.equals(mColor) ? Spot.whiteChess : Spot.blackChess;
    }

    //返回字符串，在userPanel显示
    public static String getColorString(String mColor) {
        if (Spot.notChess.equals(mColor)) {
            return "无";
        }
        if (Spot.blackChess.equals(mColor)) {
            return "黑棋";
        }
        if (Spot.whiteChess.equals(mColor)) {
            return "白棋";
        }
        return "getColorString() 传入参数错误！" + mColor;
    }
}
