package com.example.tictactoe;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText edtEmail, edtFullName, edtPhoneNum, edtPassword;
    Button registerBtn;
    FirebaseAuth fAuth;
    TextView signIn;
    FirebaseFirestore fStore;
    String userID;

    interface RequestUser {
//        @GET("/users/{uid}")
//        Call<Data> getUser(@Path("uid") String uid);
        @POST("/users/")
        Call<ResponsePost> postUser(@Body RequestPost requestPost);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.email);
        edtFullName = findViewById(R.id.fullName);
        edtPhoneNum = findViewById(R.id.numberPhone);
        edtPassword = findViewById(R.id.password);
        registerBtn = findViewById(R.id.signUpBtn);
        signIn = findViewById(R.id.signInBtn);

        FirebaseApp.initializeApp(this);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://a53a-2402-800-6327-b583-c57c-7007-dd8f-1bde.ngrok-free.app").addConverterFactory(GsonConverterFactory.create()).build();
        RequestUser requestUser = retrofit.create(RequestUser.class);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, fullName, phoneNum, password;

                email = edtEmail.getText().toString();
                fullName = edtFullName.getText().toString();
                phoneNum = edtPhoneNum.getText().toString();
                password = edtPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(RegisterActivity.this, "Enter full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phoneNum)) {
                    Toast.makeText(RegisterActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
                                    userID = fAuth.getCurrentUser().getUid();
                                    requestUser.postUser(new RequestPost(email, fullName, phoneNum, password)).enqueue(new Callback<ResponsePost>() {
                                        @Override
                                        public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
                                        }

                                        @Override
                                        public void onFailure(Call<ResponsePost> call, Throwable t) {
                                        }
                                    });
                                    DocumentReference documentReference = fStore.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("email", email);
                                    user.put("fullname", fullName);
                                    user.put("phone", phoneNum);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: user profile is created for " + userID);
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

}