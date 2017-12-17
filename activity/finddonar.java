package com.example.user.bloodconnect.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.bloodconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class finddonar extends AppCompatActivity
{
    Spinner fbloodgroup;
    DatabaseReference reference;
    private FirebaseAuth mauth;

    EditText fbloodunit,ffname,flname,faddress,fnumber;
    Button fsubmit,fneeddate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finddonar);
        String bloodgroup[] = {"Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-", "Bombay Blood Group"};

        reference = FirebaseDatabase.getInstance().getReference().child("donationhistory");

        fbloodgroup = (Spinner) findViewById(R.id.fbloodgroup);
        fbloodunit = (EditText) findViewById(R.id.fbloodunit);
        ffname = (EditText) findViewById(R.id.ffname);
        flname = (EditText) findViewById(R.id.flname);
        faddress = (EditText) findViewById(R.id.faddress);
        fnumber = (EditText) findViewById(R.id.fnumber);
        fsubmit = (Button) findViewById(R.id.fsubmit);
        fneeddate=(Button)findViewById(R.id.fneeddate);

        Calendar cal=Calendar.getInstance();
        final int year=cal.get(cal.YEAR);
        final int month=cal.get(cal.MONTH);
        final int day=cal.get(cal.DAY_OF_MONTH);



        fneeddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datepicker=new DatePickerDialog(finddonar.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fneeddate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                datepicker.show();
            }

        });


        ArrayAdapter adapter1 = new ArrayAdapter(finddonar.this, R.layout.support_simple_spinner_dropdown_item, bloodgroup);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fbloodgroup.setAdapter(adapter1);

        fsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = ffname.getText().toString();
                String lname = flname.getText().toString();
                String address = faddress.getText().toString();
                String number = fnumber.getText().toString();
                String bloodunit = fbloodunit.getText().toString();
                String bloodgroup = fbloodgroup.getSelectedItem().toString();
                String needdate= fneeddate.getText().toString();

                if (fname.equals("") || lname.equals("") || address.equals("") || number.equals("") || bloodunit.equals("") || bloodgroup.equals("Select Blood Group")||needdate.equals("")) {
                    Toast.makeText(finddonar.this, "ENTER ALL THE FIELDS", Toast.LENGTH_SHORT).show();
                }

                 else if(number.length()==10)
                 {
                        ProgressDialog progress = new ProgressDialog(finddonar.this);
                        progress.setMessage("updating request.. PLEASE WAIT!!!!");
                        progress.show();
                        donationhistory(fname, lname, number, address, bloodgroup, bloodunit,needdate);
                        progress.cancel();
                }
            }
        });
    }
    private void donationhistory(String fname, String lname, String number, String address, String bloodgroup, String bloodunit,String needdate) {

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("donationhistory");

        final Map finddonar = new HashMap<>();
        finddonar.put("fName", fname);
        finddonar.put("lName", lname);
        finddonar.put("Number", number);
        finddonar.put("address", address);
        finddonar.put("bloodunit", bloodunit);
        finddonar.put("bloodgroup", bloodgroup);
        finddonar.put("needdate",needdate);
        reference.child(id).setValue(finddonar).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(finddonar.this, "Request updated", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(finddonar.this, dashboard.class);
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(finddonar.this, "request again...!!! ", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
}

