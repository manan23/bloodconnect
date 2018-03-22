package com.example.user.bloodconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bloodconnect.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AcceptActivity extends AppCompatActivity {
    TextView needname, neednumber, needaddress, needgroup, needunit, needdate, needstatus;
    Button buttonaccept, buttonshare;
    String fname, lname, bloodunit, location, bloodgroup, number, date, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        buttonaccept = (Button) findViewById(R.id.buttonaccept);
        buttonshare = (Button) findViewById(R.id.buttonshare);
        needname = (TextView) findViewById(R.id.requestname);
        neednumber = (TextView) findViewById(R.id.requestnumber);
        needaddress = (TextView) findViewById(R.id.requestaddress);
        needgroup = (TextView) findViewById(R.id.requestgroup);
        needunit = (TextView) findViewById(R.id.requestunit);
        needdate = (TextView) findViewById(R.id.requestdate);
        needstatus = (TextView) findViewById(R.id.requeststatus);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final String requester_id = getIntent().getStringExtra("requesterid");
        final String accepter_id = getIntent().getStringExtra("accepterid");
        reference.child("current_request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fname = dataSnapshot.child(requester_id).child("fName").getValue().toString();
                lname = dataSnapshot.child(requester_id).child("lName").getValue().toString();
                bloodunit = dataSnapshot.child(requester_id).child("bloodunit").getValue().toString();
                location = dataSnapshot.child(requester_id).child("address").getValue().toString();
                bloodgroup = dataSnapshot.child(requester_id).child("bloodgroup").getValue().toString();
                number = dataSnapshot.child(requester_id).child("Number").getValue().toString();
                date = dataSnapshot.child(requester_id).child("needdate").getValue().toString();
                status = dataSnapshot.child(requester_id).child("status").getValue().toString();
                needname.setText(fname + lname);
                neednumber.setText(number);
                needunit.setText(bloodunit);
                needgroup.setText(bloodgroup);
                needaddress.setText(location);
                needdate.setText(date);
                needstatus.setText(status);
                if ((!(accepter_id.equals(requester_id))) && (!status.equals("Accepted"))) {
                    buttonaccept.setVisibility(View.VISIBLE);
                    buttonshare.setVisibility(View.VISIBLE);
                    buttonaccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {

                            Map request = new HashMap();

                            request.put("accepted_request/" + accepter_id + "/" + requester_id + "/" + "status", "Accepted");
                            request.put("accepted_request/" + requester_id + "/" + accepter_id + "/" + "status", "Accepted");
                            request.put("current_request/" + requester_id + "/" + "status", "Accepted");

                            reference.updateChildren(request, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        Map donation = new HashMap();
                                        donation.put("fname", fname);
                                        donation.put("lname", lname);
                                        donation.put("bloodunit", bloodunit);
                                        donation.put("bloodgroup", bloodgroup);
                                        donation.put("location", location);
                                        donation.put("date", date);
                                        donation.put("number", number);

                                        reference.child("donation_history").child(accepter_id).child(requester_id).setValue(donation, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                if (databaseError == null) {
                                                    Toast.makeText(AcceptActivity.this, "updated", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        Intent intent = new Intent(AcceptActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            });

                        }


                    });
                    buttonshare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ;


    }
}
