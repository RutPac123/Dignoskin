package com.example.dignoskin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterUser extends AppCompatActivity {
    private EditText emailedt;
    private EditText passedt;
    private EditText passedtagain;
    private Button signUpbtn;
    private FirebaseAuth mAuth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        emailedt = findViewById(R.id.emailsignup);
        passedt = findViewById(R.id.passsignup);
        passedtagain = findViewById(R.id.passsignupagain);
        signUpbtn = findViewById(R.id.signupbtn);
        mAuth1 = FirebaseAuth.getInstance();


        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailedt.getText().toString().isEmpty() || !passedt.getText().toString().isEmpty() || !passedtagain.getText().toString().isEmpty()) {
                    signUp();
                } else {
                    Toast.makeText(RegisterUser.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void signUp() {
        String email = emailedt.getText().toString();
        String password = passedt.getText().toString();
        String passagain = passedtagain.getText().toString();
        if (passagain.equals(password)) {
            mAuth1.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterUser.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }
    }
}