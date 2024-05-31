package com.example.ludo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

    }
    View view(int id) {
        return findViewById(id);
    }

    public void onClick(View v) {
        if(v == view(R.id.register)) {
            Intent in = new Intent(this, RegisterActivity.class);
            startActivity(in);
        }
        else if(v == view(R.id.login)) {
            String email = ((EditText)view(R.id.email)).getText().toString();
            String password = ((EditText)view(R.id.password)).getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            Pattern pattern = Pattern.compile("[A-Za-z][A-Za-z0-9]*@gmail.com");
            if(!Pattern.matches(pattern.toString(), email)) {
                builder.setMessage("Enter correct Email!");
                builder.show();
            }
            else if(password.length() < 6) {
                builder.setMessage("Password length should be atleast six!");
                builder.show();
            }
            else if(password.contains(" ")) {
                builder.setMessage("Space should be not present in Password!");
                builder.show();
            }
            else {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent in = new Intent(LoginActivity.this, MainActivity2.class);
                            startActivity(in);
                            finish();
                        }
                        else {
                            builder.setMessage("Email or Password is incorrect!");
                            builder.show();
                        }
                    }
                });
            }
        }
    }
}