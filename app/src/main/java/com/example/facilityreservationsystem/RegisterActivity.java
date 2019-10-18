package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Hashtable;

import errorlog.AppLog;
import model.SysUser;

public class RegisterActivity extends AppCompatActivity {
    private SysUser currUser;
//    @Override
//    public void onBackPressed() {
//        if (currUser != null){
//            currUser.logout();
//        }
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Hashtable<String,String> regDetails = new Hashtable<String,String>();

        final Button regButton = findViewById(R.id.btregister1);
        final EditText username = findViewById(R.id.etusername);
        final EditText password = findViewById(R.id.etpassword);
        final EditText fname = findViewById(R.id.etfname);
        final EditText lname = findViewById(R.id.etlname);
        final Spinner role = findViewById(R.id.sprole);
        final EditText utaid = findViewById(R.id.etutaid);
        final EditText vehicle = findViewById(R.id.etvehicle);
        final EditText phone = findViewById(R.id.etphone);
        final EditText permit = findViewById(R.id.etpermit);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                regDetails.put("username",username.getText().toString());
                regDetails.put("password",password.getText().toString());
                regDetails.put("fname",fname.getText().toString());
                regDetails.put("lname",lname.getText().toString());
                regDetails.put("role",role.getSelectedItem().toString());
                regDetails.put("utaid",utaid.getText().toString());
                regDetails.put("vehicleno",vehicle.getText().toString());
                regDetails.put("parkingpermit",phone.getText().toString());
                regDetails.put("phone",permit.getText().toString());

//                currUser = SysUser.getUser(username.getText().toString(),getApplicationContext());
                boolean validity = SysUser.register(regDetails,getApplicationContext());
                if (validity == true){
                    currUser = SysUser.getUser(username.getText().toString(),getApplicationContext());
                    currUser.login(regDetails.get("password"));   //always returns true becoz new user just registered
                    switch (currUser.getRole()){
                        case "AD":
                            intent = new Intent(getApplicationContext(), AdminHomeScreen.class);
                            break;
                        case "UR":
                            intent = new Intent(getApplicationContext(), UserHomeScreen.class);
                            break;
                        case "FM":
                            intent = new Intent(getApplicationContext(), FacilityManagerHomeScreen.class);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + currUser.getRole());
                    }
                    intent.putExtra("username", username.getText().toString());
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), AppLog.getInstance().popMessage(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }

            }
        });
    }
}
