package model;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import control.TableData;
import view.ChatRoom;
import view.ChessBoard;
import view.MainFrame;


//将数据处理成字符串而后进行接收，发送
@SuppressWarnings("removal")
public class DataSocket {
	public final static String RETRACT = "retract";
	//接收发送的类型
	public final static String TYPE_chat = "chat";
    public final static String TYPE_spot = "spot";

    //处理数据后发送
    public static void send(Object object) {
        List<String> list = new ArrayList<>();

        //棋子信息
        if (object instanceof Spot) {
            list.add(TYPE_spot);
            int row = ((Spot) object).getRow();
            int col = ((Spot) object).getCol();

            if (row < 10) {
                list.add("0" + row);
            } else {
                list.add("" + row);
            }

            if (col < 10) {
                list.add("0" + col);
            } else {
                list.add("" + col);
            }
            MySocket.sendData(list);
        }
        //聊天内容
        if (object instanceof String) {
            list.add(TYPE_chat);
            list.add((String) object);
            MySocket.sendData(list);
        }
    }

    //接收，解析数据
    public static void receive(List<String> list) {
        String str = list.get(0);//第一个字符串判断数据类型
        switch (str) {
            case TYPE_chat:
            	if(list.get(1).equals(RETRACT)) {//悔棋通过特定字符串发送过来
            		JOptionPane.showMessageDialog(MainFrame.mainFrame,
        	                "对方悔棋", "wait",
        	                JOptionPane.CANCEL_OPTION);
            		if(TableData.retractChess()) {
        	        	ChessBoard.myBroad.repaint();	
        	        	GameCenter.showChess();
            		}
            	}
            	else {
            		ChatRoom.addText(list.get(1), ChatRoom.peText);//对方发送的聊天内容
            		//另起线程播放音效
                    new Thread() {
        				@SuppressWarnings({ "deprecation"})
        				public void run() {
                    		java.net.URL url;
                    		File f=new File("D:/Java/GoBangGame/audio/got.wav");
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
            	}
                break;
            case TYPE_spot:
                int row = Integer.valueOf(list.get(1));
                int col = Integer.valueOf(list.get(2));
                String color = Player.otherPlayer.getColor();
                ChessBoard.submitPaint(new Spot(row, col, color));
                break;
            default:
                System.out.println("DataSocket 收到未知数据！" + str);
                break;
        }
    }
}
