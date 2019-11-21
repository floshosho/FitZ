package com.example.fitz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    private final static String TAG = CreateAccountActivity.class.getSimpleName();
    private FirebaseAuth mAuth;

    // TODO Start this when we add the main menu
    // When main activity starts, check if user is currently signed in
    // if so, then redirect to main menu activity, otherwise goto sign in activity
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        // update UI
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        Button register = findViewById(R.id.button_create_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.editText_email2);
                EditText password = findViewById(R.id.editText_password2);
                EditText confPassword = findViewById(R.id.editText_confirm_password);
                createAccount(email.getText().toString(), password.getText().toString(),
                        confPassword.getText().toString());
            }
        });
    }

    private void createAccount(String email, String password, String confPassword) {
        try {
            // Make sure credentials a
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
                throw new FirebaseAuthInvalidCredentialsException(TAG, "One or more fields are empty or null");
            }
            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "Your password is too short", Toast.LENGTH_SHORT).show();
                throw new FirebaseAuthInvalidCredentialsException(TAG, "Password < 6 characters");
            }
            if (!password.equals(confPassword)) {
                Toast.makeText(getApplicationContext(), "Your passwords don't match", Toast.LENGTH_SHORT).show();
                throw new FirebaseAuthInvalidCredentialsException(TAG, "Password strings are not equal");
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "Welcome to FitZ!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainMenuActivity.class)); //TODO Give MainMenuActivity the FirebaseUser obj through Intent
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.i(TAG, e.getStackTrace().toString());
        }
    }
}

