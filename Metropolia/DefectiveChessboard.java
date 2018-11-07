package Lab04;

import java.util.Scanner;

public class DefectiveChessboard {
    int [][] chessboard;
    int count;

    public DefectiveChessboard(int size) {
        chessboard = new int [size][size];
        int count = 1;
    }

    public void tileBoard (int tr, int tc, int dr, int dc, int size) {
        if (size==1) return;
        int newSize = size/2;
        if (size == chessboard.length) makeOtherDef(dr, dc, size);
        int placeTile = count++;
        //top-left corner
        if (dr < tr + newSize && dc < tc+newSize) {

            tileBoard(tr, tc, dr, dc, newSize);
        }

        else {
            chessboard[tr+newSize-1][tc+newSize-1]=placeTile;
            tileBoard(tr, tc, tr+newSize -1, tc+newSize-1, newSize);
        }
        //top-right corner
        if (dr < tr+newSize && dc >= tc+newSize) {

            tileBoard(tr, tc+newSize, dr, dc, newSize);
        }

        else {
            chessboard[tr+newSize-1][tc+newSize] = placeTile;
            tileBoard(tr, tc+newSize, tr+newSize-1, tc+newSize, newSize);
        }
        //bottom-left corner
        if (dr >= tr+newSize && dc < tc + newSize) {

            tileBoard(tr+newSize,  tc, dr, dc, newSize);
        }

        else {
            chessboard[tr+newSize][tc+newSize-1]=placeTile;
            tileBoard(tr+newSize, tc, tr+newSize, tc+newSize-1, newSize);
        }
        //bottom-right corner
        if(dr >= tr + newSize && dc >= tc+newSize){

            tileBoard(tr+newSize, tc+newSize, dr, dc, newSize);
        }

        else {
            chessboard[tr + newSize][tc + newSize] = placeTile;
            tileBoard(tr+newSize, tc+newSize, tr + newSize, tc+newSize, newSize);
        }
    }

    private void makeOtherDef(int dr, int dc, int size) {
        int halfSize = size/2;
        //left-top corner
        if (dr < halfSize && dc < halfSize) {
            chessboard[halfSize-1][halfSize]=count;
            chessboard[halfSize][halfSize] = count;
            chessboard[halfSize][halfSize-1] = count;
        }
        //right-top corner
        else if (dr < halfSize && dc >= halfSize) {
            chessboard[halfSize][halfSize] = count;
            chessboard[halfSize][halfSize-1] = count;
            chessboard[halfSize-1][halfSize-1] = count;
        }
        //left-bottom corner
        else if (dr >= halfSize && dc < halfSize) {
            chessboard[halfSize-1][halfSize-1] = count;
            chessboard[halfSize-1][halfSize]=count;
            chessboard[halfSize][halfSize] = count;
        }
        //right-bottom corner
        else {
            chessboard[halfSize][halfSize-1] = count;
            chessboard[halfSize-1][halfSize-1] = count;
            chessboard[halfSize-1][halfSize]=count;
        }
        count++;
    }

    public void printChessboard() {
        for (int i = 0; i<chessboard.length; i++) {
            for (int j = 0; j<chessboard.length; j++)
                System.out.print(chessboard[i][j] + "\t");
            System.out.println();
        }

    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter k, board size is 2^k\nk should be in range 0 through 6");
        int k = scanner.nextInt();
        int boardSize = (int)Math.pow(2, k);
        DefectiveChessboard board = new DefectiveChessboard(boardSize);

        System.out.println("Enter location of defect");
        int dr = scanner.nextInt();
        int dc = scanner.nextInt();
        board.tileBoard(0, 0, dr-1, dc-1, boardSize);

        board.printChessboard();
    }
}
