package control;

import model.GameCenter;
import model.Player;
import model.PlayerAI;
import model.Spot;
import view.ChessBoard;
import view.UserPanel;

//人机对战
public class GameRobot {
	public static void reStart() {
        GameCenter.reStart();
        GameCenter.setMode(GameCenter.MODE_ROBOT);
        
        Player.myPlayer.start(Spot.blackChess);
        final PlayerAI playerAI = new PlayerAI();
        playerAI.start(Spot.whiteChess);
        playerAI.setGrade(Player.otherPlayer.getGrade());

        //用户面板，更新用户信息
        UserPanel.setUserInfo(Player.myPlayer, UserPanel.left);
        UserPanel.setUserInfo(playerAI, UserPanel.right);

        //下棋线程
        new Thread(() -> {
            String color = playerAI.getColor();
            while (!GameCenter.isEnd()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("robotThread 睡眠异常！");
                }
                //轮到人机下棋了
                if (TableData.getNowColor().equals(color)&&!GameCenter.isEnd()) {
                    Spot spot = playerAI.getBestChess(color);//获取最佳下棋位置
                    ChessBoard.submitPaint(spot);
                }
            }
            System.out.println("电脑下棋线程已停止");
        }).start();
    }
}
