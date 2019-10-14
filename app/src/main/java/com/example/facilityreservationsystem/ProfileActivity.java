package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import model.SysUser;

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
        final Button btncancel = findViewById(R.id.btcancel);

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
                currUser.setPassword(password.getText().toString());
                currUser.setFname(fname.getText().toString());
                currUser.setLname(lname.getText().toString());
                currUser.setUtaid(Integer.parseInt(utaid.getText().toString()));
                currUser.setVehicle(vehicle.getText().toString());
                currUser.setPhone(Integer.parseInt(phone.getText().toString()));
                currUser.setParking(Integer.parseInt(permit.getText().toString()));

                currUser.updateProfile(getApplicationContext());
                finish();
//                Intent intent = new Intent(getApplicationContext(),)
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (currUser.getRole()){
                    case "AD":
                        intent = new Intent(getApplicationContext(),AdminHomeScreen.class);
                        break;
                    case "UR":
                        intent = new Intent(getApplicationContext(),UserHomeScreen.class);
                        break;
                    case "FM":
                        intent = new Intent(getApplicationContext(),FacilityManagerHomeScreen.class);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + currUser.getRole());
                }

                startActivity(intent);

            }
        });

    }
}
