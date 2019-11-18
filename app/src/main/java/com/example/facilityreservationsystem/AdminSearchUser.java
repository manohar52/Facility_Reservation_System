package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;

import model.SysUser;
import model.User;

public class AdminSearchUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_user);

        final EditText etlname = findViewById(R.id.etlname);
        final Button btsearch = findViewById(R.id.btsearch);

        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lname = String.valueOf(etlname.getText());

                Bundle bundle = new Bundle();
                bundle.putString("LASTNAME",lname);
                Intent intent = new Intent(getApplicationContext(),AdminUserList.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
