package control;

import model.GameCenter;
import model.Player;
import model.Spot;
import view.UserPanel;

//双人对战
public class GameCouple {

    public static void reStart() {
        GameCenter.reStart();
        GameCenter.setMode(GameCenter.MODE_COUPLE);
        Player.myPlayer.start(Spot.blackChess);
        Player.otherPlayer.start(Spot.whiteChess);

        //用户面板，更新用户信息
        UserPanel.setUserInfo(Player.myPlayer, UserPanel.left);
        UserPanel.setUserInfo(Player.otherPlayer, UserPanel.right);
    }
}
