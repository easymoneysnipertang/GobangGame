package control;

import javax.swing.JOptionPane;

import view.MainFrame;
import view.StatePanel;

//倒计时，调整状态面板上的倒计时时间
public class Countdown {
	public static int time = 30;
    public static Thread thread;

    public static void startTiming(int mTime) {
        time = mTime;
        thread = new Thread(() -> {
            while (time >= 0) {
                try {
                    StatePanel.setTime(time--);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
            JOptionPane.showMessageDialog(MainFrame.mainFrame,
	                "Time's up", "tick",
	                JOptionPane.CANCEL_OPTION);
        });
        thread.start();
    }
}
