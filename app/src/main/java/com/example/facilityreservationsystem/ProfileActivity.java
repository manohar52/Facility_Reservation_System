package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import model.SysUser;
import utils.AlertBox;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String username = settings.getString("username","");
        final SysUser currUser = SysUser.getUser(username,getApplicationContext());

        final TextView tvusername = findViewById(R.id.tvusername);
        final EditText password = findViewById(R.id.etpassword);
        final EditText fname = findViewById(R.id.etfname);
        final EditText lname = findViewById(R.id.etlname);
        final EditText utaid = findViewById(R.id.etutaid);
        final EditText vehicle = findViewById(R.id.etvehicle);
        final EditText phone = findViewById(R.id.etphone);
        final EditText permit = findViewById(R.id.etpermit);

        final Button btnupdate = findViewById(R.id.btupdate);

        tvusername.setText(username);
        password.setText(currUser.getPassword());
        fname.setText(currUser.getFname());
        lname.setText(currUser.getLname());
        utaid.setText((String.valueOf(currUser.getUtaid())));
        vehicle.setText(currUser.getVehicle());
        phone.setText(String.valueOf(currUser.getPhone()));
        permit.setText(String.valueOf(currUser.getParking()));


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        AlertBox ab = new AlertBox("Do you wish to update the profile details?");
        ab.showOKDialog(ProfileActivity.this, new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which) {

            currUser.setPassword(password.getText().toString());
            currUser.setFname(fname.getText().toString());
            currUser.setLname(lname.getText().toString());
            currUser.setUtaid(Integer.parseInt(utaid.getText().toString()));
            currUser.setVehicle(vehicle.getText().toString());
            currUser.setPhone(Integer.parseInt(phone.getText().toString()));
            currUser.setParking(Integer.parseInt(permit.getText().toString()));

            currUser.updateProfile(getApplicationContext());

            Toast toast = Toast.makeText(getApplicationContext(), "Profile Updated!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
          }
        });

            }
        });
    }
}
