package model;


//玩家类
public class Player {
	public String name;
    public String address = "无IP地址(本地玩家)";
    public int grade = 0;
    public String color = Spot.notChess;
    public static int playerIndex = 1;
    //存储双方对战玩家
    public static Player myPlayer, otherPlayer;

    public Player() {
        this.name = "默认玩家" + playerIndex;
        playerIndex++;
    }

    //联机时初始化两个玩家
    public static void init() {
        myPlayer = new Player();
        otherPlayer = new Player();
    }

    //获取棋色
    public void start(String color) {
        this.color = color;
        System.out.println(name + " 获得棋色 " + getColorString());
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getColorString() {
        return Spot.getColorString(color);
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int addGrade(int add) {
        grade += add;
        return grade;
    }
}
