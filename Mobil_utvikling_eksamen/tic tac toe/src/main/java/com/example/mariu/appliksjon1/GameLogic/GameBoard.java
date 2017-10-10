package com.example.mariu.appliksjon1.GameLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rikmar15 on 04-May-17.
 */

public class GameBoard {

    private int BOARD_SIZE;
    private int ROWS_TO_WIN;


    private int MOVES_LEFT;

    private Pieces[][] board;


    public GameBoard(int boardSize, int rowsToWin) {
        BOARD_SIZE = boardSize;
        ROWS_TO_WIN = rowsToWin;
        MOVES_LEFT = boardSize * boardSize;

        board = new Pieces[boardSize][boardSize];
        createBoard();
    }

    /**
     * Sets all places on the board to empty
     */
    public void createBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            Pieces[] column = board[i];
            for (int j = 0; j < BOARD_SIZE; j++) {
                column[j] = Pieces.Empty;
            }
        }
    }

    //just prints the status of the board
    public void testBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            Pieces[] column = board[i];
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.println(column[j]);
            }
            System.out.println("######");
        }
    }




    /**
     * Checks if game is won by comparing rows columns and diagonals.
     *
     * @return boolean is game won
     */
    public boolean isGameWon(int x, int y) {

        //HORIZONTAL
        if (y + 1 < BOARD_SIZE && y + 2 < BOARD_SIZE) {
            if ((board[x][y] == board[x][y + 1]) && (board[x][y] == board[x][y + 2])) {
                return true;
            }
        }

        if (y - 1 >= 0 && y - 2 >= 0) {
            if ((board[x][y] == board[x][y - 1]) && (board[x][y] == board[x][y - 2])) {
                return true;
            }
        }

        if (y - 1 >= 0 && y + 1 < BOARD_SIZE) {
            if ((board[x][y] == board[x][y - 1]) && (board[x][y] == board[x][y + 1])) {
                return true;
            }
        }


        //HORIZONTAL
        //VERTICAL
        if (x + 1 < BOARD_SIZE && x + 2 < BOARD_SIZE) {

            if ((board[x][y] == board[x + 1][y]) && (board[x][y] == board[x + 2][y])) {
                return true;
            }
        }

        if (x - 1 >= 0 && x - 2 >= 0) {
            if ((board[x][y] == board[x - 1][y]) && (board[x][y] == board[x - 2][y])) {
                return true;
            }
        }

        if (x - 1 >= 0 && x + 1 < BOARD_SIZE) {
            if ((board[x][y] == board[x - 1][y]) && (board[x][y] == board[x + 1][y])) {
                return true;
            }
        }
        //VERTICAL

        //DIAGONAL

        if (x + 1 < BOARD_SIZE && y - 1 >= 0 && x - 1 >= 0 && y + 1 < BOARD_SIZE) {
            if ((board[x][y] == board[x + 1][y - 1]) && (board[x][y] == board[x - 1][y + 1])) {
                return true;
            }
        }


        if (x + 1 < BOARD_SIZE && y - 1 >= 0 && x + 2 < BOARD_SIZE && y - 2 >= 0) {
            if ((board[x][y] == board[x + 1][y - 1]) && (board[x][y] == board[x + 2][y - 2])) {
                return true;
            }
        }

        if (y + 1 < BOARD_SIZE && x - 1 >= 0 && y + 2 < BOARD_SIZE && x - 2 >= 0) {
            if ((board[x][y] == board[x - 1][y + 1]) && (board[x][y] == board[x - 2][y + 2])) {
                return true;
            }
        }
        //DIAGOAL

        //DIAGONAL2

        if (x + 1 < BOARD_SIZE && y + 1 < BOARD_SIZE && x - 1 >= 0 && y - 1 >= 0) {
            if ((board[x][y] == board[x + 1][y + 1]) && (board[x][y] == board[x - 1][y - 1])) {
                return true;
            }
        }

        if (x + 1 < BOARD_SIZE && y + 1 < BOARD_SIZE && x + 2 < BOARD_SIZE && y + 2 < BOARD_SIZE) {
            if ((board[x][y] == board[x + 1][y + 1]) && (board[x][y] == board[x + 2][y + 2])) {
                return true;
            }
        }

        if (x - 1 >= 0 && y - 1 >= 0 && x - 2 >= 0 && y - 2 >= 0) {
            if ((board[x][y] == board[x - 1][y - 1]) && (board[x][y] == board[x - 2][y - 2])) {
                return true;
            }
        }
        //DIAGONAL2

        return false;
    }



    /**
     * Checks if a piece has a valid position on the board.
     *
     * @param x     int x coordinate
     * @param y     int y coordinate
     * @param piece enum piece to put on the board
     * @return boolean valid board position
     */
    public boolean isValidPosition(int x, int y, Pieces piece) {
        if (board[x][y] == Pieces.Empty)
            return true;
        else
            return false;
    }

    /**
     * Sets piece on the board on specific coordinate
     *
     * @param x     int x coordinate
     * @param y     int y coordinate
     * @param piece enum piece to put on the board
     */
    public void setPiece(int x, int y, Pieces piece) {
        board[x][y] = piece;
        useMove();
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public int getRowsToWin() {
        return ROWS_TO_WIN;
    }

    public Pieces[][] getBoard() {
        return board;
    }

    public int getMovesLeft() {
        return MOVES_LEFT;
    }

    private void useMove() {
        MOVES_LEFT--;
    }




    /*
    public boolean isGameWon() {
        List<Pieces> vertical = new ArrayList<>();
        List<Pieces> horizontal = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            Pieces[] rootPosition = board[i];
            horizontal = new ArrayList<>();
            vertical = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(rootPosition[j] + " ");

                Pieces[] horizontalRootPosition = board[j];
                horizontal.add(horizontalRootPosition[i]);
                vertical.add(rootPosition[j]);
            }

            //Checks if game is won vertically
            if (checkRows(vertical))
                return true;
                //Checks if game is won horizontally
            else if (checkRows(horizontal))
                return true;

            System.out.println("\n");
        }

        //checks diagonally
        List<Pieces> diagonal = new ArrayList<>();
        List<Pieces> diagonal2 = new ArrayList<>();

        diagonal = new ArrayList<>();
        diagonal2 = new ArrayList<>();
        for (int j = 0; j < BOARD_SIZE; j++) {
            diagonal.add(board[j][j]);
            diagonal2.add(board[BOARD_SIZE - (j + 1)][j]);
            //System.out.print(board[BOARD_SIZE - (j+1)][j] + " ");
            //System.out.print(board[j][j] + " ");
        }

        //diagonal 1
        if (checkRows(diagonal))
            return true;
            //diagonal 2
        else if (checkRows(diagonal2))
            return true;
        else
            return false;
    }


    //Checks if a row with pieces are a streak
    private boolean checkRows(List<Pieces> list) {
        int xInARowCounter = 1;
        Pieces horizontalTemp = null;
        for (Pieces piece : list) {

            if ((piece == horizontalTemp) && (piece != Pieces.Empty)) {
                xInARowCounter++;
                System.out.println(xInARowCounter);
                if (xInARowCounter == ROWS_TO_WIN)
                    return true;
            } else
                xInARowCounter = 1;

            horizontalTemp = piece;
        }
        return false;
    }

    * */
}
