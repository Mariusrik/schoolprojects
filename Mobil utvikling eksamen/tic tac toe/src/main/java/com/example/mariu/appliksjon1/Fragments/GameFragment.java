package com.example.mariu.appliksjon1.Fragments;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.mariu.appliksjon1.GameLogic.GameManager;
import com.example.mariu.appliksjon1.GameLogic.GameStatus;
import com.example.mariu.appliksjon1.Model.GameStatisticsContract;
import com.example.mariu.appliksjon1.Model.GameStatisticsDbHelper;
import com.example.mariu.appliksjon1.R;

/**
 * Created by mariu on 16-May-17.
 */

public class GameFragment extends Fragment {

    GameManager gameManager;

    TextView gameHeader;
    private int gameBoardSize = 3;

    GameStatisticsDbHelper dbHelper;

    String player1 = "Undefined";
    String player2 = "Undefined";

    //Here the two board sizes are hardcoded, if i had time i would make dynamic layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        ViewGroup root;
        if (gameBoardSize == 5) {
             root = (ViewGroup) inflater.inflate(R.layout.fragment_game_big, null);
        }else {
             root = (ViewGroup) inflater.inflate(R.layout.fragment_game, null);
        }

        gameManager = new GameManager(gameBoardSize, player1, player2);

        dbHelper = new GameStatisticsDbHelper(getActivity());

        TextView player1TextView = (TextView) root.findViewById(R.id.player1TextView);
        player1TextView.setText(player1);

        TextView player2TextView = (TextView) root.findViewById(R.id.player2TextView);
        player2TextView.setText(player2);

        gameHeader = (TextView) root.findViewById(R.id.gameHeader);

        return root;
    }




    private class dbAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(GameStatisticsContract.StatisticsTable.COLUMN_NAME_WINNER, params[0]);
            values.put(GameStatisticsContract.StatisticsTable.COLUMN_NAME_LOSER, params[1]);

            db.insert(GameStatisticsContract.StatisticsTable.TABLE_NAME, null, values);

            return null;
        }
    }

    private void showMenuButtons(){
        ViewSwitcher switcher = (ViewSwitcher) getView().findViewById(R.id.buttonSwitcher);
        switcher.showNext();
    }


    public void enableButtons(Boolean value){
        for(int i = 1; i <= (gameBoardSize * gameBoardSize); i++  ){
            String buttonID = "gameButton" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());

            getView().findViewById(resID).setEnabled(value);
        }
    }

    public void boardClick(View v) {
        GameStatus gameStatusEnum = gameManager.gameMove((Button) getView().findViewById(v.getId()));

        if(gameStatusEnum == GameStatus.Won){
            gameHeader.setText(gameManager.getWinner() + " " + getText(R.string.won_against) + " " + gameManager.getLoser());

            showMenuButtons();

            new dbAsyncTask().execute(gameManager.getWinner(), gameManager.getLoser());

            enableButtons(false);
        } else if (gameStatusEnum == GameStatus.Draw){
            gameHeader.setText(R.string.draw_message);
            showMenuButtons();
        } else if (gameStatusEnum == GameStatus.OnGoing){
            gameHeader.setText(getText(R.string.next_turn_is) + " " + gameManager.getNextPlayerTurn());
        }
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }


    public void setGameBoardSize(int boardSize){
        this.gameBoardSize = boardSize;
    }
    //TODO: Are you sure you want to quit menu
}
