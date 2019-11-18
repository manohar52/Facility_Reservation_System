package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import errorlog.AppLog;
import model.SysUser;


public class LoginActivity extends AppCompatActivity {

    private SysUser currUser;
    @Override
    public void onBackPressed() {
        if (currUser != null){
            currUser.logout();
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username = findViewById(R.id.etusername);
        final EditText password = findViewById(R.id.password);

        final Button loginButton = findViewById(R.id.loginbtn);
        final Button regButton = findViewById(R.id.btregister);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            currUser = SysUser.getUser(username.getText().toString(),getApplicationContext());
            Intent intent;
            boolean status = currUser.login(password.getText().toString());
                if(status){ //Login Successful
                    Toast toast = Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    switch (currUser.getRole()){
                        case "AD":
                            intent = new Intent(getApplicationContext(), AdminHomeScreen.class);
                            break;
                        case "UR":
                            intent = new Intent(getApplicationContext(), UserHomeScreen.class);
                            break;
                        case "FM":
                            intent = new Intent(getApplicationContext(), FacilityManagerHomeScreen.class);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + currUser.getRole());
                    }
                    intent.putExtra("username", username.getText().toString());
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "No user found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
            }
        });
    }
}
