package view;

import java.awt.Desktop;
import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import control.Countdown;

//菜单栏，通过弹出对话框来实现交互
public class MyMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	//菜单项
    JMenuItem checkIP, exit, about,setTime, word,helpOnline;

    public MyMenuBar() {
        UIManager.put("Menu.font", new Font("宋体", Font.BOLD, 20));
        UIManager.put("MenuItem.font", new Font("宋体", Font.BOLD, 20));

        JMenu menu = new JMenu("菜单");
        JMenu setting = new JMenu("设置");
        JMenu help = new JMenu("帮助");
        
        checkIP = new JMenuItem("查看本机IP");
        exit = new JMenuItem("退出");
        about = new JMenuItem("开发说明");
        setTime = new JMenuItem("倒计时设置");
        word = new JMenuItem("游戏说明");
        helpOnline = new JMenuItem("Github");

        menu.add(checkIP);
        menu.add(exit);
        setting.add(setTime);
        help.add(about);
        help.add(word);
        help.add(helpOnline);

        this.add(menu);
        this.add(setting);
        this.add(help);
        
        addListener();
    }

    public void addListener() {
        exit.addActionListener(event -> MainFrame.close());
        
        about.addActionListener(event -> {
            String function = "Java五子棋";
            String person = "\nStudent_tang\n";
            String info = function + person + "\n开发时间：" + MainFrame.BUILD_TIME + "\n"
            								+MainFrame.WORDS;
            JOptionPane.showMessageDialog(null, info, "软件说明",
                    JOptionPane.INFORMATION_MESSAGE);
        });       
        
        checkIP.addActionListener(event -> {
            String localIP = "本机IP地址:";
            InetAddress addr;
			try {
				addr = InetAddress.getLocalHost();
				String hostName=addr.getHostName();
				localIP = localIP +"\n"+hostName+ "\n" + addr.getHostAddress();
				JOptionPane.showMessageDialog(MainFrame.mainFrame, localIP, "查看本机IP", 
						JOptionPane.INFORMATION_MESSAGE);
			} catch (UnknownHostException e) {
				System.out.println("wrong in check ip");
			}                      
        });
        
        setTime.addActionListener(event -> {
            String input = JOptionPane.showInputDialog("请输入超时时间(秒)", "30");
            try {
                int time = Integer.valueOf(input);
                Countdown.startTiming(time);
            } catch (Exception e) {
            }
        });
        
        word.addActionListener(event -> JOptionPane
                .showMessageDialog(
                        MainFrame.mainFrame,
                        "游戏名称：Java五子棋\n\n双人互博：本地玩家轮流操作，左右互博\n人机模式：与内置AI下棋\n联机模式："
                        + "可创建房间或输入IP地址加入房间远端下棋\n",
                        "游戏说明", JOptionPane.INFORMATION_MESSAGE));
        
        helpOnline.addActionListener(event -> {
            try {
                java.net.URI uri = new java.net.URI("https://github.com/easymoneysnipertang");
                Desktop.getDesktop().browse(uri);
            } catch (Exception e) {
            }
        });
    }
}
