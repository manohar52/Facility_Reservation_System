package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UserReservationSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation_search);

        final EditText etdate = findViewById(R.id.etdate);
        final EditText ettime = findViewById(R.id.ettime);

        Calendar cal = new GregorianCalendar();

        SimpleDateFormat df = new SimpleDateFormat(("MM/dd/yyyy"));
        final String currDate = df.format(cal.getTime());
        etdate.setText(currDate);

        SimpleDateFormat tf = new SimpleDateFormat(("hh:mm aa"));
        String currTime = tf.format(cal.getTime());
        ettime.setText(currTime);

        final Button btsearch = findViewById(R.id.btsearch);
        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rTime = ettime.getText().toString();
                String rDate = etdate.getText().toString();

                Intent intent = new Intent(getApplicationContext(),UserReservationList.class);

                Bundle bundle = new Bundle();
                bundle.putString("rdate",rDate);
                bundle.putString("rtime",rTime);

                intent.putExtras(bundle);
                startActivity(intent);;
            }
        });

    }
}
