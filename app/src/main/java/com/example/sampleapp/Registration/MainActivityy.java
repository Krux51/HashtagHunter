package com.example.sampleapp.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleapp.MainActivity;
import com.example.sampleapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityy extends AppCompatActivity implements  View.OnClickListener {
    private TextView register,forgotpassword;
    private EditText editTextEmail,editTextPassword;
    private Button signin;
    private FirebaseAuth mAuth;
    private ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityy);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
        signin=(Button)findViewById(R.id.signin);
        signin.setOnClickListener(this);
        editTextPassword=(EditText)findViewById(R.id.password);
        editTextEmail=(EditText)findViewById(R.id.email);
        progressbar=(ProgressBar)findViewById(R.id.progressbar);
        forgotpassword=(TextView)findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();


        }

    @Override
    public void onClick(View v) {
        // Perform action on click
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.signin:
                userlogin();
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this,Forgotpassword.class));
                break;
        }


    }

    private void userlogin() {



            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if (email.isEmpty()) {
                editTextEmail.setError("Email is required");
                editTextEmail.requestFocus();
                return;
            }
            if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                editTextEmail.setError("Please enter a valid Email Address!");
                editTextEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                editTextPassword.setError("Password is required!");
                editTextPassword.requestFocus();
                return;
            }
            if (password.length() < 6) {
                editTextPassword.setError("Minimum password length is 6 characters!");
                editTextPassword.requestFocus();
                return;

            }

            progressbar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                        if (User.isEmailVerified()) {

                            startActivity(new Intent(MainActivityy.this, MainActivity.class));
                        } else {
                            User.sendEmailVerification();
                            Toast.makeText(MainActivityy.this, "Check your email to verifiy your account!", Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(MainActivityy.this, "Failed to login ! Please check your credentials!", Toast.LENGTH_LONG).show();
                        progressbar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
