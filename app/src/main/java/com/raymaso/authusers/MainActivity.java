package com.raymaso.authusers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView data;
    private Button logout;
    private FirebaseAuth firebaseAuth;
    private Intent loginactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        loginactivity = new Intent(MainActivity.this, LoginActivity.class);

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(loginactivity);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        data = (TextView) findViewById(R.id.data);
        logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(this);

        data.setText("Bienvenido " + user.getEmail());
    }

    @Override
    public void onClick(View v) {
        if (v == logout) {
            finish();
            firebaseAuth.signOut();
            startActivity(loginactivity);


        }
    }
}
