package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import model.Facility;
import model.Reservation;

public class NoShowViolationDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_show_violation_detail);

        final TextView tvresid = findViewById(R.id.tvresid);
        final TextView tvdate = findViewById(R.id.tvdate);
        final TextView tvtime = findViewById(R.id.tvtime);
        final TextView tvfname = findViewById(R.id.tvfname);
        final TextView tvftype = findViewById(R.id.tvftype);
        final TextView tvdeposit = findViewById(R.id.tvdeposit);
        final TextView tvnsvio = findViewById(R.id.tvnsvio);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = settings.edit();
        final String fname,ftype,resDate,resTime,deftype,depo;
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            long resid = bundle.getLong("RESID");
            Reservation res = Reservation.getInstance(resid);
            resDate = res.getDate();
            resTime = res.getStime();
            fname = res.getFacName();
            Facility f = Facility.getInstance(fname,getApplication());
            ftype = f.getFtype().getFdesc();
            depo = Integer.toString(f.getDeposit());
            deftype = res.getNoshowViolationStatus();

            tvresid.setText(Long.toString(resid));
            tvdate.setText(resDate);
            tvtime.setText(resTime);
            tvfname.setText(fname);
            tvftype.setText(ftype);
            tvdeposit.setText(depo);
            tvnsvio.setText(deftype);
        }
    }
}
