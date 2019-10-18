package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import model.Facility;
import model.Reservation;
import model.SysUser;
import model.User;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final Button btpay = findViewById(R.id.btpay);
        btpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String username = settings.getString("username"," ");
                User user = (User) SysUser.getUser(username,getApplicationContext());
                Facility f = Facility.getInstance(bundle.getString("FACILITY"),getApplicationContext());
                Reservation reservation = new Reservation(getApplicationContext(),user,f,bundle.getString("DATE"),bundle.getString("STIME"),bundle.getString("ETIME"));
                reservation.save();

                Toast toast = Toast.makeText(getApplicationContext(), "Reservation sucessfully created!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();

                Intent intent = new Intent(getApplicationContext(),UserFacilitySearch.class);
                startActivity(intent);
            }
        });

    }
}
