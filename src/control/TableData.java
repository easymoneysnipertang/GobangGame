package control;

import model.Spot;

//处理棋盘上的数据
public class TableData {
	//储存当前应该下棋玩家棋色
    private static String nowColor = Spot.notChess;
    //储存游戏是否结束
    private static boolean gameOver = false;
    private final static Spot[][] spots = new Spot[19][19];//棋子
    //储存最后一次下棋棋子
    public static Spot lastSpot;

    //储存游戏结束时，对五子连珠画线的起始位置
    public static int indexRow = 0;
    public static int indexCol = 0;
    public static int endRow = 0;
    public static int endCol = 0;

    //重新游戏
    public static void reset() {
        //初始化棋桌数据
        for (int i = 0; i < spots.length; i++) {
            for (int j = 0; j < spots[0].length; j++) {
                spots[i][j] = new Spot(i, j, Spot.notChess);
            }
        }
        gameOver = false;
        //黑棋先下
        nowColor = Spot.blackChess;
        indexRow = indexCol = endRow = endCol = 0;
        
        lastSpot=null;
        System.out.println("已初始化棋盘数据");
    }

    //悔棋，更新棋盘数据
    public static boolean retractChess() {
    	if(lastSpot==null||gameOver)
    	{
    		
    		System.out.println("不能悔棋了");
    		return false;
    	}
    	int row=lastSpot.getRow();
    	int col=lastSpot.getCol();
    	nowColor=Spot.getBackColor(nowColor);//换人下棋
    	spots[row][col].setColor(Spot.notChess);
    	lastSpot=null;
    	
    	return true;
    }
    
    //下棋，做判断，更新棋盘数据
    public static void putDownChess(Spot spot) {
        String mColor = spot.getColor();
        int row = spot.getRow();
        int col = spot.getCol();
        if (gameOver) {//游戏已结束则直接返回
            return;
        }
        //nonsense
        if (!nowColor.equals(mColor)) {
            System.out.println("putChess() 不属于此玩家操作" + mColor);
            return;
        }
        if (hasSpot(row, col)) {
            System.out.println("此位置已有棋子" + row + ":" + col);
            return;
        }
        //可以下棋
        nowColor = Spot.getBackColor(nowColor);//下一步该下棋的颜色
        spots[row][col].setColor(mColor);
        
        lastSpot=spots[row][col];
    }

    
    //判断是否有玩家已经胜利
    public static boolean isOver() {
        if (gameOver) {// 游戏已结束则直接返回结果
            return true;
        }
        //四个角度遍历每个棋子
        for (int i = 0; i < spots.length; i++) {
            for (int j = 0; j < spots[0].length; j++) {
                //此点向右最大最大连棋数
                int countRow = 0;
                //此点向下最大最大连棋数
                int countCol = 0;
                //此点右下最大最大连棋数
                int countCR = 0;
                //此点左下最大最大连棋数
                int countCL = 0;
                indexRow = i;
                indexCol = j;

                Spot spot = spots[i][j];
                String color = spot.getColor();
                //判断此点是否有棋子
                if (Spot.notChess.equals(color)) {
                    continue;
                }
                
                //获得向右最大连棋数
                if (j <= 14) {
                    for (int m = 1; m <= 4; m++) {
                        String mColor = spots[i][j + m].getColor();
                        if (mColor.equals(color)) {
                            countRow++;
                        }
                    }
                }
                //判断向下最大连棋数
                if (i <= 14)
                    for (int m = 1; m <= 4; m++) {
                        String mColor = spots[i + m][j].getColor();
                        if (mColor.equals(color)) {
                            countCol++;
                        }
                    }
                //判断右下最大连棋数
                if (i <= 14 && j <= 14)
                    for (int m = 1; m <= 4; m++) {
                        String mColor = spots[i + m][j + m].getColor();
                        if (mColor.equals(color)) {
                            countCR++;
                        }
                    }
                //判断左下最大连棋数
                if (i <= 14 && j >= 4)
                    for (int m = 1; m <= 4; m++) {
                        String mColor = spots[i + m][j - m].getColor();
                        if (mColor.equals(color)) {
                            countCL++;
                        }
                    }
                
                //如果五子连珠则得到结束位置的行列值
                if (countRow == 4) {
                    endRow = indexRow;
                    endCol = indexCol + 4;
                    gameOver = true;
                }
                if (countCol == 4) {
                    endRow = indexRow + 4;
                    endCol = indexCol;
                    gameOver = true;
                }
                if (countCR == 4) {
                    endRow = indexRow + 4;
                    endCol = indexCol + 4;
                    gameOver = true;
                }
                if (countCL == 4) {
                    endRow = indexRow + 4;
                    endCol = indexCol - 4;
                    gameOver = true;
                }
                //胜利直接返回，不用再去考虑连线上其他棋子
                if (gameOver) {
                    return gameOver;
                }
            }
        }
        return gameOver;
    }

    public static Spot getSpot(int row, int col) {
        return spots[row][col];
    }

    public static String getNowColor() {
        return nowColor;
    }

    public static boolean hasSpot(int row, int col) {
        Spot spot = spots[row][col];
        String color = spot.getColor();
        return !Spot.notChess.equals(color);
    }
}
