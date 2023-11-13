package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RoomActivity extends AppCompatActivity {

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        mapping();
        backToPrevious();
    }

    private void mapping() {
        backBtn = findViewById(R.id.backBtn);
    }

    private void backToPrevious() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoomActivity.this, PlayerNameActivity.class));
            }
        });
    }
}