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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText pass;
    private TextView login;
    private Button register;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private  Intent myintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        myintent = new Intent(RegisterActivity.this, LoginActivity.class);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        register = (Button) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.login);

        register.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==register){
            registerUser();
        }
      if(v==login){
         startActivity(myintent);
      }
    }

    private void registerUser(){

        String correo = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if(TextUtils.isEmpty(correo)){
            Toast.makeText(this, "El correo es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "El password es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registrando usuario...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(myintent);
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }
}
