package com.example.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HelloUser extends AppCompatActivity {

    private ImageView logOut;
    private TextView fullName;
    private TextView email;
    private TextView phone;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_user);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        mapping();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                email.setText(value.getString("email"));
                fullName.setText(value.getString("fullname"));
                phone.setText(value.getString("phone"));
            }
        });
    }
    private void mapping() {
        logOut = findViewById(R.id.logoutBtn);
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }
}