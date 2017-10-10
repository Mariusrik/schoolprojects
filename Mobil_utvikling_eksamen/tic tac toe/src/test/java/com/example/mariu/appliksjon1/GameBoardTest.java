package com.example.mariu.appliksjon1;

import com.example.mariu.appliksjon1.GameLogic.GameBoard;
import com.example.mariu.appliksjon1.GameLogic.Pieces;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by rikmar15 on 06.05.2017.
 */

public class GameBoardTest {
    GameBoard gameBoard;
    private int BOARD_SIZE = 3;
    private int ROWS_TO_WIN = 3;
    Pieces[][] board;

    @Before
    public void setUp() {
        gameBoard = new GameBoard(BOARD_SIZE, ROWS_TO_WIN);
        board = gameBoard.getBoard();
    }

    @Test
    public void createBoardTest() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            Pieces[] column = board[i];
            for (int j = 0; j < BOARD_SIZE; j++) {
                assertEquals(column[j], Pieces.Empty);
            }
        }
    }

    @Test
    public void allValidPositionsTest() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            Pieces[] column = board[i];
            for (int j = 0; j < BOARD_SIZE; j++) {
                assertTrue(gameBoard.isValidPosition(i, j, Pieces.Cross));
                assertTrue(gameBoard.isValidPosition(i, j, Pieces.Circle));
            }
        }
    }

    @Test
    public void setPieceTest() {
        gameBoard.setPiece(0, 0, Pieces.Cross);
        assertEquals(gameBoard.getBoard()[0][0], Pieces.Cross);

        gameBoard.setPiece(0, 1, Pieces.Circle);
        assertEquals(gameBoard.getBoard()[0][1], Pieces.Circle);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void setWrongPieceTest() {
        gameBoard.setPiece(gameBoard.getBoardSize() + 1, gameBoard.getBoardSize() + 1, Pieces.Cross);

        //tests if board is still intact
        Pieces[][] board = gameBoard.getBoard();
        for (int i = 0; i < BOARD_SIZE; i++) {
            Pieces[] column = board[i];
            for (int j = 0; j < BOARD_SIZE; j++) {
                assertEquals(column[j], Pieces.Empty);
            }
        }
    }


    @Test
    public void testWonVertical() {

        gameBoard.setPiece(0, 0, Pieces.Cross);
        gameBoard.setPiece(0, 1, Pieces.Circle);
        gameBoard.setPiece(1, 1, Pieces.Circle);

        gameBoard.setPiece(2, 0, Pieces.Circle);
        gameBoard.setPiece(2, 1, Pieces.Circle);
        gameBoard.setPiece(2, 2, Pieces.Circle);

        assertTrue(gameBoard.isGameWon());
    }

    @Test
    public void testWonHorizontal() {

        gameBoard.setPiece(0, 1, Pieces.Cross);
        gameBoard.setPiece(1, 1, Pieces.Cross);
        gameBoard.setPiece(2, 1, Pieces.Cross);

        assertTrue(gameBoard.isGameWon());
    }

    @Test
    public void testWonDiagoally() {

        gameBoard.setPiece(0, 0, Pieces.Cross);
        gameBoard.setPiece(1, 1, Pieces.Cross);
        gameBoard.setPiece(2, 2, Pieces.Cross);

        assertTrue(gameBoard.isGameWon());
    }

    @Test
    public void testWonDiagoally2() {

        gameBoard.setPiece(0, 2, Pieces.Cross);
        gameBoard.setPiece(1, 1, Pieces.Cross);
        gameBoard.setPiece(2, 0, Pieces.Cross);

        assertTrue(gameBoard.isGameWon());
    }
}
