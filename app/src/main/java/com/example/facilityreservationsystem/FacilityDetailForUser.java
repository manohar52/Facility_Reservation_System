package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import model.Facility;

public class FacilityDetailForUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_detail_for_user);

        String etime = null;

        final TextView tvdate = findViewById(R.id.tvdate);
        final TextView tvstime = findViewById(R.id.tvstime);
        final TextView tvetime = findViewById(R.id.tvetime);
        final TextView tvname = findViewById(R.id.tvname);
        final TextView tvftype = findViewById(R.id.tvftype);
        final TextView tvdeposit = findViewById(R.id.tvdeposit);
        final Button btreserve = findViewById(R.id.btreserve);

        Bundle bundle = getIntent().getExtras();
        String fname = bundle.getString("selectedFacility");
        String resDate = bundle.getString("resDate");
        String resTime = bundle.getString("resTime");



        Facility f = Facility.getInstance(fname,getApplication());

        tvdate.setText(resDate);
        tvstime.setText(resTime);

        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        try {
            Date temp = df.parse(resTime);
            Calendar cal = new GregorianCalendar();
            cal.setTime(temp);
            float interval = f.getFtype().getInterval();
            if ( interval < 1){
                int min = (int)(interval*60);
                cal.add(Calendar.MINUTE,min);
            }else{
                cal.add(Calendar.HOUR, (int) interval);
            }
            Date temptime = cal.getTime();
            etime = df.format(temptime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        tvetime.setText(etime);
        tvname.setText(f.getName());
        tvftype.setText(f.getFtype().getFdesc());
        tvdeposit.setText("$"+Integer.toString(f.getDeposit()));

        btreserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
