package com.example.talkpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";// JHJHjh

    EditText etId, etPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etId=(EditText)findViewById(R.id.etId);
        etPassword =(EditText)findViewById(R.id.etPassword);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);
       // String stPassword=etPassword.getText().toString();

        Button btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener((v)->{
            String stEmail=etId.getText().toString();
            String stPassword=etPassword.getText().toString();
            if(stEmail.isEmpty()){
                Toast.makeText(MainActivity.this,"please insert Email",Toast.LENGTH_LONG).show();
                return;
            }
            if(stPassword.isEmpty()){
                Toast.makeText(MainActivity.this,"please insert Password",Toast.LENGTH_LONG).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(stEmail, stPassword)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        String stUserEmail=user.getEmail();
                        String stUserName=user.getDisplayName();
                        Log.d(TAG,"stUserEmail: "+stUserEmail+"stUesrName"+stUserName);
                        Intent in=new Intent(MainActivity.this,ChatActivity.class);
                        in.putExtra("email",stEmail);
                        startActivity(in);
                        // updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        // updateUI(null);
                    }

                    // ...
                }
            });
             //   Toast.makeText(MainActivity.this,"Login",Toast.LENGTH_LONG).show(); // 말풍선으로 로그인 표시
        });

        Button btnRegister =(Button)findViewById(R.id.btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stEmail=etId.getText().toString();
                String stPassword=etPassword.getText().toString();
                if(stEmail.isEmpty()){
                    Toast.makeText(MainActivity.this,"please insert Email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(stPassword.isEmpty()){
                    Toast.makeText(MainActivity.this,"please insert Password",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
             //   Toast.makeText(MainActivity.this,"Email"+stEmail+", password: "+stPassword, Toast.LENGTH_LONG).show();
                mAuth.createUserWithEmailAndPassword(stEmail, stPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //  updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //  updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });


    }
    
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //    updateUI(currentUser);
    }
}