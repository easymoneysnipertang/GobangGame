package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.TableData;
import model.DataSocket;
import model.GameCenter;
import model.Player;
import model.Spot;


//棋盘绘制
public class ChessBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	//棋子大小
    protected static int chessSize;
    public static ChessBoard myBroad;
    
    //用线程来解决棋盘会覆盖掉棋子
    static Thread gThread, allChessThread;

    public ChessBoard() {
        this.setVisible(true);
        //this.setBackground(new Color(249, 214, 91));
        this.setBackground(new Color(205,181,205));
        this.addListener();
        myBroad = this;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintTable();
        paintAllChess();
        paintResult();
    }

    //初始化
    public static void init() {
        chessSize = myBroad.getWidth() / 19;
        myBroad.repaint();
    }

    public static void addGrade() {
        //当前应该下白棋，则黑棋获胜，给黑棋加分
        if (!Player.myPlayer.getColor().equals(TableData.getNowColor())) {
            int grade = Player.myPlayer.addGrade(1);
            UserPanel.setGrade(grade, UserPanel.left);
        } else {
            int grade = Player.otherPlayer.addGrade(1);
            UserPanel.setGrade(grade, UserPanel.right);
        }
    }

    private void addListener() {
    	//鼠标事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int cx = e.getX();
                int cy = e.getY();
                System.out.print("点击坐标" + cx + ":" + cy);

                //根据鼠标坐标，获得行列位置
                int row = Coordinate.yToRow(cy);
                int col = Coordinate.xToCol(cx);
                Spot spot = new Spot(row - 1, col - 1, TableData.getNowColor());
                System.out.println("， 点击行列" + row + ":" + col);

                boolean canSpot = isCanSpot(spot);
                if (canSpot) {
                	//联机模式发送棋子信息
                    if (GameCenter.getMode() == GameCenter.MODE_ONLINE) {
                        DataSocket.send(spot);
                    }
                    submitPaint(spot);
                }
                
            }
        });
    }
    
    //接收绘制请求
    public static void submitPaint(Spot spot) {
        paintChessImages(spot);
        TableData.putDownChess(spot);

        //判断游戏是否结束
        if (TableData.isOver()) {
            addGrade();//增加用户积分
            GameCenter.setMode(GameCenter.MODE_END);
            paintResult();//绘制连线
            //弹出对话框
            if (Spot.whiteChess.equals(TableData.getNowColor())) {
                //当前应该下白棋，则黑棋获胜，显示对话框
                JOptionPane.showMessageDialog(MainFrame.mainFrame, "黑棋获胜！增加100积分！",
                        "游戏结束", JOptionPane.CANCEL_OPTION);
            } else {
                JOptionPane.showMessageDialog(MainFrame.mainFrame, "白棋获胜！增加100积分！",
                        "游戏结束", JOptionPane.CANCEL_OPTION);
            }
        }
    }

    //判断能否下棋
    public static boolean isCanSpot(Spot spot) {
        if (TableData.hasSpot(spot.getRow(), spot.getCol())) {
            System.out.println("ChessBroad 此点已有棋子！");
            return false;
        }
        //如果是联机对战，界面上只能下自己颜色的棋子
        if (GameCenter.getMode() == GameCenter.MODE_ONLINE) {
            if (!spot.getColor().equals(Player.myPlayer.getColor())) {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,
                        "联机对战中，请先等待对方下棋", "请等待",
                        JOptionPane.CANCEL_OPTION);
                System.out.println(Player.myPlayer.getColor() + ":" + TableData.getNowColor());
                return false;
            }
        }
        //没有棋子障碍，还需继续判断是否开始游戏
        if (GameCenter.isEnd()) {
            JOptionPane.showMessageDialog(null,
                    "游戏未开始，或已结束！\n请先选择游戏模式", "游戏未开始",
                    JOptionPane.CANCEL_OPTION);
            return false;
        }
        
        return true;
    }

    //绘制棋子
	private static void paintChessImages(Spot spot) {
        if (spot != null) {
        	
            int row = spot.getRow() + 1;
            int col = spot.getCol() + 1;

            int cx = Coordinate.colToX(col);
            int cy = Coordinate.rowToY(row);
            Graphics g = myBroad.getGraphics();
            String color = spot.getColor();
            switch (color) {
                case Spot.blackChess:
                    g.setColor(Color.black);
                    break;
                case Spot.whiteChess:
                    g.setColor(Color.white);
                    break;
                default:
                    return;
            }
            g.fillOval(cx - chessSize / 2+7, cy - chessSize / 2+7, chessSize,
                    chessSize);
        }
    }

    //绘制棋盘
    private static void paintTable() {
        final Graphics graphics = myBroad.getGraphics();
        graphics.setFont(new Font("黑体", Font.BOLD, 20));
        //在线程中绘制棋盘
        gThread = new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            for (int i = 0; i < 19; i++) {
                graphics.drawString("" + (i + 1),1, 25 + chessSize * i);
                graphics.drawLine(chessSize / 2 +7, chessSize / 2 + chessSize * i +7,
                        chessSize / 2 + chessSize * 18 +7, chessSize / 2
                                + chessSize * i +7);

                graphics.drawString("" + (i + 1), chessSize * i + 13, 15);
                graphics.drawLine(chessSize / 2 + chessSize * i +7, chessSize / 2 +7,
                        chessSize / 2 + chessSize * i +7, chessSize / 2
                                + chessSize * 18 +7);
            }
        });
        gThread.start();
    }

    //绘制所有棋子
    private static void paintAllChess() {
        //绘制所有棋子线程，没有线程时棋子可能绘制失败！
        allChessThread = new Thread(() -> {
            try {//等待棋桌绘制完成
                gThread.join();
            } catch (InterruptedException e) {
            }
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {
                    Spot spot = TableData.getSpot(i, j);
                    paintChessImages(spot);
                }
            }
        });
        allChessThread.start();
    }

    //胜利后绘制五子连珠
    private static void paintResult() {
    	if (GameCenter.isEnd()) {
            if (TableData.endRow + TableData.endCol < 5) {//TODO:delete
                return;
            }

            System.out.println("赢棋起始位置：" + TableData.indexRow + " " + TableData.indexCol);
            System.out.println("赢棋终止位置：" + TableData.endRow + " " + TableData.endCol);

            Graphics2D g = (Graphics2D) myBroad.getGraphics();
            int indexX = Coordinate.colToX(TableData.indexCol + 1)+7;
            int indexY = Coordinate.rowToY(TableData.indexRow + 1)+7;
            int endX = Coordinate.colToX(TableData.endCol + 1)+7;
            int endY = Coordinate.rowToY(TableData.endRow + 1)+7;
            g.setColor(Color.yellow);
            g.setStroke(new BasicStroke(5.0f));
            g.setFont(new Font("黑体", Font.BOLD, 150));
            g.drawLine(indexX, indexY, endX, endY);
        }
    }
}
