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

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = settings.edit();
        final String fname,resDate,resTime;
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            fname = bundle.getString("selectedFacility");
            resDate = bundle.getString("resDate");
            resTime = bundle.getString("resTime");

            editor.putString("selectedFacility", fname);
            editor.putString("resDate", resDate);
            editor.putString("resTime", resTime);
            editor.commit();
        }else{
            fname = settings.getString("selectedFacility","");
            resDate = settings.getString("resDate","");
            resTime = settings.getString("resTime","");
        }

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

        final String finalEtime = etime;
        btreserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Payment.class);
                Bundle bundle = new Bundle();
                bundle.putString("DATE",resDate);
                bundle.putString("STIME",resTime);
                bundle.putString("ETIME", finalEtime);
                bundle.putString("FACILITY",fname);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
