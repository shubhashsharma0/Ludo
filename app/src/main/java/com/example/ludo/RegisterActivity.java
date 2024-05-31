package com.example.ludo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    boolean b = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        view(R.id.name).setOnClickListener(this);
        view(R.id.gender).setOnClickListener(this);
        view(R.id.dob).setOnClickListener(this);
        view(R.id.email).setOnClickListener(this);
        view(R.id.password).setOnClickListener(this);
        view(R.id.confirmpassword).setOnClickListener(this);
        view(R.id.submit).setOnClickListener(this);
    }

    View view(int v) {
        return findViewById(v);
    }
    public void onClick(View v) {

        String name = ((EditText)view(R.id.name)).getText().toString().trim();
        String gender = ((EditText)view(R.id.gender)).getText().toString().trim();
        String dob = ((EditText)view(R.id.dob)).getText().toString();
        String email = ((EditText)view(R.id.email)).getText().toString().trim();
        String password = ((EditText)view(R.id.password)).getText().toString().trim();
        String confirmPassword = ((EditText)view(R.id.confirmpassword)).getText().toString().trim();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(b) {
                    finish();
                }
            }
        });

        Pattern pattern = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*@gmail.com");


        if(v == view(R.id.submit)) {
            if(!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))) {
                builder.setMessage("Gender should be either Male or Female!");
                builder.show();
            }
            else if(!Pattern.matches("(([0-2][0-9])|(3[0-1]))/((0[0-9])|(1[0-2]))/(19|(2[0-9]))[0-9]{2}", dob)) {
                builder.setMessage("Enter correct Date!");
                builder.show();
            }
            else if(!email.matches(pattern.toString())) {
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
            else if(!password.equals(confirmPassword)) {
                builder.setMessage("Confirm Password and Password do not match!");
                builder.show();
            }
            else {
                (view(R.id.submit)).setClickable(false);
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                DatabaseReference reference = rootNode.getReference("users");
                RegistrationDetails rd = new RegistrationDetails(name, gender, dob, email, password, confirmPassword);

                String userName = email.substring(0,email.length() - 10);
                reference.child(userName).setValue(rd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }
                        else {
                                b = true;
                                builder.setMessage("Registration Unsuccessful!");
                                builder.show();
                            }
                            }
                        });
                    }
                });

            }
        }
    }
}