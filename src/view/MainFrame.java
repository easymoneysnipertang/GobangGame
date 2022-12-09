package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import model.GameCenter;
import model.Player;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
    
    //窗口大小
    private int width = 730;
    private int height = 760;

    //主面板
    public static MainFrame mainFrame;

    public MainFrame() {
        mainFrame = this;
        UIManager.put("Label.font", new Font("宋体", Font.BOLD, 15));
        UIManager.put("Button.font", new Font("楷体", Font.PLAIN, 20));

        this.setTitle(" Java五子棋");
        this.setSize(width, height);
        this.setResizable(false);
        this.setLayout(null);

        //设置居中显示
        int sWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int sHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((sWidth - width) / 2, (sHeight - height) / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWidget();
    }

    //添加控件和菜单栏
    private void addWidget() {
        int x = 5;
        int y = 2;
        int mWidth = width / 4 * 3;
        int mHeight = width / 5;

        this.setJMenuBar(new MyMenuBar());
        this.getContentPane().setBackground(Color.white);

        getContentPane().add(new UserPanel());
        UserPanel.userPanel.setBounds(x, height-mHeight-y-65, mWidth, mHeight);

        getContentPane().add(new ChessBoard());
        ChessBoard.myBroad.setBounds(x, y, mWidth, height-mHeight-2*y-65);

        getContentPane().add(new StatePanel());
        StatePanel.my.setBounds(mWidth + 2 * x, y, width - mWidth - 2 * x,
                mHeight);

        getContentPane().add(new ChatRoom());
        ChatRoom.myRoom.setBounds(mWidth + 2 * x, mHeight+y, width - mWidth
                - 2 * x, (height - mHeight - 2*y)/2);

        getContentPane().add(new ControlPanel());
        ControlPanel.my.setBounds(mWidth + 2 * x, 355, width - mWidth 
        		- 2 * x,height - mHeight - 2 * y);
    }

    // 界面显示，控件加载完毕后执行(向控件加载数据等)
    public static void init() {
        GameCenter.reStart();
        ChessBoard.init();
        UserPanel.init();
        ControlPanel.init();
        Player.init();
        mainFrame.repaint();
    }

    // 窗口关闭事件
    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            close();
        } else {
            super.processWindowEvent(e); //执行窗口事件的默认动作
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ControlPanel.init();
    }

    public static void close() {
        int i = JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "正在退出五子棋...",
                JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
