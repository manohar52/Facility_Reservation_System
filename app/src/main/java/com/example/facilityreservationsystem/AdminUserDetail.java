package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import model.Admin;
import model.SysUser;
import utils.AlertBox;

public class AdminUserDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_detail);

        Bundle bundle = getIntent().getExtras();
        final String uname = bundle.getString("SUSER");

        final SysUser selUser = SysUser.getUser(uname,getApplicationContext());

        final TextView tvusername = findViewById(R.id.tvusername);

        final TextView fname = findViewById(R.id.tvfname);
        final TextView lname = findViewById(R.id.tvlname);
        final TextView utaid = findViewById(R.id.tvutaid);
        final TextView vehicle = findViewById(R.id.tvvehicle);
        final TextView phone = findViewById(R.id.tvphone);
        final TextView permit = findViewById(R.id.tvpermit);

        final Spinner sprole = findViewById(R.id.sprole);
        final TextView noshow = findViewById(R.id.tvnoshow);
        final TextView vio = findViewById(R.id.tvvio);



        final Button btnupdate = findViewById(R.id.btupdate);
        final Button btnrevoke = findViewById(R.id.btrevoke);

        tvusername.setText(uname);

        fname.setText(selUser.getFname());
        lname.setText(selUser.getLname());
        utaid.setText((String.valueOf(selUser.getUtaid())));
        vehicle.setText(selUser.getVehicle());
        phone.setText(String.valueOf(selUser.getPhone()));
        permit.setText(String.valueOf(selUser.getParking()));
        switch (selUser.getRole()){
            case "UR":
                sprole.setSelection(0);
                break;
            case "FM":
                sprole.setSelection(1);
                break;
            case "AD":
                sprole.setSelection(2);
                break;
        }

        noshow.setText(Integer.valueOf(selUser.getNoOfNoShows()).toString());
        vio.setText(Integer.valueOf(selUser.getNoOfViolations()).toString());

        if(selUser.getRevoked().equals("1")){
            btnrevoke.setTag(1);
            btnrevoke.setText("Un-Revoke User");

        }else{
            btnrevoke.setTag(0);
            btnrevoke.setText("Revoke User");
        }

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertBox ab = new AlertBox();
                ab.showOKDialog(AdminUserDetail.this, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String username = settings.getString("username","");
                        Admin admin = (Admin) SysUser.getUser(username, getApplicationContext());
                        switch(sprole.getSelectedItem().toString()){
                            case "User":
//                                admin.setRole("UR");
                                admin.updateUser(uname,"UR",getApplicationContext());
                                break;
                            case "Facility Manager":
//                                admin.setRole("FM");
                                admin.updateUser(uname,"FM",getApplicationContext());
                                break;
                            case "Admin":
//                                admin.setRole("AD");
                                admin.updateUser(uname,"AD",getApplicationContext());
                                break;
                        }
                        Toast toast = Toast.makeText(getApplicationContext(), "Update Sucessful", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    }
                });
                    }
                });

        btnrevoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertBox ab = new AlertBox();
                ab.showOKDialog(AdminUserDetail.this, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String username = settings.getString("username", "");
                        Admin admin = (Admin) SysUser.getUser(username, getApplicationContext());
                        admin.revokeUser(uname);

                        if (btnrevoke.getTag().toString().equals("1")) {
                            selUser.setRevoked("0");
                            btnrevoke.setTag(0);
                            btnrevoke.setText("Revoke User");
                            Toast toast = Toast.makeText(getApplicationContext(), "System User unrevoked", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();
                        } else {
                            selUser.setRevoked("1");
                            btnrevoke.setTag(1);
                            btnrevoke.setText("Un-Revoke User");
                            Toast toast = Toast.makeText(getApplicationContext(), "System User revoked", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();
                        }
                    }
                });

            }
        });
    }
}
