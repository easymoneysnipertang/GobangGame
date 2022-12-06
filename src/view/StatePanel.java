package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import control.TableData;
import model.GameCenter;
import model.Spot;

//状态面板，建立线程实时刷新
public class StatePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static JLabel colorLabel, modeLabel, timeLabel,smileLabel;
    public static StatePanel my;

    public StatePanel() {
        modeLabel = new JLabel("显示游戏模式");
        timeLabel = new JLabel("当前回合:");
        colorLabel = new JLabel("玩家:黑棋先下");
        smileLabel = new JLabel(": )");
        smileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        this.setBackground(new Color(245,245,245));
        this.setLayout(new GridLayout(0, 1));
        this.add(smileLabel);
        this.add(modeLabel);
        this.add(timeLabel);
        this.add(colorLabel);
        my = this;
        updateInfo();
    }

    //更新状态面板
    private static void updateInfo() {
        new Thread(() -> {
            while (true) {//用线程来进行刷新
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    colorLabel.setText("此面板显示异常！");
                }
                String color = TableData.getNowColor();
                if (Spot.blackChess.equals(color)) {
                    colorLabel.setText("黑子说话");
                } else if (Spot.whiteChess.equals(color)) {
                    colorLabel.setText("白子说话");
                } else {
                    colorLabel.setText("玩家:黑棋先下");
                }

                switch (GameCenter.getMode()) {
                    case GameCenter.MODE_END:
                        modeLabel.setText("当前模式:未开始");
                        break;
                    case GameCenter.MODE_COUPLE:
                        modeLabel.setText("当前模式:双人对战");
                        break;
                    case GameCenter.MODE_ROBOT:
                        modeLabel.setText("当前模式:人机对战");
                        break;
                    case GameCenter.MODE_ONLINE:
                        modeLabel.setText("当前模式:联机对战");
                        break;
                    default:
                        break;
                }

            }
        }).start();
    }

    public static void setTime(int mTime) {
        timeLabel.setText("当前回合: 倒计时" + mTime + "秒");
    }
}
