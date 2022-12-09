package view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import control.GameCouple;
import control.GameOnline;
import control.GameRobot;
import control.TableData;
import model.DataSocket;
import model.GameCenter;
import model.MySocket;

//右侧控制按钮面板
@SuppressWarnings("removal")
public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static ControlPanel my;
    private JButton btnBack, btnAgain,btnCouple, btnOnline, btnRobot
    ,btnNone1,btnNone2,btnNone3;

    public ControlPanel() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(new Color(245,245,245));//TODO
        
        btnNone3=new JButton("********");//画分割线
        btnNone3.setBackground(new Color(245,245,245));
        btnNone3.setBorderPainted(false);
        btnNone3.setEnabled(false);
        
        btnAgain = new JButton("开始游戏");
        btnAgain.setBackground(new Color(240,255,240));
        
        btnNone1=new JButton("********");//画分割线
        btnNone1.setBackground(new Color(245,245,245));
        btnNone1.setBorderPainted(false);
        btnNone1.setEnabled(false);
        
        btnCouple = new JButton("双人互博");
        btnRobot = new JButton("人机模式");
        btnOnline = new JButton("联机对战");
        btnCouple.setBackground(new Color(248,248,255));
        btnRobot.setBackground(new Color(248,248,255));
        btnOnline.setBackground(new Color(248,248,255));
        
        btnNone2=new JButton("********");
        btnNone2.setBackground(new Color(245,245,245));
        btnNone2.setBorderPainted(false);
        btnNone2.setEnabled(false);
        
        btnBack = new JButton("悔棋");
        btnBack.setBackground(new Color(224,255,255));

        this.add(btnNone3);
        this.add(btnAgain);
        this.add(btnNone1);
        this.add(btnCouple);
        this.add(btnRobot);
        this.add(btnOnline);
        this.add(btnNone2);
        this.add(btnBack);

        my = this;
        addListener();
    }

    public static void init() {
        my.repaint();
    }

    private void addListener() {
        btnBack.addActionListener(event -> {            
        	if(!TableData.retractChess()) {//悔棋失败
    	    	JOptionPane.showMessageDialog(MainFrame.mainFrame,
    	                "现在不能悔棋了", "悔棋失败",
    	                JOptionPane.CANCEL_OPTION);
        	}
        	else {
        		//另起线程播放音效
                new Thread() {
    				@SuppressWarnings({ "deprecation"})
    				public void run() {
                		java.net.URL url;
                		File f=new File("D:/Java/GoBangGame/audio/back.wav");
                		if(!f.exists())System.out.println("文件不存在");
                		else{
    	            		try {
    							url=f.toURL();
    							AudioClip aau;
    							aau=Applet.newAudioClip(url);
    							aau.play();
    						} catch (MalformedURLException e) {							
    							e.printStackTrace();
    						}
                		}         		
                	}
                }.start();
        		
	        	//联机模式得让对面收到悔棋操作
	        	if(GameCenter.getMode()==GameCenter.MODE_ONLINE) {
	        		DataSocket.send(DataSocket.RETRACT);
	        	}
	        	ChessBoard.myBroad.repaint();	
	        	GameCenter.showChess();
        	}
        });

        btnAgain.addActionListener(event -> {
        	//另起线程播放音效
            new Thread() {
				@SuppressWarnings({ "deprecation"})
				public void run() {
            		java.net.URL url;
            		File f=new File("D:/Java/GoBangGame/audio/new.wav");
            		if(!f.exists())System.out.println("文件不存在");
            		else{
	            		try {
							url=f.toURL();
							AudioClip aau;
							aau=Applet.newAudioClip(url);
							aau.play();
						} catch (MalformedURLException e) {							
							e.printStackTrace();
						}
            		}	
            	}
            }.start();
            
            GameCenter.reStart();
            ChessBoard.myBroad.repaint();
            MySocket.close();//TODO：check
            //其他按钮现在才可点击
            btnCouple.setEnabled(true);
            btnRobot.setEnabled(true);
            btnOnline.setEnabled(true);            
            
            btnAgain.setBackground(new Color(240,255,240));//恢复颜色
            btnAgain.setText("开始游戏");
            try {
                MySocket.socket.close();
            } catch (Exception e) {
            }
            
        });


        btnCouple.addActionListener(event -> {
        	//另起线程播放音效
            new Thread() {
				@SuppressWarnings({ "deprecation"})
				public void run() {
            		java.net.URL url;
            		File f=new File("D:/Java/GoBangGame/audio/start.wav");
            		if(!f.exists())System.out.println("文件不存在");
            		else{
	            		try {
							url=f.toURL();
							AudioClip aau;
							aau=Applet.newAudioClip(url);
							aau.play();
						} catch (MalformedURLException e) {							
							e.printStackTrace();
						}
            		}         		
            	}
            }.start();
            
            GameCouple.reStart();
            //其他的按钮无法点击
            btnCouple.setEnabled(false);
            btnRobot.setEnabled(false);
            btnOnline.setEnabled(false);
            
            btnAgain.setBackground(new Color(205,92,92));
            btnAgain.setText("重新游戏");
        });

        btnRobot.addActionListener(event -> {
        	//另起线程播放音效
            new Thread() {
				@SuppressWarnings({ "deprecation"})
				public void run() {
            		java.net.URL url;
            		File f=new File("D:/Java/GoBangGame/audio/start.wav");
            		if(!f.exists())System.out.println("文件不存在");
            		else{
	            		try {
							url=f.toURL();
							AudioClip aau;
							aau=Applet.newAudioClip(url);
							aau.play();
						} catch (MalformedURLException e) {							
							e.printStackTrace();
						}
            		}         		
            	}
            }.start();
            
            GameRobot.reStart();
            btnCouple.setEnabled(false);
            btnRobot.setEnabled(false);
            btnOnline.setEnabled(false);
            
            btnAgain.setBackground(new Color(205,92,92));
            btnAgain.setText("重新游戏");
        });

        btnOnline.addActionListener(event -> {
        	//另起线程播放音效
            new Thread() {
				@SuppressWarnings({ "deprecation"})
				public void run() {
            		java.net.URL url;
            		File f=new File("D:/Java/GoBangGame/audio/start.wav");
            		if(!f.exists())System.out.println("文件不存在");
            		else{
	            		try {
							url=f.toURL();
							AudioClip aau;
							aau=Applet.newAudioClip(url);
							aau.play();
						} catch (MalformedURLException e) {							
							e.printStackTrace();
						}
            		}         		
            	}
            }.start();
            
            GameOnline.reStart();
            MyDialog.online();

            btnCouple.setEnabled(false);
            btnRobot.setEnabled(false);
            btnOnline.setEnabled(false);
            
            btnAgain.setBackground(new Color(205,92,92));
            btnAgain.setText("重新游戏");
        });
    }
}
