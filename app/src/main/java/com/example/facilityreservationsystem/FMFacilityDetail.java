package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.Facility;
import utils.AlertBox;

public class FMFacilityDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmfacility_detail);

        final TextView tvfname = findViewById(R.id.tvfname);
        final TextView tvftype = findViewById(R.id.tvftype);
        final TextView tvdeposit = findViewById(R.id.tvdeposit);
        final CheckBox cbstatus = findViewById(R.id.cbstatus);
        final EditText etdamage = findViewById(R.id.etdamage);

        final Button btmodify = findViewById(R.id.btmodify);

        String fname = getIntent().getExtras().getString("FACNAME");
        final Facility f = Facility.getInstance(fname,getApplicationContext());

        if(f !=null){
            tvfname.setText(f.getName());
            tvftype.setText(f.getFtype().getFdesc());
            tvdeposit.setText("$"+Integer.valueOf(f.getDeposit()).toString());
            if(f.getAvailability() == 1){
                cbstatus.setChecked(true);
            }else{
                cbstatus.setChecked(false);
            }
            etdamage.setText(f.getStatus());
        }

        btmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            AlertBox ab = new AlertBox("Do you wish to Update the Facility Details?");
            ab.showOKDialog(FMFacilityDetail.this, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(cbstatus.isSelected()){
                    f.setAvailability(1);
                }else{
                    f.setAvailability(0);
                }
                f.setStatus(String.valueOf(etdamage.getText()));
                f.update();
                Toast toast = Toast.makeText(getApplicationContext(), "Facility Details Modified!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
              }
            });


            }
        });



    }
}
