package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.Facility;
import model.Reservation;

public class ReservationDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_detail);

        final TextView tvresid = findViewById(R.id.tvresid);
        final TextView tvftype = findViewById(R.id.tvftype);
        final TextView tvfname = findViewById(R.id.tvfname);
        final TextView tvdeposit = findViewById(R.id.tvdeposit);
        final EditText etdate = findViewById(R.id.etdate);
        final EditText etstime = findViewById(R.id.etstime);
        final EditText etetime = findViewById(R.id.etetime);

        final Button btupdate = findViewById(R.id.btupdate);
        final Button btdelete = findViewById(R.id.btdelete);


        long resid = Long.parseLong(getIntent().getExtras().getString("RESID"));
        final Reservation res = Reservation.getInstance(resid);
        if (res != null){
            String fname = res.getFacName();
            Facility f = Facility.getInstance(fname,getApplicationContext());
            String ftype = f.getFtype().getFdesc();

            tvresid.setText(String.valueOf(res.getResId()));
            tvftype.setText(ftype);
            tvdeposit.setText(String.valueOf(f.getDeposit()));
            etdate.setText(res.getDate());
            etstime.setText(res.getStime());
            etetime.setText(res.getEtime());
        }

        btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                res.delete();
                Toast toast = Toast.makeText(getApplicationContext(), "Reservation Deleted!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
                Intent intent = new Intent(getApplicationContext(),UserReservationList.class);
                startActivity(intent);
            }
        });

        btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDate = String.valueOf(etdate.getText());
                String newStime = String.valueOf(etstime.getText());
                res.update(newDate,newStime);

                Toast toast = Toast.makeText(getApplicationContext(), "Reservation Modified!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
                Intent intent = new Intent(getApplicationContext(),UserReservationList.class);
                startActivity(intent);
            }
        });
    }
}
