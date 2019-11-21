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

public class SignInActivity extends AppCompatActivity {

    private final static String TAG = SignInActivity.class.getSimpleName();
    private FirebaseAuth mAuth;

    // TODO Start this when we add the main menu
    // When main activity starts, check if user is currently signed in
    // if so, then redirect to main menu activity, otherwise goto sign in activity
//    @Override
//    public void onStart() {
//        // Handle error cases here eventually
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();

        Button login = findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.editText_email); // get email from EditText obj
                EditText password = findViewById(R.id.editText_password); // get password from EditText obj
                signInUser(email.getText().toString(), password.getText().toString()); // Attempt to sign in the user
            }
        });

        Button createAccount = findViewById(R.id.button_create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Create Account button pressed");
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });

    }

    private void signInUser(String email, String password) {
        try {
            // Make sure credentials a
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                throw new FirebaseAuthInvalidCredentialsException(TAG, "One or both credentials is empty or null");
            }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.i(TAG, e.getStackTrace().toString());
        }
    }

}
