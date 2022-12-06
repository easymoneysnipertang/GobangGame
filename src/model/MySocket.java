package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class MySocket {
	public static Socket socket;
    public static ServerSocket server;
    public final static int port = 8090;

    //是否连接上，用来判断是否掉线，窗口提示
    public static boolean isStart = false;
    //对方IP地址
    public static String peAddress;

    //作为Server创建socket，将连接到的socket保存到起来
    public static void startServer() throws IOException {
        server = new ServerSocket(port);
        MySocket.socket = server.accept();
        startGetData();//有玩家加入房间，准备接收数据
        isStart = true;
        peAddress = socket.getInetAddress().getHostAddress();
        System.out.println("startServer() 对方加入连接成功！准备接收对方数据");
    }

    //作为Client连接到指定的IP地址上去
    public static void getSocket(final String address) throws Exception {
        socket = new Socket(address,port);
        //socket.setSoTimeout(5000);
        //socket.connect(new InetSocketAddress(address, port));
        startGetData();//成功加入房间，准备接收数据
        isStart = true;
        peAddress = socket.getInetAddress().getHostAddress();
        System.out.println("getSocket() 加入对方连接成功,准备接收对方数据");
    }

    //发送数据，当发送数据异常时把isStart置为false，即断开连接
    public static void sendData(final Object object) {
        new Thread(() -> {
            try {
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                os.writeObject(object);
            } catch (Exception e) {
            	//e.printStackTrace();
                System.out.println("putData()数据发送异常！终止连接");
                isStart = false;
            }
        }).start();
    }

    //接收数据
    public static void startGetData() {
        new Thread(() -> {
            while (true) {
                try {
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    Object object = in.readObject();
                    if (object instanceof List) {
                        @SuppressWarnings("unchecked")
						List<String> list = (List<String>) object;
                        DataSocket.receive(list);
                    }
                } catch (Exception e) {
                	//e.printStackTrace();
                    System.out.println("getData()数据接收异常，终止连接");
                    isStart = false;//接收数据异常也会把isStart置为false
                    return;
                }
            }
        }).start();
    }

    public static void close() {
        try {
            server.close();
            socket.close();
            isStart = false;
        } catch (Exception e) {
        	System.out.println("wrong in closing socket");
        }
    }
}
