package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Player;

//两个玩家信息面板，分成左面板和右面板，在更新信息的时候也需要左右的位置参数
public class UserPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	static JPanel myPanel, otherPanel;
    private static JLabel myName, myAddress, myColor, myGrade;
    private static JLabel otherName, otherAddress, otherColor, otherGrade;
    static UserPanel userPanel;
    
    public static final int left = 0;
    public static final int right = 1;

    public UserPanel() {
        this.setVisible(true);
        this.setLayout(null);
        this.setBackground(Color.white);//TODO

        myPanel = new JPanel();
        otherPanel = new JPanel();
        myPanel.setBackground(new Color(255,236,139));
        otherPanel.setBackground(new Color(144,238,144));
        myPanel.setLayout(new GridLayout(0, 1));
        otherPanel.setLayout(new GridLayout(0, 1));

        //往面板上添加标签
        myName = new JLabel(" 玩家: ");
        myAddress = new JLabel(" IP: ");
        myColor = new JLabel(" 棋色: ");
        myGrade = new JLabel(" 分数: ");

        otherName = new JLabel(" 玩家: ");
        otherAddress = new JLabel(" IP: ");
        otherColor = new JLabel(" 棋色: ");
        otherGrade = new JLabel(" 分数: ");

        myPanel.add(myName);
        myPanel.add(myAddress);
        myPanel.add(myColor);
        myPanel.add(myGrade);

        otherPanel.add(otherName);
        otherPanel.add(otherAddress);
        otherPanel.add(otherColor);
        otherPanel.add(otherGrade);

        //左右面板合成完整的用户面板
        this.add(myPanel);
        this.add(otherPanel);
        userPanel = this;
    }

    //初始化
    public static void init() {
        myPanel.setBounds(2, 2, userPanel.getWidth() / 2 - 2, userPanel.getHeight() - 5);
        otherPanel.setBounds(userPanel.getWidth() / 2 + 2, 2, userPanel.getWidth() / 2 - 2,
                userPanel.getHeight() - 5);
        
        //初始化后重新绘制
        setUserInfo(null, left);
        setUserInfo(null, right);
        userPanel.repaint();
    }

    //根据位置设置每个面板上的信息
    public static void setUserInfo(Player player, int position) {
        if (player == null) {
            player = new Player();
        }
        if (position == left) {
            myName.setText(" 玩家: " + player.getName());
            myAddress.setText(" IP: " + player.getAddress());
            myColor.setText(" 棋色: " + player.getColorString());
            myGrade.setText(" 分数: " + player.getGrade());
        } else {
            otherName.setText(" 玩家: " + player.getName());
            otherAddress.setText(" IP: " + player.getAddress());
            otherColor.setText(" 棋色: " + player.getColorString());
            otherGrade.setText(" 分数: " + player.getGrade());
        }
    }

    public static void setGrade(int grade, int position) {
        if (position == left) {
            myGrade.setText(" 分数: " + grade);
        } else {
            otherGrade.setText(" 分数: " + grade);
        }
    }
}
