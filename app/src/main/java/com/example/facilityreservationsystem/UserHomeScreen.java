package com.example.facilityreservationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import model.SysUser;

public class UserHomeScreen extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_logout:
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = settings.getString("username","");
        SysUser currSysUser = SysUser.getUser(username, getApplicationContext());

        Intent intent = getIntent();
//        String username = intent.getStringExtra("username");
        TextView textv = findViewById(R.id.etusername);

        textv.append(username);

        final Button btprofile = findViewById(R.id.btprofile);
        btprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);

            }
        });

        final  Button btreqres = findViewById(R.id.btreqres);
        btreqres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserFacilitySearch.class);
                startActivity(intent);
            }
        });

        final Button btres = findViewById(R.id.btres);
        btres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserReservationList.class);
                startActivity(intent);
            }
        });

    }
}
