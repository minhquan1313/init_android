package com.mtb.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    TextView p1_score_text,
            p2_score_text,
            winner_text;
    CardView tick_1_1,
            tick_1_2,
            tick_1_3,
            tick_2_1,
            tick_2_2,
            tick_2_3,
            tick_3_1,
            tick_3_2,
            tick_3_3;
    Button reset_btn;
    Context context;

    int p1_score = 0,
            p2_score = 0;
    boolean isPaused = false;

    int[] gameState = {
            0, 0, 0,
            0, 0, 0,
            0, 0, 0
    };
    int row = 3, col = 3, countToWin = 3;
    int[][] winingPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };
    PlayerTurn playerTurn = PlayerTurn.PLAYER_ONE;
    PlayerTurn playerWon = null;

    enum PlayerTurn {
        PLAYER_ONE,
        PLAYER_TWO;

        public int getV() {
            switch (this) {
                case PLAYER_ONE:
                    return 1;
                case PLAYER_TWO:
                    return 2;
            }

            return 1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        initial();

        tickClicks();

        resetBtn();

        updateUI();
    }


    private void initial() {
        tick_1_1 = findViewById(R.id.tick_1_1);
        tick_1_2 = findViewById(R.id.tick_1_2);
        tick_1_3 = findViewById(R.id.tick_1_3);
        tick_2_1 = findViewById(R.id.tick_2_1);
        tick_2_2 = findViewById(R.id.tick_2_2);
        tick_2_3 = findViewById(R.id.tick_2_3);
        tick_3_1 = findViewById(R.id.tick_3_1);
        tick_3_2 = findViewById(R.id.tick_3_2);
        tick_3_3 = findViewById(R.id.tick_3_3);
        p1_score_text = findViewById(R.id.p1_score_text);
        p2_score_text = findViewById(R.id.p2_score_text);
        winner_text = findViewById(R.id.winner_text);
        reset_btn = findViewById(R.id.reset_btn);
    }

    private void tickClicks() {
        onBtnClick(tick_1_1, 1, 1);
        onBtnClick(tick_1_2, 1, 2);
        onBtnClick(tick_1_3, 1, 3);
        onBtnClick(tick_2_1, 2, 1);
        onBtnClick(tick_2_2, 2, 2);
        onBtnClick(tick_2_3, 2, 3);
        onBtnClick(tick_3_1, 3, 1);
        onBtnClick(tick_3_2, 3, 2);
        onBtnClick(tick_3_3, 3, 3);
    }

    private void reset() {
        gameState = new int[]{
                0, 0, 0,
                0, 0, 0,
                0, 0, 0
        };
        playerWon = null;
        playerTurn = PlayerTurn.PLAYER_ONE;
        resetBtns(tick_1_1,
                tick_1_2,
                tick_1_3,
                tick_2_1,
                tick_2_2,
                tick_2_3,
                tick_3_1,
                tick_3_2,
                tick_3_3);
    }

    private int getPosNumber(int x, int y) {
        return col * (x - 1) + y;
    }

    private void resetBtns(CardView... btns) {
        for (CardView btn : btns) {
            btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.black));
        }
    }

    private void updateUI() {
        p1_score_text.setText(String.valueOf(p1_score));
        p2_score_text.setText(String.valueOf(p2_score));

        if (playerWon != null) return;
        String player = "Luot danh cua " + (playerTurn == PlayerTurn.PLAYER_ONE ? "player One" : "player Two");
        winner_text.setText(player);
    }

    private void onBtnClick(CardView btn, int x, int y) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused) return;
                Toast.makeText(context, "Click " + x + " " + y, Toast.LENGTH_SHORT).show();

                int post = getPosNumber(x, y) - 1;
                if (gameState[post] != 0)
                    return;

                gameState[post] = playerTurn.getV();
                if (playerTurn == PlayerTurn.PLAYER_ONE) {
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red));
                } else {
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.blue));
                }

                for (int[] winPos : winingPositions) {
                    if (gameState[winPos[0]] == gameState[winPos[1]] &&
                            gameState[winPos[1]] == gameState[winPos[2]] &&
                            gameState[winPos[0]] != 0) {
                        playerWon = playerTurn;

                        String player = "Nguoi chien thang la ";
                        if (playerWon == PlayerTurn.PLAYER_ONE) {
                            player += "player One";
                            p1_score++;
                        } else {
                            player += "player Two";
                            p2_score++;
                        }
                        updateUI();
                        winner_text.setText(player);
                        isPaused = true;
                        return;
                    }
                }
                playerTurn = playerTurn == PlayerTurn.PLAYER_ONE ? PlayerTurn.PLAYER_TWO : PlayerTurn.PLAYER_ONE;
            }
        });
    }

    private void resetBtn() {
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPaused = false;
                reset();
                updateUI();
            }
        });
    }
}