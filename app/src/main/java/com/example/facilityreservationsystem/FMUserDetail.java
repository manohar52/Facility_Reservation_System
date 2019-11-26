package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import model.SysUser;

public class FMUserDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmuser_detail);

        Bundle bundle = getIntent().getExtras();
        final String uname = bundle.getString("SUSER");
        final SysUser currUser = SysUser.getUser(uname,getApplicationContext());

        final TextView tvusername = findViewById(R.id.tvusername);

        final TextView fname = findViewById(R.id.tvfname);
        final TextView lname = findViewById(R.id.tvlname);
        final TextView utaid = findViewById(R.id.tvutaid);
        final TextView noshow = findViewById(R.id.tvnoshow);
        final TextView vio = findViewById(R.id.tvvio);

        tvusername.setText(uname);

        fname.setText(currUser.getFname());
        lname.setText(currUser.getLname());
        utaid.setText((String.valueOf(currUser.getUtaid())));

        noshow.setText(Integer.valueOf(currUser.getNoOfNoShows()).toString());
        vio.setText(Integer.valueOf(currUser.getNoOfViolations()).toString());

    }
}
