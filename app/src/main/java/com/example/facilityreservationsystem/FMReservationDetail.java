package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.Facility;
import model.Reservation;
import utils.AlertBox;

public class FMReservationDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmreservation_detail);

        final TextView tvresid = findViewById(R.id.tvresid);
        final TextView tvftype = findViewById(R.id.tvftype);
        final TextView tvfname = findViewById(R.id.tvfname);
        final TextView tvdeposit = findViewById(R.id.tvdeposit);
        final TextView tvdate = findViewById(R.id.tvdate);
        final TextView tvstime = findViewById(R.id.tvstime);
        final TextView tvetime = findViewById(R.id.tvetime);
        final TextView tvuname = findViewById(R.id.tvuname);
        final CheckBox cbnoshow = findViewById(R.id.cbnoshow);
        final CheckBox cbviolation = findViewById(R.id.cbviolation);

        final Button btdelete = findViewById(R.id.btdelete);
        final Button btnoshow = findViewById(R.id.btnoshow);
        final Button btviolation = findViewById(R.id.btviolation);

        long resid = Long.parseLong(getIntent().getExtras().getString("RESID"));
        final Reservation res = Reservation.getInstance(resid);
        if (res != null){
            String fname = res.getFacName();
            Facility f = Facility.getInstance(fname,getApplicationContext());
            String ftype = f.getFtype().getFdesc();

            tvfname.setText(fname);

            tvresid.setText(String.valueOf(res.getResId()));
            tvftype.setText(ftype);
            tvdeposit.setText(String.valueOf(f.getDeposit()));
            tvdate.setText(res.getDate());
            tvstime.setText(res.getStime());
            tvetime.setText(res.getEtime());
            tvuname.setText(res.getUsername());
            if(res.getNoshow().equals("1")){
              cbnoshow.setChecked(true);
              btnoshow.setEnabled(false);
            }
            if(res.getViolation().equals("1")){
                cbviolation.setChecked(true);
                btviolation.setEnabled(false);
            }
        }


        btnoshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Do you wish to report a no-show?";
                AlertBox ab = new AlertBox(msg);
                ab.showOKDialog(FMReservationDetail.this, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        res.setNoshow("1");
                        res.update();
                        cbnoshow.setChecked(true);
                        Toast toast = Toast.makeText(getApplicationContext(), "No-show Reported!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    }
                });
            }
        });

        btviolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Do you wish to report a Violation?";
                AlertBox ab = new AlertBox(msg);
                ab.showOKDialog(FMReservationDetail.this, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        res.setViolation("1");
                        res.update();
                        cbviolation.setChecked(true);
                        Toast toast = Toast.makeText(getApplicationContext(), "Violation Reported!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    }
                });
            }
        });


        btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        String msg = "The Following Reservation will be deleted:\n";
        msg = msg+res.getUsername()+"\n"+res.getDate()+"\n"+res.getStime()+"\n"+res.getEtime();
        AlertBox ab = new AlertBox(msg);
        ab.showOKDialog(FMReservationDetail.this, new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which) {
            res.delete();
            Toast toast = Toast.makeText(getApplicationContext(), "Reservation Deleted!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            Intent intent = new Intent(getApplicationContext(),FMViewReseervationList.class);
            startActivity(intent);
        }
        });

            }
        });


    }
}
