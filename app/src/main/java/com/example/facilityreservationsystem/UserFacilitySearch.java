package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import DOA.facility_doa;
import model.Facility;
import model.FacilityType;

public class UserFacilitySearch extends AppCompatActivity {

//    private String nextSlotTime(String time) throws ParseException {
//        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
//        Date date = df.parse(time);
//        Calendar c = new GregorianCalendar();
//        c.setTime(date);
//        c.add(Calendar.HOUR,);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
//        String[] comp1 = time.split(":");
//        String[] comp2 = comp1[1].split(" ");
//
//        int hh = Integer.parseInt(comp1[0]);
////        String mm = comp2[0];
//        int mm = Integer.parseInt(comp2[0]);
//        String aa = comp2[1];
//        String smm;
////        hh = hh + 1;
//        if (mm > 0 && mm < 30) {
//            mm = ;
//        }
//        switch (aa) {
//            case "am":
//                if (hh < 6) {
//                    hh = 6;
//                }
//                if (hh == 12) {
//                    aa = "pm";
//                }
//                break;
//            case "pm":
//                if (hh >= 12) {
//                    hh = 6;
//                    aa = "am";
//                }
//        }
//        String shh;
//        if (hh < 10) {
//            shh = String.format("0%s", hh);
//        } else {
//            shh = String.format("%s", hh);
//        }
//        return shh + ":" + mm + " " + aa;
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_facility_search);

        final EditText etdate = findViewById(R.id.etdate);
        final EditText ettime = findViewById(R.id.ettime);
        final Spinner venue = findViewById(R.id.spvenue);
        final Spinner type = findViewById(R.id.sptype);

        Calendar cal = new GregorianCalendar();

        SimpleDateFormat df = new SimpleDateFormat(("MM/dd/yyyy"));
        final String currDate = df.format(cal.getTime());
        etdate.setText(currDate);

        SimpleDateFormat tf = new SimpleDateFormat(("hh:mm aa"));
        String currTime = tf.format(cal.getTime());
//        final String nextSlot = nextSlotTime(currTime);
        ettime.setText(currTime);

        final facility_doa fdoa = facility_doa.getInstance(getApplicationContext());
        final ArrayList<String> facilityTypes = fdoa.getAllFacilityTypes();
//        Set<String> keys = facilityTypes.keySet();
//        List<String> typeArray = new ArrayList<String>();
//        for (String k : keys) {
//            typeArray.add(k);
//        }
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, facilityTypes);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);

        final Button btSearch = findViewById(R.id.btsearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fdesc = (String) type.getSelectedItem();
                String fven = (String) venue.getSelectedItem();
                String rTime = ettime.getText().toString();
                String rDate = etdate.getText().toString();

//                Calendar cal = new GregorianCalendar();
//                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//                String today = df.format((cal.getTime()));
//
//                Date rd = null;
//                try {
//                    rd = df.parse(rDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                int diff = rd.compareTo()

//                String[] compDate = rTime.split(":");
//                String[] compDate1 = compDate[1].split(" ");
//                if (compDate1[0] != "00"){
////                    FacilityType ft = FacilityType.getInstance(fdesc);
//                    rTime = nextSlotTime(rTime);
//                }


                Intent intent = new Intent(getApplicationContext(),AvailableFacilityList.class);
                Bundle bundle = new Bundle();
                bundle.putString("rdate",rDate);
                bundle.putString("rtime",rTime);
                bundle.putString("fdesc",fdesc);
                intent.putExtras(bundle);
                startActivity(intent);;
            }
        });
    }
}