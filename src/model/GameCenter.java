package model;

import control.TableData;

//游戏中心
public class GameCenter {
	//储存当前游戏模式状态
    private static int gameModel;
    
    //游戏结束
    public final static int MODE_END = 0;
    //双人对战
    public final static int MODE_COUPLE = 1;
    //人机对战
    public final static int MODE_ROBOT = 2;
    //联机对战
    public final static int MODE_ONLINE = 3;

    //重新开始游戏
    public static void reStart() {
        //初始化棋桌数据
        TableData.reset();
        gameModel = MODE_END;
    }

    //test 显示所有的棋子
    public static void showChess() {
        System.out.println("显示棋桌： 游戏是否结束： isOver() " + TableData.isOver());
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                String color = TableData.getSpot(i, j).getColor();
                System.out.print(color + ", ");
            }
            System.out.println();
        }
    }

    

    public static int getMode() {
        return gameModel;
    }

    public static void setMode(int mode) {
        gameModel = mode;
    }

    public static boolean isEnd() {
        return gameModel == MODE_END;
    }
}
