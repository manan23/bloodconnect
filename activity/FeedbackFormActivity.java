package com.example.user.bloodconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.bloodconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FeedbackFormActivity extends AppCompatActivity {
    DatabaseReference reference;
    EditText feedname, feedmail, feednumber, feedmessage;
    Button feedsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackform);
        feedname = (EditText) findViewById(R.id.feedname);
        feedmail = (EditText) findViewById(R.id.feedmail);
        feedmessage = (EditText) findViewById(R.id.feedmessage);
        feednumber = (EditText) findViewById(R.id.feednumber);
        feedsubmit = (Button) findViewById(R.id.feedSubmit);


        feedsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = feedname.getText().toString();
                String mail = feedmail.getText().toString();
                String number = feednumber.getText().toString();
                String message = feedmessage.getText().toString();

                if (name.equals("") || number.equals("") || mail.equals("") || message.equals("")) {
                    Toast.makeText(FeedbackFormActivity.this, "ENTER ALL THE FIELDS", Toast.LENGTH_SHORT).show();
                }
                else if (number.length() == 10) {

                    reference = FirebaseDatabase.getInstance().getReference().child("feedback");
                    final Map feedback = new HashMap<>();
                    feedback.put("Name", name);
                    feedback.put("Number", number);
                    feedback.put("mail", mail);
                    feedback.put("message", message);
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    reference.child(id).setValue(feedback).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(FeedbackFormActivity.this, "Feedback Submission Successfull", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(FeedbackFormActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(FeedbackFormActivity.this, "feedback submission failed...!!! ", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                }

            }

        });
    }
}
