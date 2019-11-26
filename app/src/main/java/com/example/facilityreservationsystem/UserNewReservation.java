package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import model.Facility;
import model.Reservation;

public class UserNewReservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_new_reservation);

        final TextView tvresid = findViewById(R.id.tvresid);
        final TextView tvftype = findViewById(R.id.tvftype);
        final TextView tvfname = findViewById(R.id.tvfname);
        final TextView tvdeposit = findViewById(R.id.tvdeposit);
        final TextView tvdate = findViewById(R.id.tvdate);
        final TextView tvstime = findViewById(R.id.tvstime);
        final TextView tvetime = findViewById(R.id.tvetime);
        final TextView tvuname = findViewById(R.id.tvuname);

        long resid = Long.parseLong(getIntent().getExtras().getString("RESID"));
        final Reservation res = Reservation.getInstance(resid);
        if (res != null) {
            String fname = res.getFacName();
            Facility f = Facility.getInstance(fname, getApplicationContext());
            String ftype = f.getFtype().getFdesc();

            tvfname.setText(fname);

            tvresid.setText(String.valueOf(res.getResId()));
            tvftype.setText(ftype);
            tvdeposit.setText(String.valueOf(f.getDeposit()));
            tvdate.setText(res.getDate());
            tvstime.setText(res.getStime());
            tvetime.setText(res.getEtime());
            tvuname.setText(res.getUsername());
        }
    }
}
