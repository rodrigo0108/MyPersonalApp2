package com.rramos.mypersonalapp2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rramos.mypersonalapp2.R;
import com.rramos.mypersonalapp2.models.User;
import com.rramos.mypersonalapp2.repositories.UserRepository;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private EditText usernameInput;
    private EditText passwordInput;
    private ProgressBar progressBar;
    private View loginPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = (EditText)findViewById(R.id.username_input);
        passwordInput = (EditText)findViewById(R.id.password_input);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        loginPanel = findViewById(R.id.login_panel);

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // username remember
        String username = sharedPreferences.getString("username", null);
        if(username != null){
            usernameInput.setText(username);
            passwordInput.requestFocus();
        }

        // islogged remember
        if(sharedPreferences.getBoolean("islogged", false)){
            // Go to Dashboard
            goDashboard();
        }
    }
    public void callLogin(View view){
        loginPanel.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Login logic
        int estado = UserRepository.login(username, password);
        User user = null;
        /*ser user = UserRepository.login(username, password);
        User userem= UserRepository.validaEmail(user.getEmail());*/

        if(estado==0){
            user=UserRepository.create(username,username,password);
        }else if(estado==2){
            Toast.makeText(this, "Contraseña incorrecta ", Toast.LENGTH_SHORT).show();
            loginPanel.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }else{
            user = UserRepository.read((long) estado);
        }
/*
        if(user == null){

            user=UserRepository.create(username,username,password);
            Toast.makeText(this, "Bienvenido usuario nuevo: " + user.getFullname(), Toast.LENGTH_SHORT).show();

        }*/

        Toast.makeText(this, "Bienvenido " + user.getFullname(), Toast.LENGTH_SHORT).show();

        // Save to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean success = editor
                .putString("id", user.getId().toString())
                .putBoolean("islogged", true)
                .commit();

        // Go to Dashboard
        goDashboard();


    }

    private void goDashboard(){
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
