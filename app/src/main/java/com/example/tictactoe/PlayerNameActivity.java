package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PlayerNameActivity extends AppCompatActivity {

    private ImageView backBtn;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);
        mapping();
        backToPrevious();
        goToNextPage();
    }

    private void mapping() {
        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
    }

    private void backToPrevious() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerNameActivity.this, LobbyActivity.class));
            }
        });
    }

    private void goToNextPage() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
            }
        });
    }
}