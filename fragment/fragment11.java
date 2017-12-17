package com.example.user.bloodconnect.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.user.bloodconnect.R;
import com.example.user.bloodconnect.activity.mypojo;
import com.example.user.bloodconnect.adapter.currenteadapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by User on 25-09-2017.
 */

public class fragment11 extends Fragment {
    DatabaseReference reference;
    ArrayList<mypojo> needlist=new ArrayList<>();
    ListView currentlist ;
    currenteadapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, null, false);
        currentlist = (ListView) v.findViewById(R.id.currentlist);
        reference = FirebaseDatabase.getInstance().getReference().child("donationhistory");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mypojo pojo = dataSnapshot.getValue(mypojo.class);
                needlist.add(pojo);
                adapter = new currenteadapter(getActivity(), R.layout.currentitem, needlist);
                currentlist.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        //return inflater.inflate(R.layout.fragment1, container, false);
        return v;
    }
}