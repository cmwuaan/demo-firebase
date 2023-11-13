package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class LobbyActivity extends AppCompatActivity {
    ImageView logoutBtn;
    private FirebaseAuth mAuth;
    private RadioGroup radioGroup;

    private  RadioButton createLobby;
    private RadioButton joinLobby;

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        mAuth = FirebaseAuth.getInstance();
        mapping();
        logout();
        selectLobby();
    }
    private void mapping() {
        logoutBtn = findViewById(R.id.logoutBtn);
        radioGroup = findViewById(R.id.radioGroup);
        createLobby = findViewById(R.id.createLobby);
        joinLobby = findViewById(R.id.joinLobby);
        nextBtn = findViewById(R.id.nextBtn);
    }

    private void logout() {
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectLobby() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radId =radioGroup.getCheckedRadioButtonId();

                if (radId == R.id.joinLobby) {
                    startActivity(new Intent(getApplicationContext(), JoinActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), PlayerNameActivity.class));
                    finish();
                }
            }
        });
    }
}