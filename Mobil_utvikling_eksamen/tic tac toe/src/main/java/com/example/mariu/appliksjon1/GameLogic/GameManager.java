package com.example.mariu.appliksjon1.GameLogic;

import android.util.Log;
import android.widget.Button;

import com.example.mariu.appliksjon1.R;

/**
 * Created by mariu on 18-May-17.
 */

public class GameManager {

    GameBoard gameBoard;


    String player1;
    String player2;

    String nextPlayerTurn;

    String winner;
    String loser;


    public GameManager(int gameBoardSize,String player1, String player2){
        gameBoard = new GameBoard(gameBoardSize, 3);
        this.player1 = player1;
        this.player2 = player2;
    }

    public GameStatus gameMove(Button button) {

        button.setEnabled(false);
        int movesLeft = gameBoard.getMovesLeft();
        String[] xy = button.getTag().toString().split("\\.");

        Log.v("SPLITTAG", xy[0]);
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);

        if (movesLeft % 2 == 1) {
            Log.v("MODULO", "Odd number");
            button.setBackgroundResource(R.drawable.circle_piece);
            gameBoard.setPiece(x, y, Pieces.Circle);
            nextPlayerTurn = player2;
            return gameStatus(player1, player2, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
        }else if (movesLeft % 2 == 0) {
            Log.v("MODULO", "Even number");
            button.setBackgroundResource(R.drawable.cross_piece);
            gameBoard.setPiece(x, y, Pieces.Cross);
            nextPlayerTurn = player1;
            return gameStatus(player2, player1, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
        }
        return GameStatus.OnGoing;
    }


    private GameStatus gameStatus(String winner, String loser, int x, int y){
        if (gameBoard.isGameWon(x,y)) {
            setWinner(winner);
            setLoser(loser);
            return GameStatus.Won;

        } else if (gameBoard.getMovesLeft() == 0){
            return GameStatus.Draw;

        } else {
            return GameStatus.OnGoing;
        }
    }


    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }


    public String getNextPlayerTurn(){
        return this.nextPlayerTurn;
    }
}
