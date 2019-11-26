package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.adminuserlistadapter;
import adapters.noshowviolationadapter;
import model.Admin;
import model.Reservation;
import model.SysUser;
import recyclerlistener.RecyclerTouchListener;

public class AdminUserList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private adminuserlistadapter mAdapter;
    private SysUser currUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String lname = "";
        SharedPreferences settings;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);

        recyclerView = (RecyclerView) findViewById(R.id.rvadulist);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
        lname = bundle.getString("LASTNAME");
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("SLASTNAME", lname);
        editor.commit();

        }
        else{
            settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            lname = settings.getString("SLASTNAME","");
        }
        String un = settings.getString("username","");
        currUser = SysUser.getUser(un,getApplicationContext());

        List<SysUser> userList = new ArrayList<>();
        userList = SysUser.getUsersByLastName(lname,getApplicationContext());

        if(userList == null){
            Toast toast = Toast.makeText(getApplicationContext(), "No user found!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
        else {
            Collections.sort(userList, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    SysUser u1 = (SysUser) o1;
                    SysUser u2 = (SysUser) o2;
                    return u1.getFname().compareTo(u2.getFname());
                }
            });
            mAdapter = new adminuserlistadapter(userList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            // row click listener
            final List<SysUser> finalUserList = userList;
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent;
                    SysUser user = finalUserList.get(position);

                    if (currUser.getRole().equals("AD")) {
                        intent = new Intent(getApplicationContext(), AdminUserDetail.class);
                    } else {
                        intent = new Intent(getApplicationContext(), FMUserDetail.class);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("SUSER", user.getUsername());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }
    }
}
