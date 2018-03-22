package com.example.user.bloodconnect.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.bloodconnect.R;
import com.example.user.bloodconnect.activity.MyPojoModelActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 25-09-2017.
 */

public class DonationHistoryFragment extends Fragment {
    Query q;
    @Nullable
    RecyclerView donationlist;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("donation_history");
    ;
    String Acceptor_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2, container, false);
        donationlist= view.findViewById(R.id.donationlist);
        getvalue();
        DividerItemDecoration itemDecoration = new DividerItemDecoration(donationlist.getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_line));
        donationlist.setLayoutManager(new LinearLayoutManager(getContext()));
        donationlist.addItemDecoration(itemDecoration);
        return view;
    }

    public void getvalue()
    {
        q = reference.child(Acceptor_id).orderByChild("date");

        FirebaseRecyclerAdapter<MyPojoModelActivity, DonationHistoryHolder> adapter = new FirebaseRecyclerAdapter<MyPojoModelActivity, DonationHistoryHolder>(MyPojoModelActivity.class, R.layout.donationitem, DonationHistoryHolder.class, q) {
            @Override
            protected void populateViewHolder(final DonationHistoryHolder donationHistoryHolder, MyPojoModelActivity myPojoModelActivity, int i) {
                final String requester_id = getRef(i).getKey();

                reference.child(Acceptor_id).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String fname = dataSnapshot.child(requester_id).child("fname").getValue().toString();
                        String lname = dataSnapshot.child(requester_id).child("lname").getValue().toString();
                        String bloodunit = dataSnapshot.child(requester_id).child("bloodunit").getValue().toString();
                        String location = dataSnapshot.child(requester_id).child("location").getValue().toString();
                        String bloodgroup = dataSnapshot.child(requester_id).child("bloodgroup").getValue().toString();
                        donationHistoryHolder.setNeedname(fname, lname);
                        donationHistoryHolder.setNeedgroup(bloodgroup);
                        donationHistoryHolder.setNeedunit(bloodunit);
                        donationHistoryHolder.setNeedaddress(location);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });
            }
        };
        donationlist.setAdapter(adapter);

    }

    public static class DonationHistoryHolder extends RecyclerView.ViewHolder {

        private View v;
        private TextView needname, needaddress, needgroup, needunit;

        public DonationHistoryHolder(View itemView) {
            super(itemView);
            v = itemView;
        }
        public void setNeedname(String fname, String lname) {
            needname = (TextView) v.findViewById(R.id.donatename);
            needname.setText("Name :- " + fname+"" + lname);
        }

        public void setNeedaddress(String address) {
            needaddress = (TextView) v.findViewById(R.id.donateaddress);
            needaddress.setText("Hospital Address :- " + address);
        }

        public void setNeedgroup(String bloodgroup) {
            needgroup = (TextView) v.findViewById(R.id.donategroup);
            needgroup.setText("Blood Group :- " + bloodgroup);

        }

        public void setNeedunit(String bloodunit) {
            needunit = (TextView) v.findViewById(R.id.donateunit);
            needunit.setText("Blood Unit :- " + bloodunit);

        }


    }

}