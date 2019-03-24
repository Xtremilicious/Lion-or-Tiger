package com.xtremism.lionortiger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Player[] playerChoices = new Player[9];
    int[][] winner = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    Player currentPlayer;
    int currTaps = 0;
    private boolean gameOver;
    private Button btnReset;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 9; i++) {
            playerChoices[i] = Player.No;
        }
        btnReset = findViewById(R.id.buttonReset);
        gridLayout = findViewById(R.id.gridLayout);
        currentPlayer = Player.ONE;
        gameOver = false;
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    public void imgTapped(View tappedImage) {
        ImageView imgview = (ImageView) tappedImage;
        int tag = Integer.parseInt(imgview.getTag().toString());

        if (playerChoices[tag] == Player.No && gameOver == false) {
            imgview.setTranslationX(-2000);
            currTaps++;


            playerChoices[tag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                imgview.setImageResource(R.drawable.tiger);
                currentPlayer = Player.TWO;

            } else if (currentPlayer == Player.TWO) {
                imgview.setImageResource(R.drawable.lion);
                currentPlayer = Player.ONE;
            }

            imgview.animate().rotationBy(360).translationXBy(2000).alpha(1).setDuration(1000);

            for (int[] winnercol : winner) {
                if (playerChoices[winnercol[0]] == playerChoices[winnercol[1]] &&
                        playerChoices[winnercol[1]] == playerChoices[winnercol[2]] &&
                        playerChoices[winnercol[0]] != Player.No) {
                    if (currentPlayer == Player.ONE) {
                        Toast.makeText(this, "Player Two won", Toast.LENGTH_LONG).show();
                    } else if (currentPlayer == Player.TWO) {
                        Toast.makeText(this, "Player One won", Toast.LENGTH_LONG).show();
                    }

                    gameOver = true;
                    btnReset.setVisibility(View.VISIBLE);

                }
            }
        }
        if (currTaps == 9) {
            gameOver = true;
            btnReset.setVisibility(View.VISIBLE);
        }

    }

    public void resetGame() {

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imgV = (ImageView) gridLayout.getChildAt(i);
            imgV.setImageDrawable(null);
            imgV.setAlpha(0.2f);
        }
        for (int j = 0; j < 9; j++) {
            playerChoices[j] = Player.No;
        }
        currTaps = 0;
        gameOver = false;
        btnReset.setVisibility(View.GONE);

    }

    enum Player {
        ONE, TWO, No
    }
}
