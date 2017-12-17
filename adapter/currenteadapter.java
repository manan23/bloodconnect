package com.example.user.bloodconnect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.bloodconnect.R;
import com.example.user.bloodconnect.activity.mypojo;

import java.util.ArrayList;

/**
 * Created by User on 12-12-2017.
 */

public class currenteadapter extends ArrayAdapter<mypojo> {
    public int resource;
    public Context context;
    public ArrayList<mypojo> needlist;
    public currenteadapter(@NonNull Context context, int resource,ArrayList<mypojo> needlist) {
        super(context, resource,needlist);
        this.resource=resource;
        this.context=context;
        this.needlist=needlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.currentitem,null);

        TextView needname= (TextView) view.findViewById(R.id.needname);
        TextView neednumber= (TextView) view.findViewById(R.id.neednumber);
        TextView needaddress= (TextView) view.findViewById(R.id.needaddress);
        TextView needgroup= (TextView) view.findViewById(R.id.needgroup);
        TextView needunit= (TextView) view.findViewById(R.id.needunit);
        TextView needdate   = (TextView) view.findViewById(R.id.needdate);
        Button buttonaccept = (Button) view.findViewById(R.id.buttonaccept);
        Button buttonreject = (Button) view.findViewById(R.id.buttonreject);
        Button buttonshare  = (Button) view.findViewById(R.id.buttonshare);

        mypojo pojo1=needlist.get(position);
        needname.setText("Name :- "+pojo1.getfName()+ pojo1.getlName());
        neednumber.setText("Contact Number :- "+pojo1.getNumber());
        needaddress.setText("Hospital Address :- "+pojo1.getAddress());
        needgroup.setText("Blood Group :- "+pojo1.getBloodgroup());
        needunit.setText("Blood Unit :- "+pojo1.getBloodunit());
        needdate.setText("Required Date :- "+pojo1.getNeeddate());
        buttonaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        buttonreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




      return view;
    }
}
