package com.raymaso.authusers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText pass;
    private TextView register;
    private Button login;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Intent myintent;
    private Intent new_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);

        myintent = new Intent(LoginActivity.this, RegisterActivity.class);
        new_intent = new Intent(LoginActivity.this, MainActivity.class);

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new_intent);
        }
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            userLogin();
        }
        if (v == register) {
            finish();
            startActivity(myintent);
        }

    }

    private void userLogin() {
        String correo = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "El correo es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "El password es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Validando usuario...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new_intent);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Email o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
