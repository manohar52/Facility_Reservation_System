package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        SysUser currUser = SysUser.getUser(username,getApplicationContext());

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

    }
}
