package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class JoinActivity extends AppCompatActivity {
    private ImageView backBtn;
    private final EditText playerName = findViewById(R.id.userName);
    private final AppCompatButton joinBtn = findViewById(R.id.joinBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mapping();
        backToPrevious();
        joinTheGame();
    }

    private void mapping() {
        backBtn = findViewById(R.id.backBtn);
    }

    private void backToPrevious() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JoinActivity.this, LobbyActivity.class));
            }
        });
    }

    private void joinTheGame() {
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getPlayerName = playerName.getText().toString();

                if (getPlayerName.isEmpty()) {
                    Toast.makeText(JoinActivity.this, "Please enter player name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                    intent.putExtra("playerName", getPlayerName);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}