package com.example.user.bloodconnect.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.bloodconnect.R;
import com.example.user.bloodconnect.activity.mypojo;

import java.util.ArrayList;

/**
 * Created by User on 25-09-2017.
 */

public class fragment33 extends Fragment {
    ListView list1;
    ArrayList<mypojo> pojolist=new ArrayList<>();
    String name[] = {"MAHATMA GANDHI HOSPITAL","LIFE CARE BLOOD BANK","Blood Donation 365 Days","SANTOKBA DURLABHJI BLOOD BANK","AGARSEN BLOOD BANK","UMMED SINGH SUSHILA DEVI MEMORIAL BLOOD BANK","GLOBAL BLOOD DONAR ORGANIZATION SANSATHAN","FORTIS HOSPITAL","APEX HOSPITAL","NARAYANA HOSPITAL","METRO BLOOD BANK"};
    String address[]={"Sitapura Industrail Area,Jaipur-302022","Paramhans Colony, Murlipura, Jaipur - 302032","344, Near Seva Sadan, Bees Dukan, Adarsh Nagar, Jaipur - 302004","Bhawani Singh Road, Rambagh, Jaipur - 302005","Maharaja Agrasen Hospital, Sector No. 7, Vidyadhar Nagar, Jaipur - 302006","A-6, Shastri Nagar, Near Tagore Public School, Opposite State Bank Of Indore, Jaipur - 302016","2240, Near Siwam Mandir, Kishanpole Bazar, Jaipur","214,JLN Marg, Malviya Nagar","SP-6, Industrial Area, Malviya Nagar","Sector-28, Pratap Nagar, Sanganer","Shipra Path, Near Technology Park, Mansarovar"};
    String  number[]={"0141 2771777","0141 2236108","0141 2603151"," 0141 2566251"," 0141 2335569","0141 2281020","0141 2315598","0120 2547000","0141 2751870","0141 5192939","0141 2786001"};
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment3,null,false);
        list1 = (ListView)v.findViewById(R.id.frag3list);
        ArrayAdapter<String> bblist = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,name);
        list1.setAdapter(bblist);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builds=new AlertDialog.Builder(getActivity());
                LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v1=inflater.inflate(R.layout.alertitem,null,false);

                TextView bankname= (TextView) v1.findViewById(R.id.bankname);
                TextView bankaddress= (TextView) v1.findViewById(R.id.bankaddress);
                TextView banknumber= (TextView) v1.findViewById(R.id.banknumber);
                Button buttoncall= (Button) v1.findViewById(R.id.buttoncall);

                bankname.setText(name[position]);
                bankaddress.setText("Address-"+address[position]);
                final String bankno=number[position];
                banknumber.setText("Contact Number-"+ bankno);
                builds.setView(v1);
                builds.show();
                buttoncall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",bankno,null));
                        startActivity(intent);
                    }
                });


            }


        });

       // return inflater.inflate(R.layout.fragment3, container, false);
        return v;

    }



}
