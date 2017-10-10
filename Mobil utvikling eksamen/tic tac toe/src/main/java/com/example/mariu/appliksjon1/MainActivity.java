package com.example.mariu.appliksjon1;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mariu.appliksjon1.Fragments.GameFragment;
import com.example.mariu.appliksjon1.Fragments.MenuFragment;
import com.example.mariu.appliksjon1.Fragments.StatisticsFragment;

public class MainActivity extends AppCompatActivity {

    GameFragment gameFragment;

    String player1Name;
    String player2Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Todo: sending view thats not going to be used change this?
        openMainMenu(findViewById(R.id.main));
    }


    public void onGameStartButton(View v) {
        EditText player1 = (EditText) findViewById(R.id.player1);
        EditText player2 = (EditText) findViewById(R.id.player2);


        if(!validateInput(player1, player2))
            return;

        player1Name = player1.getText().toString();
        player2Name = player2.getText().toString();

        gameFragment = new GameFragment();
        gameFragment.setPlayer1(player1Name);
        gameFragment.setPlayer2(player2Name);
        gameFragment.setGameBoardSize(3);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.addToBackStack("MENU");
        tx.replace(R.id.main, gameFragment);
        tx.commit();
    }

    public void onBigGameStartButton(View v) {
        EditText player1 = (EditText) findViewById(R.id.player1);
        EditText player2 = (EditText) findViewById(R.id.player2);


        if(!validateInput(player1, player2))
            return;

        player1Name = player1.getText().toString();
        player2Name = player2.getText().toString();

        gameFragment = new GameFragment();
        gameFragment.setPlayer1(player1Name);
        gameFragment.setPlayer2(player2Name);
        gameFragment.setGameBoardSize(5);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.addToBackStack("MENU");
        tx.replace(R.id.main, gameFragment);
        tx.commit();
    }



    private boolean validateInput(EditText player1, EditText player2){
        String player1Name = player1.getText().toString();
        String player2Name = player2.getText().toString();
        if (TextUtils.isEmpty(player1Name)) {
            player1.setError(getString(R.string.not_empty_error));
            return false;
        } else if (TextUtils.getTrimmedLength(player1Name) < 2) {
            player1.setError(getString(R.string.more_than_2_characters_error));
            return false;
        } else {
            player1.setError(null);
        }

        if (TextUtils.isEmpty(player2Name)) {
            player2.setError(getString(R.string.not_empty_error));
            return false;
        } else if (TextUtils.getTrimmedLength(player2Name) < 2) {
            player2.setError(getString(R.string.more_than_2_characters_error));
            return false;
        } else {
            player2.setError(null);
        }
        return true;
    }


    public void onClick(View v) {
        gameFragment.boardClick(v);
    }


    //duplication because the boards are hardcoded
    public void reStartGame(View v) {
        gameFragment = new GameFragment();
        gameFragment.setPlayer1(player1Name);
        gameFragment.setPlayer2(player2Name);
        gameFragment.setGameBoardSize(3);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main, gameFragment);
        tx.commit();
    }

    public void reStartBigGame(View v) {
        gameFragment = new GameFragment();
        gameFragment.setPlayer1(player1Name);
        gameFragment.setPlayer2(player2Name);
        gameFragment.setGameBoardSize(5);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main, gameFragment);
        tx.commit();
    }

    public void openMainMenu(View v) {
        MenuFragment menuFragment = new MenuFragment();
        getSupportFragmentManager().popBackStack("MENU", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main, menuFragment);
        tx.commit();
    }

    public void startStatistics(View v) {
        StatisticsFragment statisticsFragment = new StatisticsFragment();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.addToBackStack(null);
        tx.replace(R.id.main, statisticsFragment);
        tx.commit();
    }



    private static final int TIME_INTERVAL = 2000;
    private long systemTimeOnBackPressed;
    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().findFragmentById(R.id.main) instanceof GameFragment){
            if(systemTimeOnBackPressed + TIME_INTERVAL > System.currentTimeMillis()){
                super.onBackPressed();
                return;
            }
            systemTimeOnBackPressed = System.currentTimeMillis();
            Toast.makeText(this, "Press twice to exit game board", Toast.LENGTH_SHORT).show();
        } else if(getSupportFragmentManager().findFragmentById(R.id.main) instanceof MenuFragment){
            if(systemTimeOnBackPressed + TIME_INTERVAL > System.currentTimeMillis()){
                super.onBackPressed();
                return;
            }
            systemTimeOnBackPressed = System.currentTimeMillis();
            Toast.makeText(this, "Press twice to exit application", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }


}
