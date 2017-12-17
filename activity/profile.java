package com.example.user.bloodconnect.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.user.bloodconnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class profile extends AppCompatActivity {
TextView name,number,email,city,pgender,pbloodgroup;
    Button dob;
    DatabaseReference reference;


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (TextView) findViewById(R.id.profilename);
        number = (TextView) findViewById(R.id.profilenumber);
        email = (TextView) findViewById(R.id.profilemail);
        city = (TextView) findViewById(R.id.profilecity);
        pgender = (TextView) findViewById(R.id.profilegender);
        pbloodgroup = (TextView) findViewById(R.id.profilebloodgroup);
        dob = (Button) findViewById(R.id.profiledob);



        Calendar cal = Calendar.getInstance();
        final int year = cal.get(cal.YEAR);
        final int month = cal.get(cal.MONTH);
        final int day = cal.get(cal.DAY_OF_MONTH);


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datepicker = new DatePickerDialog(profile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datepicker.show();
            }

        });

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("register").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );
                name.setText(map.get("Name"));
                pgender.setText(map.get("Gender"));
                number.setText(map.get("Number"));
                email.setText(map.get("Email"));
                pbloodgroup.setText(map.get("Blood Group"));
                dob.setText(map.get("Dob"));
                city.setText(map.get("City"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}

