package com.example.user.bloodconnect.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.bloodconnect.R;
import com.example.user.bloodconnect.activity.AcceptActivity;
import com.example.user.bloodconnect.activity.MyPojoModelActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 25-09-2017.
 */

public class Current_NeedFragment extends Fragment {
    RecyclerView currentlist;
    ImageView rightNav, leftNav;
    TextView date;
    String currDate;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("current_request");
    String accepter_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, null, false);
        currentlist = v.findViewById(R.id.currentlist);
        rightNav = v.findViewById(R.id.right_nav);
        leftNav = v.findViewById(R.id.left_nav);
        date = v.findViewById(R.id.date_cal);
        currDate=getCurrentDate();
        date.setText(currDate);
        getrequest(currDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        String currDate = date.getText().toString();
                        Log.d("new Date:", currDate);
                        getrequest(currDate);
                    }
                }, year, month, dayOfMonth);
                dp.show();

            }

        });

        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextDate = increaseDate();
                date.setText(nextDate);
                getrequest(nextDate);
            }
        });

        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevDate = decreaseDate();
                date.setText(prevDate);
                getrequest(prevDate);
            }
        });
        DividerItemDecoration itemDecoration = new DividerItemDecoration(currentlist.getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_line));
        currentlist.setLayoutManager(new LinearLayoutManager(getContext()));
        currentlist.addItemDecoration(itemDecoration);


        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        //return inflater.inflate(R.layout.fragment1, container, false);
        return v;

    }
    public String getCurrentDate()
    {
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(d);
    }

    public String increaseDate() {
        currDate = date.getText().toString();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = df.parse(currDate);
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 1);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return df.format(c.getTime());
    }

    public String decreaseDate() {
        currDate = date.getText().toString();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = df.parse(currDate);
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -1);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return df.format(c.getTime());
    }

    public void getrequest(final String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = sdf.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            final String newDate = sdf.format(c.getTime());


            Query q = reference.orderByChild("needdate").equalTo(newDate);
            FirebaseRecyclerAdapter<MyPojoModelActivity, RequestViewHolder> adapter = new FirebaseRecyclerAdapter<MyPojoModelActivity, RequestViewHolder>(MyPojoModelActivity.class, R.layout.currentitem, RequestViewHolder.class, q) {

                @Override
                protected void populateViewHolder(final RequestViewHolder requestViewHolder, MyPojoModelActivity mypojo, int i) {
                    final String requester_id = getRef(i).getKey();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String fname = dataSnapshot.child(requester_id).child("fName").getValue().toString();
                            String lname = dataSnapshot.child(requester_id).child("lName").getValue().toString();
                            String bloodunit = dataSnapshot.child(requester_id).child("bloodunit").getValue().toString();
                            String location = dataSnapshot.child(requester_id).child("address").getValue().toString();
                            String bloodgroup = dataSnapshot.child(requester_id).child("bloodgroup").getValue().toString();
                            requestViewHolder.setNeedname(fname, lname);
                            requestViewHolder.setNeedgroup(bloodgroup);
                            requestViewHolder.setNeedunit(bloodunit);
                            requestViewHolder.setNeedaddress(location);
                            requestViewHolder.v.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getActivity(), AcceptActivity.class);
                                    i.putExtra("accepterid", accepter_id);
                                    i.putExtra("requesterid", requester_id);
                                    startActivity(i);
                                }
                            });


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            };
            currentlist.setAdapter(adapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        private View v;
        private TextView needname, needaddress, needgroup, needunit;

        public RequestViewHolder(View itemView) {
            super(itemView);
            v = itemView;

        }


        public void setNeedname(String fname, String lname) {
            needname = (TextView) v.findViewById(R.id.needname);
            needname.setText("Name :- " + fname + lname);
        }

        public void setNeedaddress(String address) {
            needaddress = (TextView) v.findViewById(R.id.needaddress);
            needaddress.setText("Hospital Address :- " + address);
        }

        public void setNeedgroup(String bloodgroup) {
            needgroup = (TextView) v.findViewById(R.id.needgroup);
            needgroup.setText("Blood Group :- " + bloodgroup);

        }

        public void setNeedunit(String bloodunit) {
            needunit = (TextView) v.findViewById(R.id.needunit);
            needunit.setText("Blood Unit :- " + bloodunit);

        }


    }
}