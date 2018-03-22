package com.example.user.bloodconnect.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.user.bloodconnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccepterActivity extends AppCompatActivity {
    RecyclerView needAccepter;
    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("accepted_request").child(user_id);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepter);
        needAccepter = (RecyclerView) findViewById(R.id.needAcceptor);
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }
}