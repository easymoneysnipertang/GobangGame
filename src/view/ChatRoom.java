package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.DataSocket;
import model.MySocket;

//聊天区面板
public class ChatRoom extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static JTextArea jArea;
    private static JTextField jText;
    private static JButton btClear, btSend;
    public static ChatRoom myRoom;
    
    public final static int myText = 0;
    public final static int peText = 1;

    public ChatRoom() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(new Color(245,245,245));
        jArea = new JTextArea(7, 20);
        jText = new JTextField(15);
        
        btClear = new JButton("清空");
        btSend = new JButton("发送");
        btClear.setBackground(new Color(135,206,235));
        btSend.setBackground(new Color(135,206,235));
        
        jArea.setEnabled(false);
        jArea.setLineWrap(false);//不自动换行
        jArea.setText("聊天区：\n");
        jArea.setFont(new Font("楷体", Font.BOLD, 13));
        //滚轮包装
        JScrollPane jsp = new JScrollPane(jArea);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);//过长就用滚轮
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(jsp);
        this.add(jText);
        this.add(btClear);
        this.add(btSend);
        addListener();
        myRoom = this;
    }

    public void addListener() {
        btClear.addActionListener(event -> jArea.setText("聊天区："));//清空聊天区
        btSend.addActionListener(event -> {
            if (MySocket.isStart) {
                String text = jText.getText();
                if (text.length() > 0) {
                    addText(text, myText);
                    DataSocket.send(text);
                }
            } else {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,
                        "消息发送失败！未有玩家连接！", "发送失败",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public static void addText(String text, int who) {
        switch (who) {
            case myText:
                text = "我:" + text;
                break;
            case peText:
                text = "对方:" + text;
                break;
            default:
                break;
        }
        jArea.append(text+"\n");
    }
}
