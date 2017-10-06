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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText pass;
    private EditText name;
    private EditText username;
    private TextView login;
    private Button register;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Intent myintent;
    private Intent new_intent;

    DatabaseReference myRef ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        myRef = FirebaseDatabase.getInstance().getReference("user");
        myintent = new Intent(RegisterActivity.this, LoginActivity.class);
        new_intent = new Intent(RegisterActivity.this, MainActivity.class);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        register = (Button) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.login);

        register.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == register) {
            registerUser();
        }
        if (v == login) {
            startActivity(myintent);
        }
    }

    private void registerUser() {

        final String correo = email.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        final String nombre = name.getText().toString().trim();
        final String usuario = username.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) ) {
            Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(usuario)) {
            Toast.makeText(this, "El nombre de usuario es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "El correo es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "El password es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registrando usuario...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = df.format(c.getTime());

                    FirebaseUser id_guardado= firebaseAuth.getCurrentUser();
                    String id = myRef.push().getKey();
                    User user = new User(id, id_guardado.getUid(),nombre,usuario,correo,password,formattedDate);
                    myRef.child(usuario).setValue(user);
                    Toast.makeText(RegisterActivity.this, "Usuario Guardado", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                    finish();
                    startActivity(new_intent);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
