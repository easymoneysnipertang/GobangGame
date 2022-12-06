package view;

//用来转换棋子行列位置和在面板上的坐标
public class Coordinate {
	
     //将行转换为Y坐标
    public static int rowToY(int row) {
        return ChessBoard.chessSize * (row - 1) + ChessBoard.chessSize / 2;
    }

    //将列转换为X坐标
    public static int colToX(int col) {
        return ChessBoard.chessSize * (col - 1) + ChessBoard.chessSize / 2;
    }

    //Y坐标转换为行
    public static int yToRow(int y) {
        return (y + ChessBoard.chessSize) / ChessBoard.chessSize;
    }

    //将X坐标转换为列
    public static int xToCol(int x) {
        return (x + ChessBoard.chessSize) / ChessBoard.chessSize;
    }
}
